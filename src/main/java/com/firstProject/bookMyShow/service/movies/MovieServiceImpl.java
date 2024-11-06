package com.firstProject.bookMyShow.service.movies;

import com.firstProject.bookMyShow.exceptions.AlreadyExistsException;
import com.firstProject.bookMyShow.exceptions.ResourceNotFoundException;
import com.firstProject.bookMyShow.model.Format;
import com.firstProject.bookMyShow.model.Genre;
import com.firstProject.bookMyShow.model.Movies;
import com.firstProject.bookMyShow.payloads.AddMovieRequest;
import com.firstProject.bookMyShow.payloads.UpdateMovieRequest;
import com.firstProject.bookMyShow.repository.FormatRepository;
import com.firstProject.bookMyShow.repository.GenreRepository;
import com.firstProject.bookMyShow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public GenreRepository genreRepository;

    @Autowired
    public FormatRepository formatRepository;

    @Override
    public List<Movies> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movies addMovie(Movies movieRequest) {

        if(isMovieExists(movieRequest.getName())){
            throw new AlreadyExistsException("Movie already exists with name " + movieRequest.getName());
        }

        Set<Genre> genres = movieRequest.getGenre().stream()
                .map(g -> genreRepository.findByName(g.getName())
                        .orElseGet(() -> genreRepository.save(new Genre(g.getName()))))
                .collect(Collectors.toSet());

        Set<Format> formats = movieRequest.getFormat().stream()
                .map(format -> formatRepository.findByName(format.getName())
                        .orElseGet(() -> formatRepository.save(new Format(format.getName()))))
                .collect(Collectors.toSet());

        movieRequest.setGenre(genres);
        movieRequest.setFormat(formats);
        return movieRepository.save(movieRequest);
    }

    private boolean isMovieExists(String name) {
        return movieRepository.existsByName(name);
    }

    @Override
    public Movies modifyMovie(UpdateMovieRequest movie, Integer movieId) {
        return movieRepository.findById(movieId)
                .map(existingMovie -> updateMovie(existingMovie, movie))
                .map(movieRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found!"));
    }

    private Movies updateMovie(Movies existingMovie, UpdateMovieRequest updateMovie) {
        existingMovie.setName(updateMovie.getName());
        existingMovie.setAboutTheMovie(updateMovie.getAboutTheMovie());
        existingMovie.setLanguage(updateMovie.getLanguage());

        //Handle the Genre Entity
        Set<Genre> genres = updateMovie.getGenre().stream()
                        .map(genre -> genreRepository.findByName(genre.getName())
                                .orElseGet(() -> genreRepository.save(new Genre(genre.getName()))))
                                .collect(Collectors.toSet());
        existingMovie.setGenre(genres);

        // Handle the Format Entity

        Set<Format> formats = updateMovie.getFormat().stream()
                        .map(format -> formatRepository.findByName(format.getName())
                                .orElseGet(() -> formatRepository.save(new Format(format.getName()))))
                                .collect(Collectors.toSet());

        existingMovie.setFormat(formats);

        existingMovie.setImdbRatings(updateMovie.getImdbRatings());
        existingMovie.setCast(updateMovie.getCast());
        return existingMovie;
    }

    @Override
    public void deleteMovie(Integer movieId) {
        movieRepository.findById(movieId)
                .ifPresentOrElse(movieRepository :: delete, () -> {throw new ResourceNotFoundException("Movie Not Found");});
    }

    @Override
    public Movies getMovie(Integer movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found!"));
    }

    @Override
    public Movies getMoviesByName(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public List<Movies> getMoviesByGenre(String name) {
        return movieRepository.findByGenreName(name);
    }

    @Override
    public List<Movies> getMoviesByFormat(String name) {
        return movieRepository.findByFormatName(name);
    }

    @Override
    public List<Movies> getMoviesByGenreAndFormat(String genre, String format) {
        return movieRepository.findByGenreNameAndFormatName(genre, format);
    }
}
