// // package com.webxemphim.demo.service;
// // import com.webxemphim.demo.dto.MovieDTO;
// // import com.webxemphim.demo.entity.Movie;
// // import com.webxemphim.demo.entity.Episode;
// // import com.webxemphim.demo.repository.MovieInterface;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import java.util.Optional;

// // @Service
// // public class MovieService {

// //     @Autowired
// //     private MovieInterface movieRepository;

// //     // Thêm movie
// //     public Movie addMovie(MovieDTO movieDTO) {
// //         Movie movie = new Movie();
// //         mapToEntity(movieDTO, movie);
// //         return movieRepository.save(movie);
// //     }

// //     // Lấy thông tin movie theo ID
// //     public Optional<Movie> getMovie(int movieId) {
// //         return movieRepository.findById(movieId);
// //     }

// //     // Sửa thông tin movie theo ID
// //     public Optional<Movie> updateMovie(int id, MovieDTO movieDTO) {
// //         Optional<Movie> existingMovie = movieRepository.findById(id);
// //         if (existingMovie.isPresent()) {
// //             Movie updatedMovie = existingMovie.get();
// //             mapToEntity(movieDTO, updatedMovie);
// //             return Optional.of(movieRepository.save(updatedMovie));
// //         }
// //         return Optional.empty();
// //     }

// //     // Xóa movie (cập nhật trạng thái thành "inactive")
// //     public boolean deleteMovie(int movieId) {
// //         Optional<Movie> movie = movieRepository.findById(movieId);
// //         if (movie.isPresent()) {
// //             Movie updatedMovie = movie.get();
// //             updatedMovie.setStatus(0); // Giả sử 0 là trạng thái "inactive"
// //             movieRepository.save(updatedMovie);
// //             return true;
// //         }
// //         return false;
// //     }

// //     // Phát video từ URL (trả về URL tập phim dựa trên MovieID và EpisodeID)
// //     public String startMovie(int movieId, int episodeId) {
// //         Optional<Movie> movie = movieRepository.findById(movieId);
// //         if (movie.isPresent()) {
// //             return movie.get().getEpisodeList().stream()
// //                     .filter(episode -> episode.getId() == episodeId)
// //                     .map(Episode::getEpisodeUrl)
// //                     .findFirst().orElse(null);
// //         }
// //         return null;
// //     }

// //     // Trả về số lượt xem của một movie
// //     public int getView(int movieId) {
// //         return movieRepository.findById(movieId)
// //                 .map(Movie::getViews)
// //                 .orElse(0);
// //     }

// //     // Phương thức để ánh xạ từ DTO sang Entity
// //     private void mapToEntity(MovieDTO movieDTO, Movie movie) {
// //         movie.setMovieName(movieDTO.getMovieName());
// //         movie.setPoster(movieDTO.getPoster());
// //         movie.setTrailer(movieDTO.getTrailer());
// //         movie.setDescription(movieDTO.getDescription());
// //         //movie.setRatingMean(movieDTO.getRatingMean());
// //         movie.setDirector(movieDTO.getDirector());
// //         movie.setTotalEpisodes(movieDTO.getTotalEpisodes());
// //         movie.setViews(movieDTO.getViews());
// //         movie.setStatus(movieDTO.getStatus());
        
// //         // Nếu bạn cần ánh xạ thêm các thuộc tính liên quan như genre, release_year, country, v.v.
// //         // movie.setGenre(movieDTO.getGenre());
// //         // movie.setRelease_year(movieDTO.getRelease_year());
// //         // movie.setCountry(movieDTO.getCountry());
// //     }
// // }



// package com.webxemphim.demo.service;

// import com.webxemphim.demo.dto.MovieDTO;
// import com.webxemphim.demo.dto.SimpleMovieDTO;
// import com.webxemphim.demo.entity.Movie;
// import com.webxemphim.demo.payload.ResponseData;
// import com.webxemphim.demo.repository.MovieInterface;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class MovieService {

//     @Autowired
//     private MovieInterface movieRepository;

//     // Hàm khởi tạo 1 movie với các thông tin tương ứng
//     public Movie createMovie(MovieDTO movieDTO) {
//         Movie movie = new Movie();
//         mapToEntity(movieDTO, movie);
//         movie.setRatingMean(10);  // Đặt giá trị mặc định cho rating_mean là 10
//         return movieRepository.save(movie);
//     }

//     // // Thêm thông tin 1 bộ phim vào database
//     // public ResponseData addMovie(MovieDTO movieDTO) {
//     //     Movie movie = createMovie(movieDTO);
//     //     ResponseData responseData = new ResponseData();
//     //     responseData.setData(movie);
//     //     responseData.setDesc("Thêm phim thành công.");
//     //     return responseData;
//     // }

