package com.webxemphim.demo.service;


import com.webxemphim.demo.dto.CountryDTO;
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
        ResponseData responseData = new ResponseData();

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
                responseData.setData(countryDTO);
            responseData.setDesc("Đã lấy quốc gia thành công!");
            responseData.setData(countryDTO);
        } 
        else {
            // Nếu không tìm thấy country, trả về ResponseData với lỗi
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy quốc gia!");
        }

        return responseData;
    }
}
