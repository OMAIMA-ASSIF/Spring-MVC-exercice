package net.omaima.springmvcspringsecurity;

import net.omaima.springmvcspringsecurity.entities.Product;
import net.omaima.springmvcspringsecurity.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMvcSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcSpringSecurityApplication.class, args);
    }


    @Bean
    public CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(2000)
                    .quantity(12)
                    .build());

            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(1233)
                    .quantity(10)
                    .build());

            productRepository.save(Product.builder()
                    .name("tv")
                    .price(881)
                    .quantity(9)
                    .build());

            productRepository.findAll().forEach( p->{
                System.out.println(p.toString());

            });

        };
    }
}