//     public ResponseData addMovie(MovieDTO movieDTO) {
//         Movie movie = createMovie(movieDTO);  // Tạo đối tượng Movie từ DTO và lưu vào CSDL
//         movieRepository.save(movie);  // Lưu đối tượng Movie vào cơ sở dữ liệu
    
//         ResponseData responseData = new ResponseData();
        
//         // Ánh xạ Movie vừa tạo sang MovieDTO để trả về
//         MovieDTO savedMovieDTO = mapToDTO(movie);
//         responseData.setData(savedMovieDTO);  // Trả về DTO
//         responseData.setDesc("Thêm phim thành công.");
    
//         return responseData;
//     }
    

//     public ResponseData getMovie(int movieId) {
//         ResponseData responseData = new ResponseData();
//         Optional<Movie> movie = movieRepository.findById(movieId);
        
//         if (movie.isPresent()) {
//             MovieDTO movieDTO = mapToDTO(movie.get()); // Ánh xạ từ Movie sang MovieDTO
//             responseData.setData(movieDTO); // Trả về DTO thay vì Entity
//             responseData.setDesc("Lấy thông tin phim thành công.");
//         } else {
//             responseData.setStatus(404);
//             responseData.setSuccess(false);
//             responseData.setDesc("Không tìm thấy phim.");
//         }
        
//         return responseData;
//     }

//     // Cập nhật movie
//     public ResponseData updateMovie(int id, MovieDTO movieDTO) {
//         ResponseData responseData = new ResponseData();
//         Optional<Movie> existingMovie = movieRepository.findById(id);
//         if (existingMovie.isPresent()) {
//             Movie updatedMovie = existingMovie.get();
//             mapToEntity(movieDTO, updatedMovie);
//             movieRepository.save(updatedMovie);
//             responseData.setData(updatedMovie);
//             responseData.setDesc("Cập nhật phim thành công.");
//         } else {
//             responseData.setStatus(404);
//             responseData.setSuccess(false);
//             responseData.setDesc("Không tìm thấy phim để cập nhật.");
//         }
//         return responseData;
//     }

//     // Xóa movie (cập nhật trạng thái thành "inactive")
//     public ResponseData deleteMovie(int movieId) {
//         ResponseData responseData = new ResponseData();
//         Optional<Movie> movie = movieRepository.findById(movieId);
//         if (movie.isPresent()) {
//             Movie updatedMovie = movie.get();
//             updatedMovie.setStatus(0);  // Giả sử 0 là trạng thái "inactive"
//             movieRepository.save(updatedMovie);
//             responseData.setDesc("Xóa phim thành công.");
//         } else {
//             responseData.setStatus(404);
//             responseData.setSuccess(false);
//             responseData.setDesc("Không tìm thấy phim để xóa.");
//         }
//         return responseData;
//     }

//     // Trả về số lượt xem của một movie
//     public ResponseData getView(int movieId) {
//         ResponseData responseData = new ResponseData();
//         Optional<Movie> movie = movieRepository.findById(movieId);
//         if (movie.isPresent()) {
//             responseData.setData(movie.get().getViews());
//             responseData.setDesc("Lấy số lượt xem thành công.");
//         } else {
//             responseData.setStatus(404);
//             responseData.setSuccess(false);
//             responseData.setDesc("Không tìm thấy phim để lấy số lượt xem.");
//         }
//         return responseData;
//     }

//     // Trả về danh sách phim theo rating_mean giảm dần
//     public ResponseData getMovieRanking() {
//         ResponseData responseData = new ResponseData();
//         List<SimpleMovieDTO> movies = movieRepository.findAllByOrderByRatingMeanDesc()
//             .stream()
//             .map(movie -> new SimpleMovieDTO(movie.getId(), movie.getMovieName(), movie.getPoster()))
//             .collect(Collectors.toList());
        
//         responseData.setData(movies);
//         responseData.setDesc("Lấy danh sách phim theo xếp hạng thành công.");
//         return responseData;
//     }

