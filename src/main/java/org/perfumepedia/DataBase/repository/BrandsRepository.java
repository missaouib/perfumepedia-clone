package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BrandsRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByNameBrandStartingWith(String letter);
}
