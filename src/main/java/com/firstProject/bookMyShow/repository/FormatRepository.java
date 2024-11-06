package com.firstProject.bookMyShow.repository;

import com.firstProject.bookMyShow.model.Format;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormatRepository extends JpaRepository<Format, Integer> {

    boolean existsByName(String name);

    Optional<Format> findByName(String name);
}