//     // Phương thức để ánh xạ từ DTO sang Entity
//     private void mapToEntity(MovieDTO movieDTO, Movie movie) {
//         movie.setMovieName(movieDTO.getMovieName());
//         movie.setPoster(movieDTO.getPoster());
//         movie.setTrailer(movieDTO.getTrailer());
//         movie.setDescription(movieDTO.getDescription());
//         movie.setDirector(movieDTO.getDirector());
//         movie.setTotalEpisodes(movieDTO.getTotalEpisodes());
//         movie.setViews(movieDTO.getViews());
//         movie.setStatus(movieDTO.getStatus());
//         // Các ánh xạ khác nếu cần
//     }
//         // Phương thức ánh xạ từ Movie sang MovieDTO
//     private MovieDTO mapToDTO(Movie movie) {
//         MovieDTO movieDTO = new MovieDTO();
//         movieDTO.setId(movie.getId());
//         movieDTO.setMovieName(movie.getMovieName());
//         movieDTO.setPoster(movie.getPoster());
//         movieDTO.setTrailer(movie.getTrailer());
//         movieDTO.setDescription(movie.getDescription());
//         movieDTO.setRatingMean(movie.getRatingMean());
//         movieDTO.setDirector(movie.getDirector());
//         movieDTO.setTotalEpisodes(movie.getTotalEpisodes());
//         movieDTO.setViews(movie.getViews());
//         movieDTO.setStatus(movie.getStatus());
//         return movieDTO; // Trả về DTO thay vì Entity
//     }

// }




package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.MovieInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieInterface movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Hàm khởi tạo 1 movie với các thông tin tương ứng
    public Movie createMovie(MovieDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class); // Sử dụng ModelMapper để ánh xạ từ DTO sang Entity
        movie.setRatingMean(10);  // Đặt giá trị mặc định cho rating_mean là 10
        return movieRepository.save(movie);
    }

    public ResponseData addMovie(MovieDTO movieDTO) {
        Movie movie = createMovie(movieDTO);  // Tạo đối tượng Movie từ DTO và lưu vào CSDL
        movieRepository.save(movie);  // Lưu đối tượng Movie vào cơ sở dữ liệu

        ResponseData responseData = new ResponseData();

        // Ánh xạ Movie vừa tạo sang MovieDTO để trả về
        MovieDTO savedMovieDTO = modelMapper.map(movie, MovieDTO.class); // Sử dụng ModelMapper để ánh xạ từ Entity sang DTO
        responseData.setData(savedMovieDTO);  // Trả về DTO
        responseData.setDesc("Thêm phim thành công.");

        return responseData;
    }

    public ResponseData getMovie(int movieId) {
        ResponseData responseData = new ResponseData();
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isPresent()) {
            MovieDTO movieDTO = modelMapper.map(movie.get(), MovieDTO.class); // Sử dụng ModelMapper để ánh xạ từ Entity sang DTO
            responseData.setData(movieDTO); // Trả về DTO thay vì Entity
            responseData.setDesc("Lấy thông tin phim thành công.");
        } else {
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy phim.");
        }

        return responseData;
    }

    public ResponseData updateMovie(int id, MovieDTO movieDTO) {
        ResponseData responseData = new ResponseData();
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            Movie updatedMovie = existingMovie.get();
            modelMapper.map(movieDTO, updatedMovie); // Ánh xạ thông tin từ DTO sang Entity để cập nhật
            movieRepository.save(updatedMovie);
            MovieDTO updatedMovieDTO = modelMapper.map(updatedMovie, MovieDTO.class); // Trả về MovieDTO sau khi cập nhật
            responseData.setData(updatedMovieDTO);
            responseData.setDesc("Cập nhật phim thành công.");
        } else {
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy phim để cập nhật.");
        }
        return responseData;
    }

    public ResponseData deleteMovie(int movieId) {
        ResponseData responseData = new ResponseData();
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            Movie updatedMovie = movie.get();
            updatedMovie.setStatus(0);  // Giả sử 0 là trạng thái "inactive"
            movieRepository.save(updatedMovie);
            responseData.setDesc("Xóa phim thành công.");
        } else {
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy phim để xóa.");
        }
        return responseData;
    }

    public ResponseData getView(int movieId) {
        ResponseData responseData = new ResponseData();
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            responseData.setData(movie.get().getViews());
            responseData.setDesc("Lấy số lượt xem thành công.");
        } else {
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy phim để lấy số lượt xem.");
        }
        return responseData;
    }

    public ResponseData getMovieRanking() {
        ResponseData responseData = new ResponseData();
        List<SimpleMovieDTO> movies = movieRepository.findAllByOrderByRatingMeanDesc()
            .stream()
            .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class)) // Ánh xạ từng Movie sang SimpleMovieDTO
            .collect(Collectors.toList());

        responseData.setData(movies);
        responseData.setDesc("Lấy danh sách phim theo xếp hạng thành công.");
        return responseData;
    }
    public ResponseData startTrailer(int movieId) {
        ResponseData responseData = new ResponseData();
        Optional<Movie> movie = movieRepository.findById(movieId);
    
        if (movie.isPresent() && movie.get().getTrailer() != null) {
            responseData.setData(movie.get().getTrailer()); // Trả về URL trailer
            responseData.setDesc("Lấy trailer thành công.");
        } else {
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy phim hoặc trailer không tồn tại.");
        }
        return responseData;
    }
}
