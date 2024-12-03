package com.fpmislata.basespring.controller.admin;

import com.fpmislata.basespring.controller.admin.adminModel.movie.MovieAdminCollection;
import com.fpmislata.basespring.controller.common.BaseController;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.domain.model.Actor;
import com.fpmislata.basespring.domain.model.Director;
import com.fpmislata.basespring.domain.model.Movie;
import com.fpmislata.basespring.domain.useCase.movie.admin.*;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieCountUseCase;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieGetAllUseCase;
import com.fpmislata.basespring.domain.useCase.movie.common.MovieGetByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping(MovieAdminController.URL)
public class MovieAdminController extends BaseController {

    public static final String URL = "/api/admin/movies";

    private final MovieGetAllUseCase movieGetAllUseCase;
    private final MovieCountUseCase movieCountUseCase;
    private final MovieGetByIdUseCase movieGetByIdUseCase;
    private final MovieInsertDirectorUseCase movieInsertDirectorUseCase;
    private final MovieInsertActorsUseCase movieInsertActorsUseCase;
    private final MovieInsertUseCase movieInsertUseCase;
    private final MovieUpdateUseCase movieUpdateUseCase;
    private final MovieDeleteUseCase movieDeleteUseCase;

    @GetMapping
    public ResponseEntity<PaginatedResponse<MovieAdminCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int finalSize = getPageSize(size);

        return super.getAll(
                page,
                size,
                offset -> movieGetAllUseCase.execute((page - 1) * finalSize, finalSize),
                book -> modelMapper.map(book, MovieAdminCollection.class),
                movieCountUseCase.execute(),
                URL
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Integer id) {
        return super.getById(
                id,
                movieGetByIdUseCase::execute,
                Function.identity() // No se requiere transformación
        );
    }

    @PostMapping("/{id}/director")
    public ResponseEntity<Void> insertDirector(@PathVariable Integer id, @RequestBody Director director) {
        movieInsertDirectorUseCase.execute(id, director);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/actor")
    public ResponseEntity<Void> insertActors(@PathVariable Integer id, @RequestBody List<Actor> actors) {
        movieInsertActorsUseCase.execute(id, actors);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Movie movie) {
        movieInsertUseCase.execute(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Movie movie) {
        movieUpdateUseCase.execute(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        movieDeleteUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
