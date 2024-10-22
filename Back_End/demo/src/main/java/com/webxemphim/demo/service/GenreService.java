package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.GenreDTO;
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
        ResponseData responseData = new ResponseData();

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
            responseData.setData(genreDTO);;
            responseData.setDesc("Đã lấy thể loại thành công!");
            responseData.setData(genreDTO);
        } 
        else {
            // Nếu không tìm thấy genre, trả về ResponseData với lỗi
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy thể loại!");
        }

        return responseData;
    }

}
