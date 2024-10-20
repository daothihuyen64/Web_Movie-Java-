

package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorInterface extends JpaRepository<Actor, Integer> {
}
