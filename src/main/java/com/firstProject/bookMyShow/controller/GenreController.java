package com.firstProject.bookMyShow.controller;

import com.firstProject.bookMyShow.exceptions.AlreadyExistsException;
import com.firstProject.bookMyShow.exceptions.ResourceNotFoundException;
import com.firstProject.bookMyShow.model.Genre;
import com.firstProject.bookMyShow.payloads.APIResponse;
import com.firstProject.bookMyShow.service.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {

    @Autowired
    public GenreService genreService;

    @PostMapping("/addGenre")
    public ResponseEntity<APIResponse> addNewGenre(@RequestBody Genre genre) {
        try {
            Genre newGenre = genreService.addGenre(genre);
            return ResponseEntity.ok(new APIResponse("New Genre Added!", newGenre));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/updateGenre/{id}")
    public ResponseEntity<APIResponse> updateGenre(@PathVariable Integer id, @RequestBody Genre genre) {
        try {
            Genre modifyGenre = genreService.modifyGenre(id, genre);
            return ResponseEntity.ok(new APIResponse("Genre Updated!", modifyGenre));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/genre")
    public ResponseEntity<APIResponse> getGenreById(@RequestParam Integer id) {
        try {
            Genre genre = genreService.getGenreById(id);
            return ResponseEntity.ok(new APIResponse("Genre Extracted", genre));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/genres/all")
    public ResponseEntity<APIResponse> getAllGenres() {
        try {
            List<Genre> genres = genreService.getAllGenres();
            return ResponseEntity.ok(new APIResponse("All Genres Extracted!", genres));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/genre/name/{name}")
    public ResponseEntity<APIResponse> getGenreByName(@PathVariable String name) {
        try {
            Genre genre = genreService.getGenreByName(name);
            return ResponseEntity.ok(new APIResponse("Genre By Name retrieved!", genre));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse( e.getMessage(), null));
        }
    }

    @DeleteMapping("/genre/delete")
    public void deleteGenre(@RequestParam Integer id) {
        genreService.deleteGenre(id);
    }
}
