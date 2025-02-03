package com.fpmislata.basespring.controller;

import com.fpmislata.basespring.controller.common.BaseController;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.controller.dto.user.userMapper.movie.MovieUserMapper;
import com.fpmislata.basespring.controller.dto.user.userModel.movie.MovieUserCollection;
import com.fpmislata.basespring.controller.dto.user.userModel.movie.MovieUserDetail;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieGetAllUseCase;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieGetByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MovieUserController.URL)
public class MovieUserController extends BaseController {

    public static final String URL = "/api/movies";

    private final MovieGetAllUseCase movieGetAllUseCase;
    private final MovieGetByIdUseCase movieGetByIdUseCase;

    @GetMapping
    public ResponseEntity<PaginatedResponse<MovieUserCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        return super.getAll(
                page,
                size,
                movieGetAllUseCase::execute,
                movie -> modelMapper.map(movie, MovieUserCollection.class),
                URL
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieUserDetail> getById(@PathVariable Integer id) {
        return super.getById(
                id,
                movieGetByIdUseCase::execute,
                MovieUserMapper::toMovieUserDetail
        );
    }
}

