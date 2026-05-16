package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.PackagesDTO;
import com.webxemphim.demo.entity.Packages;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.PackagesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackagesService {

    @Autowired
    private PackagesInterface packagesRepository;

    // Thêm gói dịch vụ
    public ResponseData addPackage(PackagesDTO packagesDTO) {
        try {
            // Kiểm tra thông tin đầu vào
            if (packagesDTO.getPackageName() == null || packagesDTO.getPackageName().isEmpty()
                    || packagesDTO.getPrice() <= 0 || packagesDTO.getAccessDuration() <= 0
                    || packagesDTO.getDescription() == null || packagesDTO.getDescription().isEmpty()) {
                return new ResponseData(HttpStatus.BAD_REQUEST.value(), false,
                        "Vui lòng nhập đầy đủ thông tin gói dịch vụ!", null);
            }

            // Kiểm tra xem packageName đã tồn tại chưa
            Optional<Packages> existingPackage = packagesRepository.findByPackageName(packagesDTO.getPackageName());
            if (existingPackage.isPresent()) {
                return new ResponseData(HttpStatus.BAD_REQUEST.value(), false,
                        "Gói dịch vụ với tên này đã tồn tại!", null);
            }

            // Tạo đối tượng Packages mới và lưu vào database
            Packages packages = new Packages();
            packages.setPackageName(packagesDTO.getPackageName());
            packages.setPrice(packagesDTO.getPrice());
            packages.setAccessDuration(packagesDTO.getAccessDuration());
            packages.setDescription(packagesDTO.getDescription());
            packages.setStatus(1); // Mặc định status = 1 khi thêm mới

            Packages savedPackage = packagesRepository.save(packages);
            return new ResponseData(200, true, "Thêm gói thành công!", convertToDTO(savedPackage));
        } catch (Exception e) {
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Thêm gói thất bại!", null);
        }
    }

    // Lấy gói dịch vụ theo ID (chỉ lấy gói có status = 1)
    public Optional<PackagesDTO> getPackage(int id) {
        Optional<Packages> optionalPackage = packagesRepository.findById(id);
        // Kiểm tra nếu gói dịch vụ tồn tại và có status = 1
        if (optionalPackage.isPresent() && optionalPackage.get().getStatus() != 0) {
            return optionalPackage.map(this::convertToDTO);
        }
        return Optional.empty(); // Trả về rỗng nếu gói không tồn tại hoặc không có status = 1
    }

    // Lấy tất cả các gói dịch vụ có status = 1
    public List<PackagesDTO> getPackages() {
        List<Packages> packagesList = packagesRepository.findAll()
                .stream()
                .filter(pkg -> pkg.getStatus() != 0) // Chỉ lấy các gói có status = 1
                .collect(Collectors.toList());
        return packagesList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Xóa gói dịch vụ (chỉ thay đổi status thành 0)
    public ResponseData deletePackage(int id) {
        Optional<Packages> optionalPackage = packagesRepository.findById(id);
        if (optionalPackage.isPresent()) {
            Packages packages = optionalPackage.get();
            packages.setStatus(0); // Thay đổi status thành 0
            packagesRepository.save(packages);
            return new ResponseData(200, true, "Xóa gói thành công!", null);
        }
        return new ResponseData(400, false, "Không thể xóa gói hoặc gói không tồn tại!", null);
    }

    // Chuyển đổi từ entity sang DTO
    private PackagesDTO convertToDTO(Packages packages) {
        return new PackagesDTO(
                packages.getId(),
                packages.getPackageName(),
                packages.getPrice(),
                packages.getAccessDuration(),
                packages.getDescription(),
                packages.getStatus()
        );
    }
        // Lấy tất cả các gói dịch vụ có trong database
        public List<PackagesDTO> getAllPackages() {
            List<Packages> packagesList = packagesRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
            return packagesList.stream().map(this::convertToDTO).collect(Collectors.toList());
        }
}
