package com.tomasdutkus.movierental.controller;

import com.tomasdutkus.movierental.exception.UserException;
import com.tomasdutkus.movierental.model.User;
import com.tomasdutkus.movierental.service.UserService;
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
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class);
    private static final int MAX_PAGE_SIZE = 50;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserException {
        this.logger.info("Getting request for persisting user object : " + user.toString());

        user.setEnabled(true);
        User persistedUser = this.userService.registerUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> getAllUsers(@PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
                                                  @RequestParam(required = false, defaultValue = "id") String sort,
                                                  @RequestParam(required = false, defaultValue = "asc") String order) throws UserException {
        this.logger.info("Getting request for all user object");
        final PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("asc".equals(order) ? Sort.Direction.ASC : Sort.Direction.DESC, sort));
        Page<User> usersPage = this.userService.getAllUsers(pr);

        if (usersPage.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            long totalBooks = usersPage.getTotalElements();
            int nbPageBooks = usersPage.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalBooks));

            if (nbPageBooks < totalBooks) {
                headers.add("first", buildPageUri(PageRequest.of(0, usersPage.getSize())));
                headers.add("last", buildPageUri(PageRequest.of(usersPage.getTotalPages() - 1, usersPage.getSize())));

                if (usersPage.hasNext()) {
                    headers.add("next", buildPageUri(usersPage.nextPageable()));
                }

                if (usersPage.hasPrevious()) {
                    headers.add("prev", buildPageUri(usersPage.previousPageable()));
                }

                return new ResponseEntity<>(usersPage.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(usersPage.getContent(), headers, HttpStatus.OK);
            }
        }
    }

    @GetMapping(value = "/users/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getUserById(@PathVariable long id) throws UserException {
        this.logger.info("Getting request for getting user object by id : " + id);

        User user = this.userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        this.logger.info("User not found for the id : " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) throws UserException {
        this.logger.info("Getting request for updating user object with id: " + id);

        this.userService.updateUser(user, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteUser(@PathVariable long id) throws UserException {
        this.logger.info("Getting request for deleting user object with id: " + id);

        this.userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String buildPageUri(Pageable page) {
        return fromUriString("/api/users").query("page={page}&size={size}")
                .buildAndExpand(page.getPageNumber(), page.getPageSize()).toUriString();
    }
}
