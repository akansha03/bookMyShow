package com.firstProject.bookMyShow.service.genre;

import com.firstProject.bookMyShow.exceptions.AlreadyExistsException;
import com.firstProject.bookMyShow.exceptions.ResourceNotFoundException;
import com.firstProject.bookMyShow.model.Genre;
import com.firstProject.bookMyShow.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    public GenreRepository genreRepository;

    @Override
    public Genre addGenre(Genre genre) {
        return Optional.of(genre)
                .filter(g -> !genreRepository.existsByName(g.getName()))
                .map(genreRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException("Already exists - " + genre.getName()));
    }

    @Override
    public Genre modifyGenre(Integer id, Genre genre) {
        return Optional.ofNullable(getGenreById(id))
                .map(existingGenre -> {existingGenre.setName(genre.getName());
                return genreRepository.save(existingGenre);})
                .orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!"));
    }

    @Override
    public Genre getGenreById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!"));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteGenre(Integer id) {
        genreRepository.findById(id)
                .ifPresentOrElse(genreRepository :: delete,
                        () -> {throw new ResourceNotFoundException("Genre Not Found!");});

    }

    @Override
    public Genre getGenreByName(String name) {
       return genreRepository.findByName(name)
               .orElseThrow(() -> new ResourceNotFoundException("Genre Not Found!"));
    }
}
