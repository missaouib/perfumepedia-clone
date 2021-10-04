package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByIdImage(Long id);
    List<ProductImage> findByIdProduct(Long id);
}
