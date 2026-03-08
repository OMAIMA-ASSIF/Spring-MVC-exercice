package net.omaima.springboot.springmvc;

import net.omaima.springboot.springmvc.entities.Product;
import net.omaima.springboot.springmvc.repository.ProductRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
//import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class SpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
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
