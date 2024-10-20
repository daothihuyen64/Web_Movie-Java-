package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.ReleaseYearDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Release_Year;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.ReleaseYearInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReleaseYearService {
    
    @Autowired
    private ReleaseYearInterface releaseYearRepository;

    public ResponseData getReleaseYearById(int releaseYearId) {
        ResponseData responseData = new ResponseData();

        Optional<Release_Year> releaseYear = releaseYearRepository.findById(releaseYearId);

        if (releaseYear.isPresent()) {
            List<SimpleMovieDTO> movieDTOList = releaseYear.get().getMovieList().stream()
                    .map(movie -> new SimpleMovieDTO(
                            movie.getId(),
                            movie.getMovieName(),
                            movie.getPoster()
                    ))
                    .collect(Collectors.toList());

            ReleaseYearDTO releaseYearDTO = new ReleaseYearDTO(
                    releaseYear.get().getId(),
                    releaseYear.get().getYear(), // Sử dụng getYear() thay vì getReleaseYearName()
                    movieDTOList
            );

            responseData.setStatus(200);
            responseData.setSuccess(true);
            responseData.setDesc("Đã lấy năm phát hành thành công!");
            responseData.setData(releaseYearDTO);
        } else {
            responseData.setStatus(404);
            responseData.setSuccess(false);
            responseData.setDesc("Không tìm thấy năm phát hành");
        }

        return responseData;
    }
}
