package com.webxemphim.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webxemphim.demo.enums.EmojiType;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.ReactService;

@RestController
@RequestMapping("/react")
public class ReactController {

    @Autowired
    private ReactService reactService;

    //Thêm 1 React cho 1 Comment
    @PostMapping("/add")
    public ResponseEntity<?> addReact(@RequestParam int userId, @RequestParam int commentId, @RequestParam EmojiType reactType) {

        ResponseData responseData = new ResponseData();
        responseData.setSuccess(reactService.addReact(userId, commentId, reactType));
       
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //Lấy ra số lượng từng loại React cho 1 Comment
    @GetMapping("/{commentId}")
    public ResponseEntity<Map<EmojiType, Integer>> getReact(@PathVariable int commentId) {
        Map<EmojiType, Integer> reactCounts = reactService.getReact(commentId);
        return ResponseEntity.ok(reactCounts);
    }

    //Xoá 1 React ra khỏi 1 Comment
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteReact(@RequestParam int userId, @RequestParam int commentId) {

        ResponseData responseData = new ResponseData();
        responseData.setSuccess(reactService.deleteReact(userId, commentId));

        reactService.deleteReact(userId, commentId);
        
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
