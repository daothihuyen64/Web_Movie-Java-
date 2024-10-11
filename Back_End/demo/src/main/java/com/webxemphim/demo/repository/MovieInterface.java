package com.webxemphim.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webxemphim.demo.entity.Movie;


@Repository
public interface MovieInterface extends JpaRepository<Movie, Integer>{
    
}
