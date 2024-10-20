package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.CountryDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Country;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.CountryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryInterface countryRepository;

    // Phương thức để truy xuất movie theo CountryID
    public ResponseData getCountryById(int countryId) {
        ResponseData responseData = new ResponseData();

        Optional<Country> country = countryRepository.findById(countryId);

        if (country.isPresent()) {
            // Lấy danh sách movie của country và ánh xạ sang SimpleMovieDTO
            List<SimpleMovieDTO> movieDTOList = country.get().getMovieList().stream()
                    .map(movie -> new SimpleMovieDTO(
                            movie.getId(),
                            movie.getMovieName(),
                            movie.getPoster()
                    ))
                    .collect(Collectors.toList());

            // Đóng gói dữ liệu thành CountryDTO
            CountryDTO countryDTO = new CountryDTO(country.get().getId(), country.get().getCountryName(), movieDTOList);

            // Trả về ResponseData thành công với dữ liệu CountryDTO
            responseData.setStatus(200);
            responseData.setSuccess(true);
            responseData.setDesc("Dã lấy quốc gia thành công!");
            responseData.setData(countryDTO);
        } else {
            // Nếu không tìm thấy country, trả về ResponseData với lỗi
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy quốc gia!");
        }

        return responseData;
    }
}
