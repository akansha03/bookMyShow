package com.firstProject.bookMyShow.repository;

import com.firstProject.bookMyShow.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    boolean existsByName(String name);

    Optional<Genre> findByName(String name);
}
