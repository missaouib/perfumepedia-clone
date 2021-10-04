package org.perfumepedia.DataBase.service;

import org.perfumepedia.DataBase.model.VoteProduct;
import org.perfumepedia.DataBase.model.VoteProductStat;
import org.perfumepedia.DataBase.repository.VoteProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteProductService {

    @Autowired
    private VoteProductRepository voteProductRepository;

    public VoteProduct saveVoteProduct(VoteProduct voteProduct) {
        return voteProductRepository.save(voteProduct);
    }

    public VoteProduct findVoteProduct(Long idUser, Long idProduct) {
        return voteProductRepository.findByIdUserAndIdProduct(idUser, idProduct);
    }

    public VoteProductStat getVoteProductStatsByIdProduct(Long id) {
        return voteProductRepository.getVoteProductStatsByIdProduct(id);
    }


}
