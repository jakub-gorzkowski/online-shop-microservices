package com.online.shop.clientservice.repository;

import com.online.shop.clientservice.domain.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Boolean existsByEmail(String email);
    Page<Client> findAll(Pageable pageable);
}
