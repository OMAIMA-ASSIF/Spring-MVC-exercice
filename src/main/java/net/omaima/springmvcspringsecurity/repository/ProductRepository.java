package net.omaima.springmvcspringsecurity.repository;

import net.omaima.springmvcspringsecurity.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
