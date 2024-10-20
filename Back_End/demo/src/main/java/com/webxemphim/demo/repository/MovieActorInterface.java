package com.webxemphim.demo.repository;
import com.webxemphim.demo.entity.Movie_Actor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieActorInterface extends CrudRepository<Movie_Actor, Integer> {
}
