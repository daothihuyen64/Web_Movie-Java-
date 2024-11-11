package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.ReleaseYearDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.dto.SimpleReleaseYearDTO;
import com.webxemphim.demo.entity.Country;
import com.webxemphim.demo.entity.Release_Year;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.ReleaseYearInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReleaseYearService {
    
    @Autowired
    private ReleaseYearInterface releaseYearRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseData getAllReleaseYear() {
        List<Release_Year> years = releaseYearRepository.findAll(); // Lấy danh sách tất cả thể loại


        List<SimpleReleaseYearDTO> yearDTOs = years.stream()
                .map(year -> {
                    SimpleReleaseYearDTO yearDTO = new SimpleReleaseYearDTO();
                    yearDTO.setId(year.getId()); // Cập nhật ID
                    yearDTO.setYear(year.getYear()); // Cập nhật tên
                    return yearDTO;
                })
                .collect(Collectors.toList());

        return new ResponseData(200, true, "Lấy tất cả năm phát hành thành công!", yearDTOs);
    }
    // Thêm hàm
    // Hàm trả về danh sách phim trending
    
    public ResponseData trendingMovie() {
        // Lấy năm phát hành mới nhất
        // Lấy năm phát hành mới nhất mà không sử dụng Comparator.comparing
        Optional<Release_Year> latestReleaseYear = releaseYearRepository.findAll().stream()
        .max((year1, year2) -> Integer.compare(year1.getYear(), year2.getYear()));

        if (latestReleaseYear.isPresent()) {
            List<SimpleMovieDTO> trendingMovies = latestReleaseYear.get().getMovieList().stream()
                    .filter(movie -> movie.getStatus() != 0)  // Loại bỏ phim có status = 0
                    .sorted((movie1, movie2) -> Integer.compare(movie2.getViews(), movie1.getViews()))  // Sắp xếp theo lượt xem giảm dần
                    .limit(20)  // Giới hạn danh sách 20 phim
                    .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class))
                    .collect(Collectors.toList());

            return new ResponseData(200, true, "Lấy danh sách phim trending thành công!", trendingMovies);
        } 
        else {
            return new ResponseData(404, false, "Không tìm thấy năm phát hành mới nhất!", null);
        }
    }
    // Hàm trả về danh sách phim mới nhất
    public ResponseData newReleaseMovie() {

        // Lấy tất cả các năm phát hành, sắp xếp theo thứ tự giảm dần
        List<Release_Year> allReleaseYears = releaseYearRepository.findAll().stream()
                .sorted((year1, year2) -> Integer.compare(year2.getYear(), year1.getYear())) // Sắp xếp theo năm giảm dần
                .collect(Collectors.toList());

        List<SimpleMovieDTO> latestMovies = allReleaseYears.stream()
                .flatMap(year -> year.getMovieList().stream()) // Lấy tất cả các phim từ các năm phát hành
                .filter(movie -> movie.getStatus() != 0) // Loại bỏ phim có status = 0
                .sorted((movie1, movie2) -> {
                    // Sắp xếp theo năm phát hành trước, nếu bằng nhau thì sắp xếp theo movieId giảm dần
                    int yearCompare = Integer.compare(movie2.getRelease_year().getYear(), movie1.getRelease_year().getYear());
                    if (yearCompare == 0) {
                        return Integer.compare(movie2.getId(), movie1.getId());
                    }
                    return yearCompare;
                })
                .limit(100) // Giới hạn danh sách 100 phim
                .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class)) // Chuyển đổi sang DTO
                .collect(Collectors.toList());

        return new ResponseData(200, true, "Lấy danh sách phim mới nhất thành công!", latestMovies);
    }

    public ResponseData getReleaseYearById(int releaseYearId) {

        Optional<Release_Year> releaseYear = releaseYearRepository.findById(releaseYearId);

        if (releaseYear.isPresent()) {
            Release_Year foundYear = releaseYear.get(); 
            List<SimpleMovieDTO> movieDTOList = foundYear.getMovieList().stream()
                    .filter(movie -> movie.getStatus() != 0)
                    .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class))
                    .collect(Collectors.toList());

            ReleaseYearDTO releaseYearDTO = modelMapper.map(foundYear, ReleaseYearDTO.class);
            releaseYearDTO.setMovies(movieDTOList);
            return new ResponseData(200, true, "Đã lấy năm phát hành thành công!", releaseYearDTO);
        }
        else {
            return new ResponseData(400, false, "Không tìm thấy năm phát hành!", null);
        }
    }
    
    
}