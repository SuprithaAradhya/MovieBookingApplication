package com.bookmovie.controller;

import com.bookmovie.dto.MovieBookingDTO;
import com.bookmovie.dto.MovieDTO;
import com.bookmovie.entities.Movie;
import com.bookmovie.entities.Theatre;
import com.bookmovie.entities.User;
import com.bookmovie.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/movie_app/v1")
public class MovieController {

    @Autowired
    private MovieService movieService ;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Method for creating movies
     * 127.0.0.1:8080/movie_app/v1/movies
     */

    @PostMapping(value="/movies", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMovie(@RequestBody MovieDTO movieDTO){

        //convert movieDTO to MovieEntity

        Movie newMovie = modelMapper.map(movieDTO, Movie.class);
        Movie savedMovie = movieService.acceptMovieDetails(newMovie);

        MovieDTO savedMovieDto = modelMapper.map(savedMovie,MovieDTO.class);

        return new ResponseEntity(savedMovieDto, HttpStatus.CREATED);

    }

    @GetMapping(value = "/movies" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllMovies(){
        List<Movie> movieList = movieService.getAllMovies();
        List<MovieDTO> movieDTOList = new ArrayList<>();

        for(Movie movie : movieList){
            movieDTOList.add(modelMapper.map(movie, MovieDTO.class));
        }
        return new ResponseEntity(movieDTOList, HttpStatus.OK);
    }
    @GetMapping(value="/movies/{id}")
    public ResponseEntity getMovieBasedOnId(@PathVariable(name="id") int id){
        Movie responseMovie = movieService.getMovieDetails(id);

        MovieDTO responseMovieDTO = modelMapper.map(responseMovie, MovieDTO.class);

        return new ResponseEntity(responseMovieDTO, HttpStatus.OK);
    }

    @PutMapping( value= "movies/" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMovieDetails(@PathVariable(name ="id") int id,
                                             @RequestBody MovieDTO movieDTO){

        Movie newMovie = modelMapper.map(movieDTO, Movie.class);
        Movie updateMovieDetails = movieService.updateMovieDetails(id, newMovie);

        MovieDTO updatedMovieDTO = modelMapper.map(updateMovieDetails, MovieDTO.class);

        return new ResponseEntity(updatedMovieDTO, HttpStatus.OK) ;
    }

    @PostMapping(value = "/bookings/movie" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bookMovieDetails(@RequestBody MovieBookingDTO movieBookingDTO){
        Movie requestedMovie = modelMapper.map(movieBookingDTO.getMovie(),Movie.class);
        User fromUser = modelMapper.map(movieBookingDTO.getUser(), User.class);
        Theatre requestedTheatre = modelMapper.map(movieBookingDTO.getTheatre(), Theatre.class);

        boolean isValidBooking = movieService.bookMovie(fromUser,requestedMovie,requestedTheatre);

        if(!isValidBooking)
            return new ResponseEntity("Not Booked !!", HttpStatus.OK) ;
        return new ResponseEntity("Booked Successfully !!", HttpStatus.OK) ;
    }
}

