package com.store.store.controller;

import com.store.store.entity.Product;
import com.store.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getFiltered(Model model,
                              @RequestParam(name = "word", required = false) String word,
                              @RequestParam(name = "minCost", required = false) Integer minCost,
                              @RequestParam(name = "maxCost", required = false) Integer maxCost,
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "itemsByPage", required = false, defaultValue = "5") Integer itemsByPage) {

        Page<Product> products = productService
                .findAllByPagingAndFiltering(productService.getSpec(word, minCost, maxCost),
                        PageRequest.of(page - 1, itemsByPage, Sort.Direction.ASC, "id"));

        model.addAttribute("products", products);
        model.addAttribute("filtresString", productService.getFiltresString(word, minCost, maxCost));
        model.addAttribute("word", word);
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute("page", page);
        model.addAttribute("itemsByPage", itemsByPage);
        return "products";
    }

    @RequestMapping(value = "/product-edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id) {
        ModelAndView result = new ModelAndView("product-edit");
        result.addObject("product", productService.getOneById(id));
        return result;
    }


    @RequestMapping(value = "/product-edit/")
    public ModelAndView createProduct() {
        ModelAndView result = new ModelAndView("product-edit");
        result.addObject("product", new Product());
        return result;
    }



    @RequestMapping(value = "/product-edit/submit", method = RequestMethod.POST)
    public String productEditSubmit(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

}
