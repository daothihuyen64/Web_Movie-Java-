package com.webxemphim.demo.controller;

import com.webxemphim.demo.dto.EpisodeDTO;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/episodes")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @PostMapping("/add")
    public ResponseEntity<ResponseData> addEpisode(@RequestBody EpisodeDTO episodeDTO) {
        ResponseData responseData = episodeService.addEpisode(episodeDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
