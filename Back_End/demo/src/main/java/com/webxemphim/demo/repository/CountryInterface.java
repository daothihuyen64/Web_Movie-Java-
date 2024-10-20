package com.webxemphim.demo.repository;

import com.webxemphim.demo.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryInterface extends JpaRepository<Country, Integer> {
}
