package com.tomasdutkus.movierental.controller;

import com.tomasdutkus.movierental.model.Movie;
import com.tomasdutkus.movierental.service.MovieInventoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class MovieInventoryController {

    private final Logger logger = Logger.getLogger(MovieInventoryController.class);
    private static final int MAX_PAGE_SIZE = 50;

    @Autowired
    private MovieInventoryService movieInventoryService;

    @PostMapping(value = "/movies", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> addMovieToInventory(@RequestBody Movie movie) {
        this.logger.info("Getting request for adding movie object to inventory : " + movie.toString());

        Movie persistedRestaurant = this.movieInventoryService.addMovieToInventory(movie);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedRestaurant.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/movies", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Movie>> getAllMoviesFromInventory(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
                                                              @RequestParam(required = false, defaultValue = "id") String sort,
                                                              @RequestParam(required = false, defaultValue = "asc") String order) {
        this.logger.info("Getting request for all movies from inventory");
        final PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("asc".equals(order) ? Sort.Direction.ASC : Sort.Direction.DESC, sort));
        Page<Movie> restaurantsPage = this.movieInventoryService.getAllMovies(pr);

        if (restaurantsPage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            long totalBooks = restaurantsPage.getTotalElements();
            int nbPageBooks = restaurantsPage.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalBooks));

            if (nbPageBooks < totalBooks) {
                headers.add("first", buildPageUri(PageRequest.of(0, restaurantsPage.getSize())));
                headers.add("last", buildPageUri(
                        PageRequest.of(restaurantsPage.getTotalPages() - 1, restaurantsPage.getSize())));

                if (restaurantsPage.hasNext()) {
                    headers.add("next", buildPageUri(restaurantsPage.nextPageable()));
                }

                if (restaurantsPage.hasPrevious()) {
                    headers.add("prev", buildPageUri(restaurantsPage.previousPageable()));
                }

                return new ResponseEntity<>(restaurantsPage.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(restaurantsPage.getContent(), headers, HttpStatus.OK);
            }
        }
    }

    @GetMapping(value = "/movies/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Movie> getMovieById(@PathVariable long id) {
        this.logger.info("Getting request for getting movie object by id : " + id);

        return new ResponseEntity<>(this.movieInventoryService.getMovieById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/movies/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteFilmFromInventory(@PathVariable long id) {
        this.logger.info("Getting request for deleting film object with id: " + id);

        this.movieInventoryService.deleteMovieFromInventory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String buildPageUri(Pageable page) {
        return fromUriString("/movies").query("page={page}&size={size}")
                .buildAndExpand(page.getPageNumber(), page.getPageSize()).toUriString();
    }
}
