// package com.webxemphim.demo.service;

// import com.webxemphim.demo.dto.GenreDTO;
// import com.webxemphim.demo.dto.MovieDTO;
// import com.webxemphim.demo.entity.Genre;
// import com.webxemphim.demo.exception.ResourceNotFoundException;
// import com.webxemphim.demo.repository.GenreInterface;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class GenreService {
//     @Autowired
//     private GenreInterface genreRepository;

//     public GenreDTO getGenreById(int genreId) {
//         Genre genre = genreRepository.findById(genreId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));

//         List<MovieDTO> movieDTOList = genre.getMovieList().stream()
//                 .map(movie -> new MovieDTO(movie.getId(), movie.getMovieName(), movie.getPoster()))
//                 .collect(Collectors.toList());

//         return new GenreDTO(genre.getId(), genre.getGenreName(), movieDTOList);
//     }
// }
package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.GenreDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Genre;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.GenreInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private GenreInterface genreRepository;

    // Truy xuất danh sách movie theo genreID
    public ResponseData getGenreById(int genreId) {
        ResponseData responseData = new ResponseData();

        Optional<Genre> genre = genreRepository.findById(genreId);

        if (genre.isPresent()) {
            // Lấy danh sách movie của genre và ánh xạ sang SimpleMovieDTO
            List<SimpleMovieDTO> movieDTOList = genre.get().getMovieList().stream()
                    .map(movie -> new SimpleMovieDTO(
                            movie.getId(),
                            movie.getMovieName(),
                            movie.getPoster()
                    ))
                    .collect(Collectors.toList());

            // Đóng gói dữ liệu thành GenreDTO
            GenreDTO genreDTO = new GenreDTO(genre.get().getId(), genre.get().getGenreName(), movieDTOList);

            // Trả về ResponseData thành công với dữ liệu GenreDTO
            responseData.setStatus(200);
            responseData.setSuccess(true);
            responseData.setDesc("Đã lấy thể loại thành công");
            responseData.setData(genreDTO);
        } else {
            // Nếu không tìm thấy genre, trả về ResponseData với lỗi
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy thể loại!");
        }

        return responseData;
    }
}
