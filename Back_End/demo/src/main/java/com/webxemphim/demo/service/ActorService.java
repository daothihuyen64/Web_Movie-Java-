package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.ActorDTO;
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

    public ResponseData updateActor(ActorDTO nameActor, int movieId) {
        ResponseData responseData = new ResponseData();
        try {
            // Tìm diễn viên theo tên
            Optional<Actor> optionalActor = actorRepository.findByNameActor(nameActor.getNameActor());
            Actor actor;
    
            if (!optionalActor.isPresent()) {
                // Diễn viên không tồn tại, tạo mới
                actor = new Actor();
                actor.setNameActor(nameActor.getNameActor());
                actorRepository.save(actor);
            } else {
                // Diễn viên đã tồn tại
                actor = optionalActor.get();
            }
    
            // Tìm phim cần liên kết
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("Phim không tồn tại!"));
    
            // Kiểm tra nếu liên kết diễn viên-phim đã tồn tại
            boolean exists = movieActorRepository.existsByActorAndMovie(actor, movie);
            if (exists) {
                responseData.setStatus(HttpStatus.BAD_REQUEST.value());
                responseData.setSuccess(false);
                responseData.setDesc("Diễn viên đã được thêm vào phim này trước đó!");
                return responseData;
            }
    
            // Tạo liên kết giữa diễn viên và phim nếu chưa tồn tại
            Movie_Actor movieActor = new Movie_Actor();
            movieActor.setActor(actor);
            movieActor.setMovie(movie);
            movieActorRepository.save(movieActor);
    
            // Chuyển đổi Actor thành ActorDTO để trả về
            ActorDTO actorDTOResponse = modelMapper.map(actor, ActorDTO.class);
            responseData.setData(actorDTOResponse);
    
            // Đặt thông báo thành công phù hợp
            if (!optionalActor.isPresent()) {
                responseData.setDesc("Thêm mới diễn viên và cập nhật phim thành công!");
            } else {
                responseData.setDesc("Cập nhật diễn viên cho phim thành công!");
            }
    
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi cập nhật hoặc thêm mới diễn viên: " + e.getMessage());
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
}
