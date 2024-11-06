package com.firstProject.bookMyShow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVIES")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    private String aboutTheMovie;

    @ElementCollection
    private List<String> language;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "MOVIES_GENRE_MAPPING", joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genre;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "MOVIES_FORMAT_MAPPING", joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "format_id"))
    private Set<Format> format;

    private String imdbRatings;

    @ElementCollection
    private List<String> cast;

    @Override
    public String toString() {
        return "Movies{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", aboutTheMovie='" + aboutTheMovie + '\'' +
                ", language=" + language +
                ", genre=" + genre +
                ", format=" + format +
                ", imdbRatings=" + imdbRatings +
                ", cast=" + cast +
                '}';
    }
}
