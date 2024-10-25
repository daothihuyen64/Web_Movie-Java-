package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Release_Year;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseYearInterface extends JpaRepository<Release_Year, Integer> {

}
