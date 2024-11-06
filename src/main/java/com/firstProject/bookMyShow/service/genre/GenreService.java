package com.firstProject.bookMyShow.service.genre;

import com.firstProject.bookMyShow.model.Genre;
import java.util.List;

public interface GenreService {

    Genre addGenre(Genre genre);

    Genre modifyGenre(Integer genreId, Genre genre);

    Genre getGenreById(Integer genreId);

    List<Genre> getAllGenres();

    void deleteGenre(Integer genreId);

    Genre getGenreByName(String name);
}
