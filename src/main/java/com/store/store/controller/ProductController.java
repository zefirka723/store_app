package com.store.store.controller;

import com.store.store.entity.Product;
import com.store.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
                              @RequestParam(name = "minCost", required = false) Integer minCost,
                              @RequestParam(name = "maxCost", required = false) Integer maxCost,
                              @RequestParam(name = "page", required = false) Integer page) {
        model.addAttribute("products", productService.getProductsByFiltres(minCost, maxCost, page));
        model.addAttribute("paginator", productService.getTotalPagesCount());
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        return "products";
    }

    @RequestMapping(value = "/product-edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id) {
        ModelAndView result = new ModelAndView("product-edit");
        result.addObject("product", productService.getOneById(id));
        return result;
    }

    @RequestMapping(value = "/product-edit/submit", method = RequestMethod.POST)
    public String productEditSubmit(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/products";
    }

}
