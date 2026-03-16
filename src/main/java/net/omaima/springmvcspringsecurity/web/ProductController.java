package net.omaima.springmvcspringsecurity.web;

import net.omaima.springmvcspringsecurity.entities.Product;
import net.omaima.springmvcspringsecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/index")
    public String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("productsList", products);
        return "products";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/newProduct")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id){
        productRepository.deleteById(id);
        return "redirect:/index";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product){
        productRepository.save(product);
        return "redirect:/index";
    }
}

