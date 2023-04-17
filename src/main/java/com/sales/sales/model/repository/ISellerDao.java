package com.sales.sales.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Seller;

@Repository
public interface ISellerDao extends JpaRepository<Seller, Long> {
    @Query("SELECT s FROM Seller s WHERE CONCAT(s.lastName1, ' ', s.lastName2, ' ', s.name) LIKE %?1%")
    public List<Seller> findByName(String keyword);
}
