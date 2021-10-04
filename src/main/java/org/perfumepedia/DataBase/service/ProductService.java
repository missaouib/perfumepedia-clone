package org.perfumepedia.DataBase.service;

import org.perfumepedia.DataBase.component.PagingBuilder;
import org.perfumepedia.DataBase.dto.ListingProductDto;
import org.perfumepedia.DataBase.model.*;
import org.perfumepedia.DataBase.repository.ProductImageRepository;
import org.perfumepedia.DataBase.repository.ProductRepository;
import org.perfumepedia.DataBase.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PagingBuilder pagingBuilder;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;

    public Paged<ProductsToListing> getProducts(List<String> listTypes, List<String> listSex, List<String> listBrands, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<ProductsToListing> products = productRepository.getAllProductsToListing(listTypes, listSex, listBrands,request);
        return new Paged<>(products, pagingBuilder.create(products.getTotalPages(), pageNumber, size));
    }

    //function id disabled, different way to get all product. Similar function like getProducts
    public Paged<ListingProductDto> getProducts3(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Product> products = productRepository.findAll(request);
        List<ListingProductDto> listingProductDto = products.stream()
                .map(product -> ListingProductDto.builder()
                        .idProduct(product.getIdProduct())
                        .productName(product.getProductName())
                        .idBrand(product.getIdBrand())
                        .nameBrand(product.getBrand().getNameBrand())
                        .nameType(product.getType().getNameType())
                        .shortType(product.getType().getShortType())
                        .productImage(productImageRepository.findByIdImage(product.getIdProduct()).size() == 0 ? null : productImageRepository.findByIdImage(product.getIdProduct())
                                .stream()
                                .filter(img -> img.isMainImage())
                                .findFirst().get())
                        .score(0.0)
                        .countComments(product.getComments().size())
                        .build())
                .collect(Collectors.toList());
        return new Paged<>(new PageImpl<>(listingProductDto), pagingBuilder.create(products.getTotalPages(), pageNumber, size));
    }
}
