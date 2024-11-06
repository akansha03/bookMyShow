package com.firstProject.bookMyShow.repository;

import com.firstProject.bookMyShow.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movies, Integer> {

    boolean existsByName(String name);
    Movies findByName(String name);
    List<Movies> findByGenreName(String name);
    List<Movies> findByFormatName(String format);
    List<Movies> findByGenreNameAndFormatName(String genre, String format);
}
