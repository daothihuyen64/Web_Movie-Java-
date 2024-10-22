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
    @PostMapping("/start/{userId}/{episodeId}")
    public ResponseEntity<ResponseData> startEpisode(@PathVariable int userId, @PathVariable int episodeId) {
        ResponseData responseData = episodeService.startEpisode(userId, episodeId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // API cập nhật URL của tập phim
    @PutMapping("/update-url/{episodeId}")
    public ResponseEntity<ResponseData> updateUrlEpisode(@PathVariable int episodeId, @RequestBody EpisodeDTO episode) {
        ResponseData responseData = episodeService.updateUrlEpisode(episodeId, episode);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
