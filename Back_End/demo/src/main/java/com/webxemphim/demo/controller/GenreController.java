
package com.webxemphim.demo.controller;

import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    // Lấy thông tin thể loại và danh sách các movie liên quan theo genreID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getGenre(@PathVariable int id) {
        ResponseData responseData = genreService.getGenreById(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
