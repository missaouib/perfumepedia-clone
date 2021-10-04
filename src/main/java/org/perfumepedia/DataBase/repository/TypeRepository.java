package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
}
