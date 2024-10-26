package com.webxemphim.demo.service;


import com.webxemphim.demo.dto.CountryDTO;
import com.webxemphim.demo.dto.SimpleCountryDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Country;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.CountryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryInterface countryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseData getCountryById(int countryId) {
        Optional<Country> country = countryRepository.findById(countryId);

        if (country.isPresent()) {
            // Lấy danh sách movie của country, lọc phim có status != 0 và ánh xạ sang SimpleMovieDTO
            List<SimpleMovieDTO> movieDTOList = country.get().getMovieList().stream()
                    .filter(movie -> movie.getStatus() != 0) // Lọc các phim có status khác 0
                    .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class))
                    .collect(Collectors.toList());

            // Đóng gói dữ liệu thành CountryDTO
            // Trả về ResponseData thành công với dữ liệu CountryDTO
            CountryDTO countryDTO = modelMapper.map(country, CountryDTO.class);
                countryDTO.setMovieList(movieDTOList);
            return new ResponseData(200, true, "Đã lấy quốc gia thành công!", countryDTO);
        } 
        else {
            // Nếu không tìm thấy country, trả về ResponseData với lỗi
            return new ResponseData(404, false, "Không tìm thấy quốc gia!", null);
        }
    }

    public ResponseData getAllCountry() {
        List<Country> countries = countryRepository.findAll(); // Lấy danh sách tất cả thể loại

        List<SimpleCountryDTO> countryDTOs = countries.stream()
                .map(country -> {
                    SimpleCountryDTO countryDTO = new SimpleCountryDTO();
                    countryDTO.setId(country.getId()); // Cập nhật ID
                    countryDTO.setCountryName(country.getCountryName()); // Cập nhật tên
                    return countryDTO;
                })
                .collect(Collectors.toList());
        return new ResponseData(200, true, "Lấy tất cả các quốc gia thành công!", countryDTOs);
    }
}