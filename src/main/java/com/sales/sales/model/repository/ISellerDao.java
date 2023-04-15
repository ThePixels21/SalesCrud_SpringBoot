package com.sales.sales.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Seller;

@Repository
public interface ISellerDao extends JpaRepository<Seller, Long> {
    
}
