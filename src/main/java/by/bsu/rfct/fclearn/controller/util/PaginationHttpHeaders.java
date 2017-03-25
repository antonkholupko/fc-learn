package by.bsu.rfct.fclearn.controller.util;

import org.springframework.http.HttpHeaders;

public class PaginationHttpHeaders {

    public static final String PER_PAGE = "X-Pagination-Per-Page";
    public static final String CURRENT_PAGE = "X-Pagination-Current-Page";
    public static final String TOTAL_PAGES = "X-Pagination-Total-Pages";
    public static final String TOTAL_ENTRIES = "X-Pagination-Total-Entries";

    private PaginationHttpHeaders(){
    }

    public static void addPaginationHeaders(HttpHeaders httpHeaders,
                                            Integer pageSize, Integer pageNumber, Long entityCount, Integer totalPages) {

        httpHeaders.add(PER_PAGE, String.valueOf(pageSize));
        httpHeaders.add(CURRENT_PAGE, String.valueOf(pageNumber));
        httpHeaders.add(TOTAL_ENTRIES, String.valueOf(entityCount));
        httpHeaders.add(TOTAL_PAGES, String.valueOf(totalPages));
    }

}
