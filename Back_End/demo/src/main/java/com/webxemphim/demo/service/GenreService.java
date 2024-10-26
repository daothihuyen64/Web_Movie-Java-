package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.GenreDTO;
import com.webxemphim.demo.dto.SimpleGenreDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Genre;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.GenreInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreInterface genreRepository;
    @Autowired
    private ModelMapper modelMapper;
    // Truy xuất danh sách movie theo genreID
    public ResponseData getGenreById(int genreId) {

        Optional<Genre> genre = genreRepository.findById(genreId);

        if (genre.isPresent()) {
            // Lấy danh sách movie của genre, lọc các phim có status != 0 và ánh xạ sang SimpleMovieDTO
            List<SimpleMovieDTO> movieDTOList = genre.get().getMovieList().stream()
                    .filter(movie -> movie.getStatus() != 0) // Chỉ lấy các phim có status != 0
                    .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class))
                    .collect(Collectors.toList());

            // Đóng gói dữ liệu thành GenreDTO
            // Trả về ResponseData thành công với dữ liệu GenreDTO
            GenreDTO genreDTO = modelMapper.map(genre, GenreDTO.class);
            genreDTO.setMovieList(movieDTOList);

            return new ResponseData(200, true, "Đã lấy thể loại thành công!", genreDTO);
        } 
        else {
            // Nếu không tìm thấy genre, trả về ResponseData với lỗi
            return new ResponseData(404, false, "Không tìm thấy thể loại!", null);
        }
    }
    public ResponseData getAllGenre() {
        List<Genre> genres = genreRepository.findAll(); // Lấy danh sách tất cả thể loại

        List<SimpleGenreDTO> genreDTOs = genres.stream()
                .map(genre -> {
                    SimpleGenreDTO genreDTO = new SimpleGenreDTO();
                    genreDTO.setId(genre.getId()); // Cập nhật ID
                    genreDTO.setGenreName(genre.getGenreName()); // Cập nhật tên
                    return genreDTO;
                })
                .collect(Collectors.toList());

        return new ResponseData(200, true, "Lấy tất cả thể loại thành công!", genreDTOs);
    }
}