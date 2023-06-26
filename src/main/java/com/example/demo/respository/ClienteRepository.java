package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long>{

}
