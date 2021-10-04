package org.perfumepedia.DataBase.controller;

import org.perfumepedia.DataBase.component.MessageGenerator;
import org.perfumepedia.DataBase.repository.BrandsRepository;
import org.perfumepedia.DataBase.repository.ProductRepository;
import org.perfumepedia.DataBase.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BrandsController {
    @Autowired
    private BrandsRepository brandsRepository;
    @Autowired
    private BrandsService brandsService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MessageGenerator messageGenerator;

    @GetMapping("/brands2")
    public String brands(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        model.addAttribute("posts", brandsService.getBrands(pageNumber, size));
        return "brands2";
    }

    @RequestMapping("/brand/{idBrand}")
    public String brand(@PathVariable(value = "idBrand", required = true) Long idBrand, Model model) {
        model.addAttribute("brand", brandsRepository.findById(idBrand).get());
        model.addAttribute("products", productRepository.findByIdBrand(idBrand));
        return "brand";
    }

    @GetMapping("/brands/{letter}")
    public String brandOnTheLetter(@PathVariable(value = "letter", required = false) String letter, Model model) {
        if (letter.isEmpty()) letter = "A";
        model.addAttribute("brand", brandsRepository.findByNameBrandStartingWith(letter));
        model.addAttribute("alphabet", "abcdefghijklmnopqrstuvwxyz".toCharArray());
        model.addAttribute("test", messageGenerator.getMessage("title"));
        return "brands";
    }
}
