package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Country;
import com.webxemphim.demo.entity.Genre;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.entity.Release_Year;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.CountryInterface;
import com.webxemphim.demo.repository.GenreInterface;
import com.webxemphim.demo.repository.MovieInterface;
import com.webxemphim.demo.repository.ReleaseYearInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private GenreInterface genreInterface;
    @Autowired
    private ReleaseYearInterface releaseYearInterface;
    @Autowired
    private CountryInterface countryInterface;
    @Autowired
    private MovieInterface movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    // private Movie createMovieEntity(MovieDTO movieDTO) {
    //     Movie movie = modelMapper.map(movieDTO, Movie.class);
    //     movie.setRatingMean(0);
    //     movie.setViews(0);
    //     return movie;
    // }

    private Movie getActiveMovieOrThrow(int movieId) {
        return movieRepository.findById(movieId)
                .filter(movie -> movie.getStatus() != 0)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phim hoặc phim đã bị ẩn!"));
    }

    public ResponseData addMovie(MovieDTO movieDTO) {
        // Kiểm tra xem phim đã tồn tại theo tên
        Optional<Movie> existingMovieOpt = movieRepository.findFirstByMovieName(movieDTO.getMovieName());

        // Nếu phim đã tồn tại với status != 0, không thêm mới
        if (existingMovieOpt.isPresent() && existingMovieOpt.get().getStatus() != 0) {
            return new ResponseData(400, false, "Phim đã tồn tại trong hệ thống!", null);
        }

        Movie movie;
        if (existingMovieOpt.isPresent()) {
            // Nếu tồn tại và status = 0, dùng phim này
            movie = existingMovieOpt.get();
        } 
        else {
            // Nếu chưa tồn tại, tạo mới phim
            movie = new Movie();
        }

        // Cập nhật thông tin từ DTO vào Entity
        movie.setMovieName(movieDTO.getMovieName());
        movie.setPoster(movieDTO.getPoster());
        movie.setTrailer(movieDTO.getTrailer());
        movie.setDescription(movieDTO.getDescription());
        movie.setDirector(movieDTO.getDirector());
        movie.setTotalEpisodes(movieDTO.getTotalEpisodes());
        movie.setViews(movieDTO.getViews());
        movie.setStatus(1); // Đặt status là 1 khi thêm mới

        // Lấy Genre từ database dựa trên ID trong DTO
        if (movieDTO.getGenre() != null) {
            Optional<Genre> genreOpt = genreInterface.findById(movieDTO.getGenre().getId());
            if (genreOpt.isPresent()) {
                movie.setGenre(genreOpt.get());
            } 
            else {
                return new ResponseData(404, false, "Thể loại không tồn tại!", null);
            }
        }

        // Tương tự với Country và ReleaseYear nếu cần
        if (movieDTO.getCountry() != null) {
            Optional<Country> countryOpt = countryInterface.findById(movieDTO.getCountry().getId());
            countryOpt.ifPresent(movie::setCountry);
        }

        if (movieDTO.getRelease_year() != null) {
            Optional<Release_Year> releaseYearOpt = releaseYearInterface.findById(movieDTO.getRelease_year().getId());
            releaseYearOpt.ifPresent(movie::setRelease_year);
        }

        // Lưu vào cơ sở dữ liệu
        movieRepository.save(movie);

        // Chuyển đổi sang DTO để trả về
        MovieDTO savedMovieDTO = modelMapper.map(movie, MovieDTO.class);

        return new ResponseData(200, true, "Thêm phim thành công!", savedMovieDTO);
    }
    

    public ResponseData getMovie(int movieId) {
        try {
            Movie movie = getActiveMovieOrThrow(movieId);
            MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
            return new ResponseData(200, true, "Lấy thông tin phim thành công!", movieDTO);
        } catch (IllegalArgumentException e) {
            return new ResponseData(404, false, e.getMessage(), null);
        }
    }

    public ResponseData updateMovie(int id, MovieDTO movieDTO) {
        // Tìm phim theo ID
        Optional<Movie> movieOpt = movieRepository.findById(id);
    
        // Nếu không tìm thấy phim, trả về lỗi 404
        if (!movieOpt.isPresent()) {
            return new ResponseData(404, false, "Không tìm thấy phim!", null);
        }
    
        Movie movie = movieOpt.get();
    
    
        // Cập nhật thông tin từ DTO vào Entity
        movie.setMovieName(movieDTO.getMovieName());
        movie.setPoster(movieDTO.getPoster());
        movie.setTrailer(movieDTO.getTrailer());
        movie.setDescription(movieDTO.getDescription());
        movie.setDirector(movieDTO.getDirector());
        movie.setTotalEpisodes(movieDTO.getTotalEpisodes());
        movie.setViews(movieDTO.getViews());
        movie.setStatus(1); // Giữ status là 1 khi cập nhật
    
        // Cập nhật Genre
        if (movieDTO.getGenre() != null) {
            Optional<Genre> genreOpt = genreInterface.findById(movieDTO.getGenre().getId());
            if (genreOpt.isPresent()) {
                movie.setGenre(genreOpt.get());
            } else {
                return new ResponseData(404, false, "Thể loại không tồn tại!", null);
            }
        }
    
        // Cập nhật Country nếu cần
        if (movieDTO.getCountry() != null) {
            Optional<Country> countryOpt = countryInterface.findById(movieDTO.getCountry().getId());
            if (countryOpt.isPresent()) {
                movie.setCountry(countryOpt.get());
            } 
            else {
                return new ResponseData(404, false, "Quốc gia không tồn tại!", null);
            }
        }
    
        // Cập nhật ReleaseYear nếu cần
        if (movieDTO.getRelease_year() != null) {
            Optional<Release_Year> releaseYearOpt = releaseYearInterface.findById(movieDTO.getRelease_year().getId());
            if (releaseYearOpt.isPresent()) {
                movie.setRelease_year(releaseYearOpt.get());
            } 
            else {
                return new ResponseData(404, false, "Năm phát hành không tồn tại!", null);
            }
        }
    
        // Lưu phim đã cập nhật vào cơ sở dữ liệu
        movieRepository.save(movie);
    
        // Chuyển đổi sang DTO để trả về
        MovieDTO updatedMovieDTO = modelMapper.map(movie, MovieDTO.class);
    
        return new ResponseData(200, true, "Cập nhật phim thành công!", updatedMovieDTO);
    }

    public ResponseData deleteMovie(int movieId) {
        try {
            Movie movie = getActiveMovieOrThrow(movieId);
            movie.setStatus(0);
            movieRepository.save(movie);
            return new ResponseData(200, true, "Xóa phim thành công!", null);
        } 
        catch (IllegalArgumentException e) {
            return new ResponseData(404, false, e.getMessage(), null);
        }
    }

    public ResponseData getView(int movieId) {
        try {
            Movie movie = getActiveMovieOrThrow(movieId);
            return new ResponseData(200, true, "Lấy số lượt xem thành công!", movie.getViews());
        } 
        catch (IllegalArgumentException e) {
            return new ResponseData(404, false, e.getMessage(), null);
        }
    }

    public ResponseData getMovieRanking() {
        List<SimpleMovieDTO> movies = movieRepository.findAllByOrderByRatingMeanDesc()
                .stream()
                .filter(movie -> movie.getStatus() != 0)
                .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class))
                .collect(Collectors.toList());

        return new ResponseData(200, true, "Lấy danh sách phim theo xếp hạng thành công!", movies);
    }

    public ResponseData startTrailer(int movieId) {
        try {
            Movie movie = getActiveMovieOrThrow(movieId);
            String trailerUrl = movie.getTrailer();
            if (trailerUrl == null || trailerUrl.isEmpty()) {
                return new ResponseData(400, false, "Phim này chưa có trailer!", null);
            }
            return new ResponseData(200, true, null, trailerUrl);
        } 
        catch (IllegalArgumentException e) {
            return new ResponseData(404, false, e.getMessage(), null);
        }
    }
}
