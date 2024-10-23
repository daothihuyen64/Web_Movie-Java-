package com.webxemphim.demo.controller;

import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.ReleaseYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/releaseyear")
public class ReleaseYearController {

    @Autowired
    private ReleaseYearService releaseYearService;

    // Endpoint để lấy danh sách phim theo ReleaseYearID
    @GetMapping("/{releaseYearId}")
    public ResponseData getReleaseYear(@PathVariable int releaseYearId) {
        return releaseYearService.getReleaseYearById(releaseYearId);
    }
}
