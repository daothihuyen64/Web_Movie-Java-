

package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.ActorDTO;
import com.webxemphim.demo.dto.SimpleMovieDTO;
import com.webxemphim.demo.entity.Actor;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.entity.Movie_Actor;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.ActorInterface;
import com.webxemphim.demo.repository.MovieActorInterface;
import com.webxemphim.demo.repository.MovieInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorService {

    @Autowired
    private ActorInterface actorRepository;

    @Autowired
    private MovieInterface movieRepository;

    @Autowired
    private MovieActorInterface movieActorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseData addActor(ActorDTO actorDTO) {
        ResponseData responseData = new ResponseData();
        try {
            Actor actor = modelMapper.map(actorDTO, Actor.class);
            actorRepository.save(actor);
            ActorDTO savedActorDTO = modelMapper.map(actor, ActorDTO.class);
            responseData.setData(savedActorDTO);
            responseData.setDesc("Thêm diễn viên thành công");
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Thêm diễn viên thất bại: " + e.getMessage());
        }
        return responseData;
    }

    public ResponseData getActor(int id) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Actor> optionalActor = actorRepository.findById(id);
            if (optionalActor.isPresent()) {
                Actor actor = optionalActor.get();
                List<SimpleMovieDTO> movies = actor.getMovie_actorList().stream()
                        .map(movieActor -> modelMapper.map(movieActor.getMovie(), SimpleMovieDTO.class))
                        .collect(Collectors.toList());

                ActorDTO actorDTO = modelMapper.map(actor, ActorDTO.class);
                actorDTO.setMovies(movies);
                responseData.setData(actorDTO);
                responseData.setDesc("Lấy thông tin diễn viên thành công");
            } else {
                responseData.setStatus(HttpStatus.NOT_FOUND.value());
                responseData.setSuccess(false);
                responseData.setDesc("Diễn viên không tồn tại");
            }
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi lấy thông tin diễn viên: " + e.getMessage());
        }
        return responseData;
    }

    public ResponseData updateActor(int actorId, int movieId) {
        ResponseData responseData = new ResponseData();
        try {
            Optional<Actor> optionalActor = actorRepository.findById(actorId);
            if (optionalActor.isPresent()) {
                Actor actor = optionalActor.get();
                Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("Phim không tồn tại"));
    
                Movie_Actor movieActor = new Movie_Actor();
                movieActor.setActor(actor);
                movieActor.setMovie(movie);
    
                actor.getMovie_actorList().add(movieActor);
                movieActorRepository.save(movieActor);
    
                // Ép kiểu thủ công từ Object sang ActorDTO
                ActorDTO updatedActorDTO = (ActorDTO) getActor(actorId).getData();
                responseData.setData(updatedActorDTO);
                responseData.setDesc("Cập nhật phim cho diễn viên thành công");
            } else {
                responseData.setStatus(HttpStatus.NOT_FOUND.value());
                responseData.setSuccess(false);
                responseData.setDesc("Diễn viên không tồn tại");
            }
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi cập nhật diễn viên: " + e.getMessage());
        }
        return responseData;
    }
}