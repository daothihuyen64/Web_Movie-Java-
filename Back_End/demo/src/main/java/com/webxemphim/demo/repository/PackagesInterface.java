package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagesInterface extends JpaRepository<Packages, Integer> {
}
