package com.firstProject.bookMyShow.service.movies;

import com.firstProject.bookMyShow.model.Movies;
import com.firstProject.bookMyShow.payloads.AddMovieRequest;
import com.firstProject.bookMyShow.payloads.UpdateMovieRequest;

import java.util.List;

public interface MovieService {

    List<Movies> getAllMovies();
    Movies addMovie(Movies movie);
    Movies modifyMovie(UpdateMovieRequest movie, Integer movieId);
    void deleteMovie(Integer movieId);
    Movies getMovie(Integer movieId);
    Movies getMoviesByName(String name);
    List<Movies> getMoviesByGenre(String genre);
    List<Movies> getMoviesByFormat(String format);
    List<Movies> getMoviesByGenreAndFormat(String genre, String format);
}
