package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.Product;
import org.perfumepedia.DataBase.model.ProductsToListing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdProduct(Long id);
    List<Product> findByIdBrand(Long id);
    @Query(value = "SELECT product.id_product as idProduct," +
            " product.product_name as productName," +
            " brand.id_brand as idBrand," +
            " brand.name_brand as nameBrand," +
            " type.name_type as nameType," +
            " type.short_type as shortType," +
            " product_image.url_image as img," +
            " product_image.alt_image as alt," +
            " NVL(AVG(product_review.score),0) as score," +
            " COUNT(product_review.textreview) as countComments" +
            " FROM product" +
            " LEFT JOIN product_review ON product.id_product=product_review.id_product" +
            " LEFT JOIN brand ON product.id_brand=brand.id_brand" +
            " LEFT JOIN type ON product.id_type=type.id_type" +
            " LEFT JOIN product_image ON product.id_product=product_image.id_product" +
            " WHERE 1=1 AND (COALESCE(:idTypes) IS NULL OR (product.id_type IN (:idTypes)))" +
            " AND (COALESCE(:idSex) IS NULL OR (product.sex IN (:idSex)))" +
            " AND (COALESCE(:idBrand) IS NULL OR (product.id_brand IN (:idBrand)))" +
            " GROUP BY product.id_product DESC",
            countQuery = "SELECT count(*) FROM product " +
                    " WHERE 1=1 AND (COALESCE(:idTypes) IS NULL OR (product.id_type IN (:idTypes)))" +
                    " AND (COALESCE(:idSex) IS NULL OR (product.sex IN (:idSex)))" +
                    " AND (COALESCE(:idBrand) IS NULL OR (product.id_brand IN (:idBrand)))",
            nativeQuery = true)
    Page<ProductsToListing> getAllProductsToListing(
            @Param("idTypes") List<String> idTypes,
            @Param("idSex") List<String> idSex,
            @Param("idBrand") List<String> idBrands,
            Pageable pageable);
}


/* @Query(value = "SELECT product.id_product as idProduct," +
            " product.product_name as productName," +
            " brand.id_brand as idBrand," +
            " brand.name_brand as nameBrand," +
            " type.name_type as nameType," +
            " type.short_type as shortType," +
            " product_image.url_image as img," +
            " product_image.alt_image as alt," +
            " NVL(AVG(product_review.score),0) as score," +
            " COUNT(product_review.textreview) as countComments" +
            " FROM product" +
            " LEFT JOIN product_review ON product.id_product=product_review.id_product" +
            " LEFT JOIN brand ON product.id_brand=brand.id_brand" +
            " LEFT JOIN type ON product.id_type=type.id_type" +
            " LEFT JOIN product_image ON product.id_product=product_image.id_product" +
            " WHERE 1=1 AND (COALESCE(:idTypes) IS NULL OR (product.id_type IN (:idTypes)))" +
            " OR (COALESCE(:idSex) IS NULL OR (product.sex IN (:idSex)))" +
            " OR (COALESCE(:idBrand) IS NULL OR (product.id_brand IN (:idBrand)))" +
            " GROUP BY product.id_product DESC",
            countQuery = "SELECT count(*) FROM product WHERE 1=1 AND (COALESCE(:idBrand) IS NULL OR (product.id_brand IN (:idBrand)))",
            nativeQuery = true) */