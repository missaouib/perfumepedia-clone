package org.perfumepedia.DataBase.service;

import org.perfumepedia.DataBase.component.PagingBuilder;
import org.perfumepedia.DataBase.model.Page;
import org.perfumepedia.DataBase.model.Paged;
import org.perfumepedia.DataBase.repository.PageResporitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PageService {
    @Autowired
    private PageResporitory pageResporitory;
    @Autowired
    private PagingBuilder pagingBuilder;

    public Paged<org.perfumepedia.DataBase.model.Page> getAllPages(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        org.springframework.data.domain.Page<Page> returnPage = pageResporitory.findAll(request);
        return new Paged<>(returnPage, pagingBuilder.create(returnPage.getTotalPages(), pageNumber, size));
    }


}
