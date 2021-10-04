package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.VoteProduct;
import org.perfumepedia.DataBase.model.VoteProductStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteProductRepository extends JpaRepository<VoteProduct, Long> {
    @Query(value = "SELECT SUM(ilove) as ilove, " +
            "SUM(ilike) as ilike, " +
            "SUM(idislike) as idislike, " +
            "SUM(ihave) as ihave, " +
            "SUM(ihad) as ihad, " +
            "SUM(iwant) as iwant, " +
            "SUM(winter) as winter, " +
            "SUM(spring) as spring, " +
            "SUM(summer) as summer, " +
            "SUM(autumn) as autumn, " +
            "SUM(day) as day, " +
            "SUM(night) as night " +
            "FROM vote_product WHERE id_product = ?1", nativeQuery = true)
    VoteProductStat getVoteProductStatsByIdProduct(Long id);

    VoteProduct findByIdUserAndIdProduct(Long idUser, Long idProduct);
}
