package org.perfumepedia.DataBase.controller;

import org.perfumepedia.DataBase.dto.LoveLikeDislikeStats;
import org.perfumepedia.DataBase.model.*;
import org.perfumepedia.DataBase.service.UserService;
import org.perfumepedia.DataBase.service.VoteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoteProductRestController {

    public static final String IHAVE = "ihave";
    public static final String IWANT = "iwant";
    public static final String IHAD = "ihad";
    public static final String SPRING = "spring";
    public static final String SUMMER = "summer";
    public static final String AUTUMN = "autumn";
    public static final String WINTER = "winter";
    public static final String DAY = "day";
    public static final String NIGHT = "night";
    public static final String DONE = "Done";
    public static final String NOT_LOGIN = "not-login";
    @Autowired
    private UserService userService;
    @Autowired
    private VoteProductService voteProductService;

    @RequestMapping(value = "vote/product", method = RequestMethod.POST)
    public @ResponseBody ResponseAjax iHaveVoteToProduct(
            @RequestParam("voteValue") String voteValue,
            @RequestParam("idProduct") Long idProduct)
    {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            return new ResponseAjax(NOT_LOGIN, null);
        }
        VoteProduct existVoteProduct = voteProductService.findVoteProduct(user.getIdUser(), idProduct);
        if (existVoteProduct == null) {
            VoteProduct voteProduct = VoteProduct.builder()
                    .idProduct(idProduct)
                    .idUser(user.getIdUser())
                    .ilove(voteValue.equals("love") ? true : false)
                    .ilike(voteValue.equals("like") ? true : false)
                    .idislike(voteValue.equals("dislike") ? true : false)
                    .build();
            voteProductService.saveVoteProduct(voteProduct);
        } else {
            existVoteProduct.setIlove(voteValue.equals("love") ? true : false);
            existVoteProduct.setIlike(voteValue.equals("like") ? true : false);
            existVoteProduct.setIdislike(voteValue.equals("dislike") ? true : false);
            voteProductService.saveVoteProduct(existVoteProduct);
        }
        VoteProductStat voteProductStat = voteProductService.getVoteProductStatsByIdProduct(idProduct);
        LoveLikeDislikeStats loveLikeDislikeStats = LoveLikeDislikeStats.builder()
                .loveQuantity(voteProductStat.getIlove())
                .likeQuantity(voteProductStat.getIlike())
                .disLikeQuantity(voteProductStat.getIdislike())
                .build();
        return new ResponseAjax(DONE, loveLikeDislikeStats);
    }

    @RequestMapping(value = "vote/productdetail", method = RequestMethod.POST)
    public @ResponseBody ResponseAjax detailVoteToProduct(
            @RequestParam("voteName") String voteName,
            @RequestParam("voteValue") Integer voteValue,
            @RequestParam("idProduct") Long idProduct)
    {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            return new ResponseAjax(NOT_LOGIN, null);
        }
        VoteProduct existVoteProduct = voteProductService.findVoteProduct(user.getIdUser(), idProduct);
        if (existVoteProduct == null) {
            VoteProduct voteProduct = VoteProduct.builder()
                    .idProduct(idProduct)
                    .idUser(user.getIdUser())
                    .build();
            if (voteName.equals(IHAVE)) voteProduct.setIhave(voteValue == 1 ? true : false);
            if (voteName.equals(IWANT)) voteProduct.setIwant(voteValue == 1 ? true : false);
            if (voteName.equals(IHAD)) voteProduct.setIhad(voteValue == 1 ? true : false);
            if (voteName.equals(SPRING)) voteProduct.setSpring(voteValue == 1 ? true : false);
            if (voteName.equals(SUMMER)) voteProduct.setSummer(voteValue == 1 ? true : false);
            if (voteName.equals(AUTUMN)) voteProduct.setAutumn(voteValue == 1 ? true : false);
            if (voteName.equals(WINTER)) voteProduct.setWinter(voteValue == 1 ? true : false);
            if (voteName.equals(DAY)) voteProduct.setDay(voteValue == 1 ? true : false);
            if (voteName.equals(NIGHT)) voteProduct.setNight(voteValue == 1 ? true : false);
            voteProductService.saveVoteProduct(voteProduct);
        } else {
            if (voteName.equals(IHAVE)) existVoteProduct.setIhave(voteValue == 1 ? true : false);
            if (voteName.equals(IWANT)) existVoteProduct.setIwant(voteValue == 1 ? true : false);
            if (voteName.equals(IHAD)) existVoteProduct.setIhad(voteValue == 1 ? true : false);
            if (voteName.equals(SPRING)) existVoteProduct.setSpring(voteValue == 1 ? true : false);
            if (voteName.equals(SUMMER)) existVoteProduct.setSummer(voteValue == 1 ? true : false);
            if (voteName.equals(AUTUMN)) existVoteProduct.setAutumn(voteValue == 1 ? true : false);
            if (voteName.equals(WINTER)) existVoteProduct.setWinter(voteValue == 1 ? true : false);
            if (voteName.equals(DAY)) existVoteProduct.setDay(voteValue == 1 ? true : false);
            if (voteName.equals(NIGHT)) existVoteProduct.setNight(voteValue == 1 ? true : false);
            voteProductService.saveVoteProduct(existVoteProduct);
        }
        VoteProductStat voteProductStat = voteProductService.getVoteProductStatsByIdProduct(idProduct);
        VoteQuantity voteQuantity = new VoteQuantity();
        voteQuantity.setName(voteName);
        if (voteName.equals(IHAVE)) voteQuantity.setQuantity(voteProductStat.getIhave());
        if (voteName.equals(IWANT)) voteQuantity.setQuantity(voteProductStat.getIwant());
        if (voteName.equals(IHAD)) voteQuantity.setQuantity(voteProductStat.getIhad());
        if (voteName.equals(SPRING)) voteQuantity.setQuantity(voteProductStat.getSpring());
        if (voteName.equals(SUMMER)) voteQuantity.setQuantity(voteProductStat.getSummer());
        if (voteName.equals(AUTUMN)) voteQuantity.setQuantity(voteProductStat.getAutumn());
        if (voteName.equals(WINTER)) voteQuantity.setQuantity(voteProductStat.getWinter());
        if (voteName.equals(DAY)) voteQuantity.setQuantity(voteProductStat.getDay());
        if (voteName.equals(NIGHT)) voteQuantity.setQuantity(voteProductStat.getNight());
        return new ResponseAjax(DONE, voteQuantity);
    }


}
