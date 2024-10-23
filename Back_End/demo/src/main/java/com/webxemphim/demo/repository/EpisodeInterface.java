
package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Episode;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeInterface extends JpaRepository<Episode, Integer> {
    Optional<Episode> findByEpisodeNumberAndMovieId(int episodeNumber, int movieId);
}
