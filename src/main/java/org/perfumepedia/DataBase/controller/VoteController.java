package org.perfumepedia.DataBase.controller;

import org.perfumepedia.DataBase.model.User;
import org.perfumepedia.DataBase.model.VoteProduct;
import org.perfumepedia.DataBase.model.VoteProductStat;
import org.perfumepedia.DataBase.repository.VoteProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class VoteController {

    private VoteProductRepository voteProductRepository;

    @PostMapping("/vote/product222")
    public void getSearchResultViaAjax(Errors errors) {

        //AjaxVoteResponseBody result = new AjaxVoteResponseBody();
        if (errors.hasErrors()) {

            //result.setMsg(errors.getAllErrors()
            //        .stream().map(x -> x.getDefaultMessage())
            //        .collect(Collectors.joining(",")));

            //return ResponseEntity.badRequest().body(result);

        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User)authentication.getPrincipal();
        Long userId = customUser.getIdUser();

        VoteProduct voteProduct = VoteProduct.builder()
        //        .idProduct(singleVote.getIdProduct())
                .idUser(userId)
        //       .ilove(singleVote.getValue().equals("love") ? true : false)
        //        .ilike(singleVote.getValue().equals("like") ? true : false)
        //        .idislike(singleVote.getValue().equals("dislike") ? true : false)
                .build();
        voteProductRepository.save(voteProduct);
        //VoteProductStat voteProductStat = voteProductRepository.getVoteProductStatsByIdProduct(singleVote.getIdProduct());
        //result.setVoteProductStat(voteProductStat);
       //return ResponseEntity.ok(result);

    }
}
