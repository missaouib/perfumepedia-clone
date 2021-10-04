package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.Product;
import org.perfumepedia.DataBase.model.ProductNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductNoteRepository extends JpaRepository<ProductNote, Long> {
    List<ProductNote> findByIdProduct(Long idProduct);
}
