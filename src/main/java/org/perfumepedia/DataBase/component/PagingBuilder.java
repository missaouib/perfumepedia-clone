package org.perfumepedia.DataBase.component;

import org.perfumepedia.DataBase.model.Paging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PagingBuilder {

    @Value("${PagingBuilder.paginationStep}")
    private int paginationStep;

    public Paging create(int totalPages, int pageNumber, int pageSize) {
        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setNextEnabled(pageNumber != totalPages);
        paging.setPrevEnabled(pageNumber != 1);
        paging.setPageNumber(pageNumber);

        if (totalPages < paginationStep * 2 + 6) {
            paging.addPageItems(1, totalPages + 1, pageNumber);

        } else if (pageNumber < paginationStep * 2 + 1) {
            paging.addPageItems(1, paginationStep * 2 + 4, pageNumber);
            paging.last(totalPages);

        } else if (pageNumber > totalPages - paginationStep * 2) {
            paging.first(pageNumber);
            paging.addPageItems(totalPages - paginationStep * 2 - 2, totalPages + 1, pageNumber);

        } else {
            paging.first(pageNumber);
            paging.addPageItems(pageNumber - paginationStep, pageNumber + paginationStep + 1, pageNumber);
            paging.last(totalPages);
        }

        return paging;
    }
}
