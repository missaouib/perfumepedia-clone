package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageResporitory extends JpaRepository<Page, Integer> {

}
