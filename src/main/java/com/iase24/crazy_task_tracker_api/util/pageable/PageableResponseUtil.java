package com.iase24.crazy_task_tracker_api.util.pageable;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageableResponseUtil {

    public <T, R extends PageableResponse<T>> R buildPageableResponse(List<T> content, Page<?> page, R response) {
        response.setContent(content);
        response.setPageNumber(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setEmpty(page.isEmpty());
        response.setTotalPages(page.getTotalPages());
        response.setTotalItems(page.getTotalElements());
        return response;
    }
}
