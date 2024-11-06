package com.firstProject.bookMyShow.controller;

import com.firstProject.bookMyShow.exceptions.AlreadyExistsException;
import com.firstProject.bookMyShow.exceptions.ResourceNotFoundException;
import com.firstProject.bookMyShow.model.Movies;
import com.firstProject.bookMyShow.payloads.APIResponse;
import com.firstProject.bookMyShow.payloads.AddMovieRequest;
import com.firstProject.bookMyShow.payloads.UpdateMovieRequest;
import com.firstProject.bookMyShow.service.movies.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/movies")
public class MoviesController {

    @Autowired
    public MovieService movieService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllMovies() {
        try{
            List<Movies> moviesList = movieService.getAllMovies();
            return ResponseEntity.ok(new APIResponse("List of Movies!", moviesList));
        }
        catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/addMovie")
    public ResponseEntity<APIResponse> addMovie(@RequestBody Movies moviesRequest) {
        try{
            Movies newMovies = movieService.addMovie(moviesRequest);
            return ResponseEntity.ok(new APIResponse("New Movie Added!", newMovies));
        }
        catch(AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null ));
        }
    }

    @PutMapping("/updateMovie/{movieId}")
    public ResponseEntity<APIResponse> updateMovie(@PathVariable Integer movieId, UpdateMovieRequest movieRequest) {
        try{
            Movies updatedMovie = movieService.modifyMovie(movieRequest, movieId);
            return ResponseEntity.ok(new APIResponse("Update Movie", updatedMovie));
        }
        catch(ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<APIResponse> deleteMovie(@PathVariable Integer movieId) {
        try {
            movieService.deleteMovie(movieId);
            return ResponseEntity.ok(new APIResponse("Movie Deleted!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/movie")
    public ResponseEntity<APIResponse> getMovieById(@RequestParam Integer movieId) {
        try {
            Movies movies = movieService.getMovie(movieId);
            return ResponseEntity.ok(new APIResponse("Movie Extracted By ID", movies));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/movieByName")
    public ResponseEntity<APIResponse> getMovieByName(@RequestParam String name) {
        try {
            Movies movies = movieService.getMoviesByName(name);
            if(movies != null)
                return ResponseEntity.ok(new APIResponse("Movies Found!", movies));
            else
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Movies Not Found", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/movieByGenre")
    public ResponseEntity<APIResponse> getMovieByGenre(@RequestParam String genre) {
        try {
            List<Movies> moviesList = movieService.getMoviesByGenre(genre);
            if(!moviesList.isEmpty())
                return ResponseEntity.ok(new APIResponse("Movies Found!", moviesList));
            else{
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Movies Not Found!", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/movieByFormat")
    public ResponseEntity<APIResponse> getMovieByFormat(@RequestParam String format) {
        try {
            List<Movies> moviesList = movieService.getMoviesByFormat(format);
            if(moviesList.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Movies Not Found!", null));
            else
                return ResponseEntity.ok(new APIResponse("Movies Found!", moviesList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/movies/genre/{genre}/format/{format}")
    public ResponseEntity<APIResponse> getMoviesByGenreAndFormat(@PathVariable String genre,
                                                                 @PathVariable String format) {
        try {
            List<Movies> moviesList = movieService.getMoviesByGenreAndFormat(genre, format);
            if(moviesList.isEmpty())
                return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Not Found!", null));
            else
                return ResponseEntity.ok(new APIResponse("List of Movies", moviesList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }
}
