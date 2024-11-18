package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.ActorDTO;
import com.webxemphim.demo.dto.DetailActorDTO;
import com.webxemphim.demo.dto.MovieDTO;
import com.webxemphim.demo.dto.MovieDetailDTO;
import com.webxemphim.demo.dto.SimpleActorDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
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

    // public ResponseData addActor(ActorDTO actorDTO) {
    //     ResponseData responseData = new ResponseData();
    //     try {
    //         Actor actor = modelMapper.map(actorDTO, Actor.class);
    //         actorRepository.save(actor);
    //         ActorDTO savedActorDTO = modelMapper.map(actor, ActorDTO.class);
    //         responseData.setData(savedActorDTO);
    //         responseData.setDesc("Thêm diễn viên thành công");
    //     } catch (Exception e) {
    //         responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         responseData.setSuccess(false);
    //         responseData.setDesc("Thêm diễn viên thất bại: " + e.getMessage());
    //     }
    //     return responseData;
    // }



    public ResponseData getActor(int id){
        try {
            Optional<Actor> optionalActor = actorRepository.findById(id);
            if(optionalActor.isPresent()){
                Actor actor = optionalActor.get();
                // Lọc ra các phim có status khác 0
                List<SimpleMovieDTO> movies = actor.getMovie_actorList().stream()
                        .map(movieActor -> movieActor.getMovie()) // Lấy movie từ Movie_Actor
                        .filter(movie -> movie.getStatus() != 0) // Lọc những phim có status khác 0
                        .map(movie -> modelMapper.map(movie, SimpleMovieDTO.class)) // Map Movie sang SimpleMovieDTO
                        .collect(Collectors.toList());
    
                ActorDTO actorDTO = modelMapper.map(actor, ActorDTO.class);
                actorDTO.setMovies(movies);
                return new ResponseData(200, true, "Lấy thông tin diễn viên thành công!", actorDTO);
            } 
            else{
                return new ResponseData(HttpStatus.NOT_FOUND.value(), false, "Diễn viên không tồn tại!", null);
            }
        }
        catch(Exception e){
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Lỗi khi lấy thông tin diễn viên: " + e.getMessage(), null);
        }
    }

    public ResponseData updateActors(List<SimpleActorDTO> actorDTOList, int movieId) {
        ResponseData responseData = new ResponseData();
        List<SimpleActorDTO> updatedActors = new ArrayList<>();
    
        try {
            // Tìm phim cần liên kết
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("Phim không tồn tại!"));
    
            for (SimpleActorDTO actorDTO : actorDTOList) {
                Optional<Actor> optionalActor = actorRepository.findByNameActor(actorDTO.getNameActor());
                Actor actor;
    
                if (!optionalActor.isPresent()) {
                    // Diễn viên không tồn tại, tạo mới
                    actor = new Actor();
                    actor.setNameActor(actorDTO.getNameActor());
                    actorRepository.save(actor);
                } else {
                    // Diễn viên đã tồn tại
                    actor = optionalActor.get();
                }
    
                // Kiểm tra nếu liên kết diễn viên-phim đã tồn tại
                boolean exists = movieActorRepository.existsByActorAndMovie(actor, movie);
                if (!exists) {
                    // Tạo liên kết giữa diễn viên và phim nếu chưa tồn tại
                    Movie_Actor movieActor = new Movie_Actor();
                    movieActor.setActor(actor);
                    movieActor.setMovie(movie);
                    movieActorRepository.save(movieActor);
                }
    
                // Chuyển đổi Actor thành ActorDTO và thêm vào danh sách đã cập nhật
                SimpleActorDTO updatedActorDTO = modelMapper.map(actor, SimpleActorDTO.class);
                updatedActors.add(updatedActorDTO);
            }
    
            responseData.setData(updatedActors);
            responseData.setDesc("Cập nhật danh sách diễn viên thành công!");
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi cập nhật danh sách diễn viên: " + e.getMessage());
        }
        return responseData;
    }
    
    public ResponseData getAllActors() {
        List<SimpleActorDTO> actors = actorRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Actor::getNameActor))
                .map(actor -> modelMapper.map(actor, SimpleActorDTO.class))
                .collect(Collectors.toList());
    
        return new ResponseData(200, true, "Lấy danh sách tất cả diễn viên thành công!", actors);
    }
    public ResponseData getAllMovieActor(int actorId) {
        try {
            // Tìm diễn viên theo ID
            Optional<Actor> optionalActor = actorRepository.findById(actorId);
            if (optionalActor.isPresent()) {
                Actor actor = optionalActor.get();
    
                // Lấy danh sách các bộ phim mà diễn viên đã tham gia và sắp xếp theo thứ tự từ điển
                List<MovieDTO> movies = actor.getMovie_actorList().stream()
                        .map(Movie_Actor::getMovie) // Lấy movie từ Movie_Actor
                        .map(movie -> modelMapper.map(movie, MovieDTO.class)) // Map Movie sang MovieDTO
                        .sorted(Comparator.comparing(MovieDTO::getMovieName)) // Sắp xếp theo tên phim
                        .collect(Collectors.toList());
    
                // Map Actor sang DetailActorDTO
                DetailActorDTO detailActorDTO = modelMapper.map(actor, DetailActorDTO.class);
                detailActorDTO.setMovies(movies);
    
                return new ResponseData(HttpStatus.OK.value(), true, "Lấy danh sách phim diễn viên đã tham gia thành công!", detailActorDTO);
            } else {
                return new ResponseData(HttpStatus.NOT_FOUND.value(), false, "Diễn viên không tồn tại!", null);
            }
        } catch (Exception e) {
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false,
                    "Lỗi khi lấy danh sách phim diễn viên đã tham gia: " + e.getMessage(), null);
        }
    }

}
