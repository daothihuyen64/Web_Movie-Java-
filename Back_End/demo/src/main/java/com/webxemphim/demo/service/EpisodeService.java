package com.webxemphim.demo.service;

import com.webxemphim.demo.dto.EpisodeDTO;
import com.webxemphim.demo.entity.Episode;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.payload.ResponseData;
import com.webxemphim.demo.repository.EpisodeInterface;
import com.webxemphim.demo.repository.MovieInterface;
import com.webxemphim.demo.repository.TransactionInterface;
import com.webxemphim.demo.dto.TransactionDTO;
import com.webxemphim.demo.entity.Transaction;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.sql.Date;
import java.util.List;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeInterface episodeRepository;

    @Autowired
    private MovieInterface movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionInterface transactionRepository;

    // Hàm thêm tập phim
    public ResponseData addEpisode(EpisodeDTO episodeDTO) {
        try {
            // Kiểm tra xem bộ phim có tồn tại không
            Optional<Movie> optionalMovie = movieRepository.findById(episodeDTO.getMovieId());
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();

                // Kiểm tra nếu phim có status = 0 thì không cho thêm tập
                if (movie.getStatus() == 0) {
                    return new ResponseData(HttpStatus.BAD_REQUEST.value(), false, "Không thể thêm tập cho phim đã bị vô hiệu hóa!", null);
                }

                // Kiểm tra xem tập phim với episodeNumber và movieId đã tồn tại chưa
                Optional<Episode> existingEpisode = episodeRepository.findByEpisodeNumberAndMovieId(episodeDTO.getEpisodeNumber(), episodeDTO.getMovieId());
                if (existingEpisode.isPresent()) {
                    return new ResponseData(HttpStatus.CONFLICT.value(), false, "Tập phim này đã tồn tại!", null);
                }

                // Ánh xạ từ EpisodeDTO sang Entity Episode
                Episode episode = new Episode();
                episode.setEpisodeNumber(episodeDTO.getEpisodeNumber());
                episode.setEpisodeUrl(episodeDTO.getEpisodeUrl());
                episode.setMovie(movie);

                // Lưu tập phim vào database
                episodeRepository.save(episode);

                return new ResponseData(200, true, "Thêm tập phim thành công!", episodeDTO);
            } else {
                return new ResponseData(HttpStatus.NOT_FOUND.value(), false, "Bộ phim không tồn tại!", null);
            }
        } catch (Exception e) {
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false,"Lỗi khi thêm tập phim: " + e.getMessage(), null);
        }
    }

    public ResponseData startEpisode(int userId, int episodeNumber, int movieId) {
        try {
            // Lấy danh sách các giao dịch hợp lệ của user, sắp xếp theo endDate giảm dần
            List<Transaction> validTransactions = transactionRepository.findValidTransactions(userId, new Date(System.currentTimeMillis()));
    
            if (!validTransactions.isEmpty()) {
                // Lấy giao dịch mới nhất (giao dịch có endDate xa nhất)
                Transaction latestTransaction = validTransactions.get(0);
                TransactionDTO transactionDTO = modelMapper.map(latestTransaction, TransactionDTO.class);
    
                // Tìm tập phim dựa trên episodeNumber và movieId
                Optional<Episode> optionalEpisode = episodeRepository.findByEpisodeNumberAndMovieId(episodeNumber, movieId);
                if (optionalEpisode.isPresent()) {
                    Episode episode = optionalEpisode.get();
                    Movie movie = episode.getMovie();
    
                    // Tăng lượt xem của bộ phim
                    movie.setViews(movie.getViews() + 1);
                    movieRepository.save(movie);  // Lưu lại bộ phim sau khi tăng lượt xem
    
                    // Map Episode sang EpisodeDTO để lấy URL
                    EpisodeDTO episodeDTO = modelMapper.map(episode, EpisodeDTO.class);
                    // Nếu tồn tại giao dịch hợp lệ, cho phép phát tập phim
                    return new ResponseData(HttpStatus.OK.value(), true, "Phát tập phim thành công và tăng lượt xem cho bộ phim!", episodeDTO.getEpisodeUrl());
                } else {
                    return new ResponseData(HttpStatus.NOT_FOUND.value(), false, "Tập phim không tồn tại!", null);
                }
            } else {
                // Nếu không tồn tại giao dịch hợp lệ, báo lỗi
                return new ResponseData(HttpStatus.FORBIDDEN.value(), false, "Giao dịch của bạn đã hết hạn hoặc không tồn tại!", null);
            }
        } catch (Exception e) {
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Lỗi khi phát tập phim: " + e.getMessage(), null);
        }
    }
    

    public ResponseData updateUrlEpisode(int episodeId, EpisodeDTO episodeDTO) {
        try {
            // Kiểm tra xem tập phim có tồn tại không
            Optional<Episode> optionalEpisode = episodeRepository.findById(episodeId);
            if (optionalEpisode.isPresent()) {
                Episode episode = optionalEpisode.get();
                Movie movie = episode.getMovie();
    
                // Kiểm tra nếu phim có status = 0 thì không cho sửa tập
                if (movie.getStatus() == 0) {
                    return new ResponseData(HttpStatus.BAD_REQUEST.value(), false, "Không thể sửa tập phim cho phim đã bị vô hiệu hóa!", null);
                }
    
                // Cập nhật thông tin URL từ EpisodeDTO
                if(episodeDTO.getEpisodeUrl() != null)episode.setEpisodeUrl(episodeDTO.getEpisodeUrl());
                if(episodeDTO.getEpisodeNumber() != 0)episode.setEpisodeNumber(episodeDTO.getEpisodeNumber());
                episodeRepository.save(episode);
    
                // Tạo đối tượng DTO để trả về
                EpisodeDTO updatedDTO = new EpisodeDTO();
                updatedDTO.setEpisodeNumber(episode.getEpisodeNumber());
                updatedDTO.setEpisodeUrl(episode.getEpisodeUrl());
                updatedDTO.setMovieId(movie.getId());
    
                return new ResponseData(HttpStatus.OK.value(), true, "Cập nhật URL tập phim thành công!", updatedDTO);
            } else {
                return new ResponseData(HttpStatus.NOT_FOUND.value(), false, "Tập phim không tồn tại!", null);
            }
        } catch (Exception e) {
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false,"Lỗi khi cập nhật URL tập phim: " + e.getMessage(), null);
        }
    }

    public ResponseData getAllEpisodes(int movieId) {
        try {
            // Kiểm tra xem bộ phim có tồn tại không
            Optional<Movie> optionalMovie = movieRepository.findById(movieId);
            if (optionalMovie.isPresent()) {
                // Lấy danh sách tất cả các tập của bộ phim
                List<Episode> episodes = episodeRepository.findAllByMovieIdOrderByEpisodeNumberAsc(movieId);
    
                if (episodes.isEmpty()) {
                    return new ResponseData(HttpStatus.OK.value(), true, "Bộ phim chưa có tập nào!", List.of());
                }
    
                // Map danh sách Entity Episode sang DTO
                List<EpisodeDTO> episodeDTOs = episodes.stream()
                        .map(episode -> {
                            EpisodeDTO episodeDTO = modelMapper.map(episode, EpisodeDTO.class);
                            episodeDTO.setMovieId(movieId); // Đảm bảo MovieId được set đúng
                            return episodeDTO;
                        })
                        .toList();
    
                return new ResponseData(HttpStatus.OK.value(), true, "Lấy danh sách tập phim thành công!", episodeDTOs);
            } else {
                return new ResponseData(HttpStatus.NOT_FOUND.value(), false, "Bộ phim không tồn tại!", null);
            }
        } catch (Exception e) {
            return new ResponseData(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Lỗi khi lấy danh sách tập phim: " + e.getMessage(), null);
        }
    }
}