package com.fpmislata.basespring.controller.common;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.domain.model.ListWithCount;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Controller
public class BaseController {

    protected ModelMapper modelMapper = new ModelMapper();
    @Value("${app.base.url}")
    private String baseUrl;
    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    public int getPageSize(Integer size) {
        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize);
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page index must be greater than zero");
        }
        return pageSize;
    }

    public <T> ResponseEntity<PaginatedResponse<T>> createPaginatedResponse(
            List<T> items, int total, int page, int pageSize, String url) {

        PaginatedResponse<T> response = new PaginatedResponse<>(items, total, page, pageSize, baseUrl + url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Metodo generico para getAll
    public <Entity, Collection> ResponseEntity<PaginatedResponse<Collection>> getAll(
            int page,
            Integer size,
            BiFunction<Integer, Integer, ListWithCount<Entity>> fetchWithCount,
            Function<Entity, Collection> mapToCollection,
            String baseUrl) {

        int pageSize = getPageSize(size);
        ListWithCount<Entity> result = fetchWithCount.apply((page - 1), pageSize);

        List<Collection> items = result.getList().stream()
                .map(mapToCollection)
                .toList();

        return createPaginatedResponse(items, (int) result.getCount(), page, pageSize, baseUrl);
    }

    // Metodo generico para findByIsbn
    public <Entity, Detail> ResponseEntity<Detail> getById(
            Integer id,
            Function<Integer, Entity> fetchEntityById,
            Function<Entity, Detail> mapToDetail) {

        Entity entity = fetchEntityById.apply(id);
        Detail detail = mapToDetail.apply(entity);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
}