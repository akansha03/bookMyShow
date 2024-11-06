package com.firstProject.bookMyShow.payloads;

import com.firstProject.bookMyShow.model.Format;
import com.firstProject.bookMyShow.model.Genre;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class AddMovieRequest {
    private Integer Id;
    private String name;
    private String aboutTheMovie;
    private List<String> language;
    private Set<Genre> genre;
    private Set<Format> format;
    private String imdbRatings;
    private List<String> cast;
}
