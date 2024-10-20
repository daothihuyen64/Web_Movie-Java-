package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.PackagesDTO;
import com.webxemphim.demo.entity.Packages;
import com.webxemphim.demo.repository.PackagesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackagesService {

    @Autowired
    private PackagesInterface packagesRepository;

    // Thêm gói dịch vụ
    public PackagesDTO addPackage(PackagesDTO packagesDTO) {
        Packages packages = new Packages();
        packages.setPackageName(packagesDTO.getPackageName());
        packages.setPrice(packagesDTO.getPrice());
        packages.setAccessDuration(packagesDTO.getAccessDuration());
        packages.setDescription(packagesDTO.getDescription());

        Packages savedPackage = packagesRepository.save(packages);
        return convertToDTO(savedPackage);
    }

    // Lấy gói dịch vụ theo ID
    public Optional<PackagesDTO> getPackage(int id) {
        Optional<Packages> optionalPackage = packagesRepository.findById(id);
        return optionalPackage.map(this::convertToDTO);
    }

    // Lấy tất cả các gói dịch vụ
    public List<PackagesDTO> getPackages() {
        List<Packages> packagesList = packagesRepository.findAll();
        return packagesList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // // Cập nhật giá của gói dịch vụ
    // public Optional<PackagesDTO> updatePackage(int id, double newPrice) {
    //     Optional<Packages> optionalPackage = packagesRepository.findById(id);
    //     if (optionalPackage.isPresent()) {
    //         Packages packages = optionalPackage.get();
    //         packages.setPrice(newPrice);
    //         Packages updatedPackage = packagesRepository.save(packages);
    //         return Optional.of(convertToDTO(updatedPackage));
    //     }
    //     return Optional.empty();
    // }

    // Xóa gói dịch vụ
    public boolean deletePackage(int id) {
        Optional<Packages> optionalPackage = packagesRepository.findById(id);
        if (optionalPackage.isPresent()) {
            packagesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Chuyển đổi từ entity sang DTO
    private PackagesDTO convertToDTO(Packages packages) {
        return new PackagesDTO(
                packages.getId(),
                packages.getPackageName(),
                packages.getPrice(),
                packages.getAccessDuration(),
                packages.getDescription()
        );
    }
}
