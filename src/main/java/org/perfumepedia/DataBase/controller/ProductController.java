package org.perfumepedia.DataBase.controller;

import org.perfumepedia.DataBase.component.HtmlTagGenerator;
import org.perfumepedia.DataBase.component.SexNameGenerator;
import org.perfumepedia.DataBase.dto.FilterProductsDto;
import org.perfumepedia.DataBase.model.*;
import org.perfumepedia.DataBase.repository.*;
import org.perfumepedia.DataBase.service.ProductService;
import org.perfumepedia.DataBase.service.UserService;
import org.perfumepedia.DataBase.service.VoteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private SexNameGenerator sexNameGenerator;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private HtmlTagGenerator htmlTagGenerator;
    @Autowired
    private ProductNoteRepository productNoteRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private VoteProductRepository voteProductRepository;
    @Autowired
    private VoteProductService voteProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private BrandsRepository brandsRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private VoteNoteRepository voteNoteRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/products")
    public String showProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Model model,
            HttpSession session)
    {
        FilterProductsDto filterProductsDto = (FilterProductsDto) session.getAttribute("filterProductsDto");
        if (filterProductsDto == null) filterProductsDto = new FilterProductsDto();
        model.addAttribute("filterProductsDto", filterProductsDto);
        model.addAttribute("types", typeRepository.findAll());
        model.addAttribute("brands", brandsRepository.findAll());
        List<String> listTypes = new ArrayList<>();
        List<String> listSex = new ArrayList<>();
        List<String> listBrands = new ArrayList<>();
        if (filterProductsDto != null || filterProductsDto.getTypes().size() > 0) {
            listTypes = filterProductsDto.getTypes();
        }
        if (filterProductsDto != null || filterProductsDto.getSex().size() > 0) {
            listSex = filterProductsDto.getSex();
        }
        if (filterProductsDto != null || filterProductsDto.getBrands().size() > 0) {
            listBrands = filterProductsDto.getBrands();
        }

        model.addAttribute("posts", productService.getProducts(listTypes, listSex, listBrands, pageNumber, size));
        return "products/products";
    }

    @GetMapping("/products/reset")
    public String deletedFilterProductsDtoInSessionValue(HttpSession session) {
        session.removeAttribute("filterProductsDto");
        return "redirect:/products";
    }

    @PostMapping("/products")
    public String showFilterProducts(
            @Valid FilterProductsDto filterProductsDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            HttpSession session)
    {
        model.addAttribute("filterProductsDto", filterProductsDto);
        session.setAttribute("filterProductsDto", filterProductsDto);
        model.addAttribute("types", typeRepository.findAll());
        model.addAttribute("brands", brandsRepository.findAll());
        model.addAttribute("posts", productService.getProducts(
                filterProductsDto.getTypes(),
                filterProductsDto.getSex(),
                filterProductsDto.getBrands(),
                pageNumber, size));
        return "products/products";
    }

    @GetMapping("/product/{id}")
    public String getProductById2(@PathVariable(name = "id") Long id, Model model) {
        if (productRepository.findByIdProduct(id).isPresent()) {
            model.addAttribute("product", productRepository.findById(id).get());
            model.addAttribute("sex", sexNameGenerator.getSexName(productRepository.findById(id).get().getSex()));
            Double score = productReviewRepository.getAverageScoreFromReviewUser(id);
            model.addAttribute("averageScore", (score == null) ? htmlTagGenerator.getScoreStar(0.0) : htmlTagGenerator.getScoreStar(score));
            model.addAttribute("voteProduct", voteProductRepository.getVoteProductStatsByIdProduct(id));
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByLogin(login);
            if (user != null) {
                model.addAttribute("userVoteProduct", voteProductService.findVoteProduct(user.getIdUser(), id));
            } else {
                model.addAttribute("userVoteProduct", null);
            }
            List<ProductNote> allProductNoteList = productNoteRepository.findByIdProduct(id);
            List<ListNoteWithVoteScore> baseNoteList = allProductNoteList.stream()
                    .filter(note -> note.isBaseNote() == true)
                    .map(note -> ListNoteWithVoteScore.builder()
                            .productNote(note)
                            .score(voteNoteRepository.getSumVoteToNote(id, note.getIdNote()) != null ? voteNoteRepository.getSumVoteToNote(id, note.getIdNote()) : 0)
                            .build())
                    .sorted(Comparator.comparingDouble(ListNoteWithVoteScore::getScore).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("baseNote", baseNoteList);
            List<ListNoteWithVoteScore> headNoteList = allProductNoteList.stream()
                    .filter(note -> note.isHeadNote() == true)
                    .map(note -> ListNoteWithVoteScore.builder()
                            .productNote(note)
                            .score(voteNoteRepository.getSumVoteToNote(id, note.getIdNote()) != null ? voteNoteRepository.getSumVoteToNote(id, note.getIdNote()) : 0)
                            .build())
                    .sorted(Comparator.comparingDouble(ListNoteWithVoteScore::getScore).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("headNote", headNoteList);
            List<ListNoteWithVoteScore> heartNoteList = allProductNoteList.stream()
                    .filter(note -> note.isHeartNote() == true)
                    .map(note -> ListNoteWithVoteScore.builder()
                            .productNote(note)
                            .score(voteNoteRepository.getSumVoteToNote(id, note.getIdNote()) != null ? voteNoteRepository.getSumVoteToNote(id, note.getIdNote()) : 0)
                            .build())
                    .sorted(Comparator.comparingDouble(ListNoteWithVoteScore::getScore).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("heartNote", heartNoteList);
            List<ProductImage> images = productImageRepository.findByIdProduct(id);
            model.addAttribute("image", images.stream()
                    .filter(img -> img.isMainImage() == true)
                    .findFirst().get());
            model.addAttribute("productReview", productReviewRepository.findByIdProduct(id));
            return "products/product";
        } else {
            model.addAttribute("error", "Brak produktu o id:" + id);
            return "error";
        }
    }
}
