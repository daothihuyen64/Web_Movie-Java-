
package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeInterface extends JpaRepository<Episode, Integer> {
}
