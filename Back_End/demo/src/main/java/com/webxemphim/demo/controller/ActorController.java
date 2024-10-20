

package com.webxemphim.demo.controller;

import com.webxemphim.demo.dto.ActorDTO;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("/add")
    public ResponseEntity<ResponseData> addActor(@RequestBody ActorDTO actorDTO) {
        ResponseData responseData = actorService.addActor(actorDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData> getActor(@PathVariable int id) {
        ResponseData responseData = actorService.getActor(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-movie")
    public ResponseEntity<ResponseData> updateActor(@PathVariable int id, @RequestBody int movieId) {
        ResponseData responseData = actorService.updateActor(id, movieId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    // @PutMapping("/{actorId}/movies/{movieId}")
    // public ResponseData updateActor(@PathVariable int actorId, @PathVariable int movieId) {
    //     return actorService.updateActor(actorId, movieId);
    // }
}
