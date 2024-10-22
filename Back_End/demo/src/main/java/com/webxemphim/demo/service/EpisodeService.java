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
        ResponseData responseData = new ResponseData();

        try {
            // Kiểm tra xem bộ phim có tồn tại không
            Optional<Movie> optionalMovie = movieRepository.findById(episodeDTO.getMovieId());
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();

                // Kiểm tra nếu phim có status = 0 thì không cho thêm tập
                if (movie.getStatus() == 0) {
                    responseData.setStatus(HttpStatus.BAD_REQUEST.value());
                    responseData.setSuccess(false);
                    responseData.setDesc("Không thể thêm tập cho phim đã bị vô hiệu hóa!");
                    return responseData;
                }

                // Kiểm tra xem tập phim với episodeNumber và movieId đã tồn tại chưa
                Optional<Episode> existingEpisode = episodeRepository.findByEpisodeNumberAndMovieId(episodeDTO.getEpisodeNumber(), episodeDTO.getMovieId());
                if (existingEpisode.isPresent()) {
                    responseData.setStatus(HttpStatus.CONFLICT.value());
                    responseData.setSuccess(false);
                    responseData.setDesc("Tập phim này đã tồn tại!");
                    return responseData;
                }

                // Ánh xạ từ EpisodeDTO sang Entity Episode
                Episode episode = modelMapper.map(episodeDTO, Episode.class);
                episode.setMovie(movie);

                // Lưu tập phim vào database
                episodeRepository.save(episode);

                responseData.setData(episodeDTO);
                responseData.setDesc("Thêm tập phim thành công!");
            } else {
                responseData.setStatus(HttpStatus.NOT_FOUND.value());
                responseData.setSuccess(false);
                responseData.setDesc("Bộ phim không tồn tại!");
            }
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi thêm tập phim: " + e.getMessage());
        }

        return responseData;
    }

    //Hàm phát tập phim
    public ResponseData startEpisode(int userId, int episodeId) {
        ResponseData responseData = new ResponseData();
        try {
            // Lấy danh sách các giao dịch hợp lệ của user, sắp xếp theo endDate giảm dần
            List<Transaction> validTransactions = transactionRepository.findValidTransactions(userId, new Date(System.currentTimeMillis()));

            if (!validTransactions.isEmpty()) {
                // Lấy giao dịch mới nhất (giao dịch có endDate xa nhất)
                Transaction latestTransaction = validTransactions.get(0);
                TransactionDTO transactionDTO = modelMapper.map(latestTransaction, TransactionDTO.class);

                // Nếu tồn tại giao dịch hợp lệ, cho phép phát tập phim
                responseData.setSuccess(true);
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setDesc("Phát tập phim thành công!");
                responseData.setData(transactionDTO);
            } 
            else {
                // Nếu không tồn tại giao dịch hợp lệ, báo lỗi
                responseData.setSuccess(false);
                responseData.setStatus(HttpStatus.FORBIDDEN.value());
                responseData.setDesc("Giao dịch của bạn đã hết hạn hoặc không tồn tại.");
            }
        } 
        catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setDesc("Lỗi khi phát tập phim: " + e.getMessage());
        }

        return responseData;
    }

    public ResponseData updateUrlEpisode(int episodeId, EpisodeDTO episodeDTO) {
        ResponseData responseData = new ResponseData();
    
        try {
            // Kiểm tra xem tập phim có tồn tại không
            Optional<Episode> optionalEpisode = episodeRepository.findById(episodeId);
            if (optionalEpisode.isPresent()) {
                Episode episode = optionalEpisode.get();
                Movie movie = episode.getMovie();
    
                // Kiểm tra nếu phim có status = 0 thì không cho sửa tập
                if (movie.getStatus() == 0) {
                    responseData.setStatus(HttpStatus.BAD_REQUEST.value());
                    responseData.setSuccess(false);
                    responseData.setDesc("Không thể sửa tập phim cho phim đã bị vô hiệu hóa!");
                    return responseData;
                }
    
                // Cập nhật thông tin URL từ EpisodeDTO
                episode.setEpisodeUrl(episodeDTO.getEpisodeUrl());
                episodeRepository.save(episode);
    
                // Tạo đối tượng DTO để trả về
                EpisodeDTO updatedDTO = new EpisodeDTO();
                updatedDTO.setEpisodeNumber(episode.getEpisodeNumber());
                updatedDTO.setEpisodeUrl(episode.getEpisodeUrl());
                updatedDTO.setMovieId(movie.getId());
    
                responseData.setSuccess(true);
                responseData.setStatus(HttpStatus.OK.value());
                responseData.setDesc("Cập nhật URL tập phim thành công!");
                responseData.setData(updatedDTO);
            } else {
                responseData.setStatus(HttpStatus.NOT_FOUND.value());
                responseData.setSuccess(false);
                responseData.setDesc("Tập phim không tồn tại!");
            }
        } catch (Exception e) {
            responseData.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseData.setSuccess(false);
            responseData.setDesc("Lỗi khi cập nhật URL tập phim: " + e.getMessage());
        }
    
        return responseData;
    }
}
