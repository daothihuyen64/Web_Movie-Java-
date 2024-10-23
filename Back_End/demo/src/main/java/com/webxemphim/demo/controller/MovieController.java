package com.webxemphim.demo.controller;

import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO movieDTO) {
        ResponseData responseData = movieService.addMovie(movieDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable int id) {
        ResponseData responseData = movieService.getMovie(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody MovieDTO movieDTO) {
        ResponseData responseData = movieService.updateMovie(id, movieDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        ResponseData responseData = movieService.deleteMovie(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getMovieRanking() {
        ResponseData responseData = movieService.getMovieRanking();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    
    @GetMapping("/views/{id}")
    public ResponseEntity<?> getView(@PathVariable int id) {
        ResponseData responseData = movieService.getView(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    
    @GetMapping("/trailer/{id}")
    public ResponseEntity<?> startTrailer(@PathVariable int id) {
        ResponseData responseData = movieService.startTrailer(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
