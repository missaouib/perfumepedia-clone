package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    @Query(value = "SELECT avg(score) FROM product_review WHERE id_product = ?1", nativeQuery = true)
    Double getAverageScoreFromReviewUser(Long idProduct);
    List<ProductReview> findByIdProduct(Long idProduct);
}
