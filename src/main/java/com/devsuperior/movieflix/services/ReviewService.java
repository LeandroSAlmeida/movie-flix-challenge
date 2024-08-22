package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review entity = new Review();
        copyDtoEntity(dto, entity);
        entity = repository.save(entity);
        return new ReviewDTO(entity);
    }

    private void copyDtoEntity(ReviewDTO dto, Review entity) {
        Movie movie = movieRepository.getOne(dto.getMovieId());
        User user = authService.authenticated();

        authService.validateSelfOrMember(user.getId());
        entity.setText(dto.getText());
        entity.setMovie(movie);
        entity.setUser(user);
    }
}
