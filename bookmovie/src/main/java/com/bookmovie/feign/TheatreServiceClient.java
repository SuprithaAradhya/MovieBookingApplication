package com.bookmovie.feign;

import com.bookmovie.entities.Theatre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "THEATRE-SERVICE")
public interface TheatreServiceClient {

    @RequestMapping(value = "${theatreApp.bookMoviePath}", method = RequestMethod.GET)
    public Theatre getTheatre(@PathVariable("theatreId") int theatreId, @PathVariable("movieId") int movieId);
}