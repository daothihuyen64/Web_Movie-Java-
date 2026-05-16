package com.webxemphim.demo.controller;

import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.ReleaseYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/releaseyear")
public class ReleaseYearController {

    @Autowired
    private ReleaseYearService releaseYearService;

    // Endpoint để lấy danh sách phim theo ReleaseYearID
    @GetMapping("/{releaseYearId}")
    public ResponseEntity<ResponseData> getReleaseYear(@PathVariable int releaseYearId) {
        ResponseData responseData = releaseYearService.getReleaseYearById(releaseYearId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseData> getAllReleaseYear() {
        ResponseData responseData = releaseYearService.getAllReleaseYear();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping("/trending")
    public ResponseEntity<ResponseData> getTrendingMovies() {
        ResponseData responseData = releaseYearService.trendingMovie();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping("/new-releases")
    public ResponseEntity<ResponseData> getNewReleaseMovies() {
        ResponseData responseData = releaseYearService.newReleaseMovie();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
