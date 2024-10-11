package com.webxemphim.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.service.MovieService;

import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    // Thêm movie
    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie newMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(newMovie);
    }

    // Lấy thông tin movie theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable int id) {
        Optional<Movie> movie = movieService.getMovie(id);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cập nhật movie
    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(movie);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa movie
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        boolean isDeleted = movieService.deleteMovie(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Phát phim (trả về URL tập phim)
    @GetMapping("/start/{movieId}/{episodeId}")
    public ResponseEntity<String> startMovie(@PathVariable int movieId, @PathVariable int episodeId) {
        String episodeUrl = movieService.startMovie(movieId, episodeId);
        if (episodeUrl != null) {
            return ResponseEntity.ok(episodeUrl);
        }
        return ResponseEntity.notFound().build();
    }

    // Trả về số lượt xem
    @GetMapping("/views/{id}")
    public ResponseEntity<Integer> getView(@PathVariable int id) {
        int views = movieService.getView(id);
        return ResponseEntity.ok(views);
    }
}
