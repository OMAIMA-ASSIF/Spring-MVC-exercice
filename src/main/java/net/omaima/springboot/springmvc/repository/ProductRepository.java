package net.omaima.springboot.springmvc.repository;

import net.omaima.springboot.springmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
