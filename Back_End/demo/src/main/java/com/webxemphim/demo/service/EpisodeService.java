package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.EpisodeDTO;
import com.webxemphim.demo.entity.Episode;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.EpisodeInterface;
import com.webxemphim.demo.repository.MovieInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeInterface episodeRepository;

    @Autowired
    private MovieInterface movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseData addEpisode(EpisodeDTO episodeDTO) {
        ResponseData responseData = new ResponseData();

        try {
            // Kiểm tra xem bộ phim có tồn tại không
            Optional<Movie> optionalMovie = movieRepository.findById(episodeDTO.getMovieId());
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();

                // Ánh xạ từ EpisodeDTO sang Entity Episode
                Episode episode = modelMapper.map(episodeDTO, Episode.class);
                episode.setMovie(movie);

                // Lưu tập phim vào database
                episodeRepository.save(episode);

                responseData.setData(episodeDTO);
                responseData.setDesc("Thêm tập phim thành công");
            } else {
                responseData.setStatus(HttpStatus.NOT_FOUND.value());
                responseData.setSuccess(false);
                responseData.setDesc("Bộ phim không tồn tại");
            }
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi thêm tập phim: " + e.getMessage());
        }

        return responseData;
    }
}
