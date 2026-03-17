package net.omaima.springmvcspringsecurity.repository;

import net.omaima.springmvcspringsecurity.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // This allows searching for products where the name contains the keyword (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
