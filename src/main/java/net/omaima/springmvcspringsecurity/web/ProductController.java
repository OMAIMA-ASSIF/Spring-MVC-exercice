package net.omaima.springmvcspringsecurity.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import net.omaima.springmvcspringsecurity.entities.Product;
import net.omaima.springmvcspringsecurity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/user/index")
    public String index(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword){

        List<Product> products ;
        if(keyword.isEmpty()){
            products = productRepository.findAll();
        }else{
            products = productRepository.findByNameContainingIgnoreCase(keyword);

        }
        model.addAttribute("productsList", products);
        model.addAttribute("keyword", keyword); // to sends the text back so it stays in the search bar :)
        return "products";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/newProduct")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @GetMapping("/admin/displayProduct")
    public String displayProduct(@RequestParam(name = "id") Long id, Model model){
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            // Optionnel : rediriger ou afficher un message d'erreur si l'ID n'existe pas
            return "redirect:/admin/products";
        }

        model.addAttribute("product", product);
        return "display-product";
    }

    @PostMapping("/admin/editProduct")
    public String editProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model  ){
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(err -> System.out.println(err.toString()));
            return "display-product";}
        productRepository.save(product);
        return "redirect:/admin/displayProduct?id=" + product.getId();
    }


    @PostMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id){
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }

    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){return "new-product";}
        productRepository.save(product);
        return "redirect:/admin/newProduct";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }
}

