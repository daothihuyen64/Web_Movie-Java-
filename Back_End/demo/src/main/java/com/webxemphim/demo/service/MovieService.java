package com.webxemphim.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webxemphim.demo.entity.Episode;
import com.webxemphim.demo.entity.Movie;
import com.webxemphim.demo.repository.MovieInterface;

import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieInterface movieRepository;

    // Thêm movie
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Lấy thông tin movie theo ID
    public Optional<Movie> getMovie(int movieId) {
        return movieRepository.findById(movieId);
    }

    // Sửa thông tin movie
    public Movie updateMovie(Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(movie.getId());
        if (existingMovie.isPresent()) {
            Movie updatedMovie = existingMovie.get();
            updatedMovie.setMovieName(movie.getMovieName());
            updatedMovie.setPoster(movie.getPoster());
            updatedMovie.setTrailer(movie.getTrailer());
            updatedMovie.setDescription(movie.getDescription());
            updatedMovie.setRatingMean(movie.getRatingMean());
            updatedMovie.setDirector(movie.getDirector());
            updatedMovie.setTotalEpisodes(movie.getTotalEpisodes());
            updatedMovie.setViews(movie.getViews());
            updatedMovie.setStatus(movie.getStatus());
            return movieRepository.save(updatedMovie);
        }
        return null;
    }

    // Xóa movie (cập nhật trạng thái thành "inactive")
    public boolean deleteMovie(int movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            Movie updatedMovie = movie.get();
            updatedMovie.setStatus(0); // Giả sử 0 là trạng thái "inactive"
            movieRepository.save(updatedMovie);
            return true;
        }
        return false;
    }

    // Phát video từ URL (trả về URL tập phim dựa trên MovieID và EpisodeID)
    public String startMovie(int movieId, int episodeId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            return movie.get().getEpisodeList().stream()
                    .filter(episode -> episode.getId() == episodeId)
                    .map(Episode::getEpisodeUrl)
                    .findFirst().orElse(null);
        }
        return null;
    }

    // Trả về số lượt xem của một movie
    public int getView(int movieId) {
        return movieRepository.findById(movieId)
                .map(Movie::getViews)
                .orElse(0);
    }
}
