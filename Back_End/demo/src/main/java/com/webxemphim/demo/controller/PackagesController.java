package com.webxemphim.demo.controller;

import com.webxemphim.demo.dto.PackagesDTO;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.service.PackagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packages")
public class PackagesController {

    @Autowired
    private PackagesService packagesService;

    // Thêm gói dịch vụ mới
    @PostMapping("/add")
    public ResponseEntity<?> addPackage(@RequestBody PackagesDTO packagesDTO) {
        ResponseData responseData = packagesService.addPackage(packagesDTO);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Lấy thông tin gói dịch vụ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPackage(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        responseData.setData(packagesService.getPackage(id).orElse(null));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // // Cập nhật giá của gói dịch vụ
    // @PutMapping("/update/{id}")
    // public ResponseEntity<?> updatePackage(@PathVariable int id, @RequestBody double newPrice) {
    //     ResponseData responseData = new ResponseData();
    //     responseData.setData(packagesService.updatePackage(id, newPrice).orElse(null));
    //     return new ResponseEntity<>(responseData, HttpStatus.OK);
    // }

    // Xóa gói dịch vụ
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable int id) {
        ResponseData responseData = packagesService.deletePackage(id);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Lấy danh sách tất cả các gói dịch vụ
    @GetMapping
    public ResponseEntity<?> getPackages() {
        ResponseData responseData = new ResponseData();
        responseData.setData(packagesService.getPackages());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
