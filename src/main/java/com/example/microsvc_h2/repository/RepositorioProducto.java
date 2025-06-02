package com.example.microsvc_h2.repository;

import com.example.microsvc_h2.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RepositorioProducto extends JpaRepository<Producto,Long>{

}