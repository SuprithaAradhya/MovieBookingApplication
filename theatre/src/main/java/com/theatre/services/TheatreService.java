package com.theatre.services;

import com.theatre.entities.Theatre;

public interface TheatreService {
    public Theatre getTheatreDetails(int theatreId, int movieId);
}
