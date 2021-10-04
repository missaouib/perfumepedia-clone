package org.perfumepedia.DataBase.service;

import org.perfumepedia.DataBase.model.Brand;
import org.perfumepedia.DataBase.model.Paged;
import org.perfumepedia.DataBase.component.PagingBuilder;
import org.perfumepedia.DataBase.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BrandsService {
    @Autowired
    private BrandsRepository brandsRepository;
    @Autowired
    private PagingBuilder pagingBuilder;

    public Paged<Brand> getBrands(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Brand> brands = brandsRepository.findAll(request);
        return new Paged<>(brands, pagingBuilder.create(brands.getTotalPages(), pageNumber, size));
    }
}
