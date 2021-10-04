package org.perfumepedia.DataBase.controller;

import org.perfumepedia.DataBase.model.Page;
import org.perfumepedia.DataBase.repository.PageResporitory;
import org.perfumepedia.DataBase.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PageController {
    @Autowired
    private PageResporitory pageResporitory;
    @Autowired
    private PageService pageService;

    @GetMapping("page/{id}")
    public String getPageById(@PathVariable(name = "id") Integer id, Model model) {
        if (!pageResporitory.findById(id).isPresent()) {
            model.addAttribute("pages", pageResporitory.findById(id).get());
            return "page/page";
        } else {
            model.addAttribute("error", "Brak podanej strony o id:" + id);
            return "error";
        }
    }

    @GetMapping("/pages")
    public String getAllPages(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                              Model model) {
        model.addAttribute("textPages", pageService.getAllPages(pageNumber, size));
        return "page/pages";
    }

    @GetMapping("/addpage")
    public String showForm(Model model) {
        model.addAttribute("page", new Page());
        return "page/addpage";
    }

    @PostMapping("/addpage")
    public String addPageToDataBase(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) {
            return "page/addpage";
        }
        pageResporitory.save(page);
        redirectAttrs.addFlashAttribute("infoSuccess", "Dodano");
        return "redirect:/pages";
    }

    @GetMapping("/wojtek")
    public String getTest() {
        return "user/edit-user.html";
    }
}
