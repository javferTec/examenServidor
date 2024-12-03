package com.fpmislata.basespring.controller.common;

import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.function.Function;

@Controller
public class BaseController {

    protected ModelMapper modelMapper = new ModelMapper();
    @Value("${app.base.url}")
    private String baseUrl;
    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    public int getPageSize(Integer size) {
        return (size != null) ? size : Integer.parseInt(defaultPageSize);
    }

    public <T> ResponseEntity<PaginatedResponse<T>> createPaginatedResponse(
            List<T> items, int total, int page, int pageSize, String url) {

        PaginatedResponse<T> response = new PaginatedResponse<>(items, total, page, pageSize, baseUrl + url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Metodo generico para getAll
    public <Entity, Collection> ResponseEntity<PaginatedResponse<Collection>> getAll(
            int page, Integer size,
            Function<Integer, List<Entity>> fetchEntities,
            Function<Entity, Collection> mapToCollection,
            int totalCount,
            String baseUrl) {

        int pageSize = getPageSize(size);

        List<Collection> items = fetchEntities.apply(page - 1).stream()
                .map(mapToCollection)
                .toList();

        return createPaginatedResponse(items, totalCount, page, pageSize, baseUrl);
    }

    // Metodo generico para findById
    public <Entity, Detail> ResponseEntity<Detail> getById(
            Integer id,
            Function<Integer, Entity> fetchEntityById,
            Function<Entity, Detail> mapToDetail) {

        Entity entity = fetchEntityById.apply(id);
        Detail detail = mapToDetail.apply(entity);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
}