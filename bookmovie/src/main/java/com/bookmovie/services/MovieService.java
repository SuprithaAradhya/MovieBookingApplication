package com.bookmovie.services;

import com.bookmovie.entities.Movie;
import com.bookmovie.entities.Theatre;
import com.bookmovie.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    public Movie acceptMovieDetails(Movie movie);

    public List<Movie> acceptMultipleMovieDetails(List<Movie> movies);

    public Movie getMovieDetails(int id);

    public Movie updateMovieDetails(int id , Movie movie);

    public boolean deleteMovie(int id);

    public List<Movie> getAllMovies();

    public Page<Movie> getPaginatedMovieDetails(Pageable pageable);

    public Boolean bookMovie(User user, Movie movie, Theatre theatre);
}