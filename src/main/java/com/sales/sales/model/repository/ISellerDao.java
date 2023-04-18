package com.sales.sales.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sales.sales.model.entity.Seller;

@Repository
public interface ISellerDao extends JpaRepository<Seller, Long> {

    /**
     * Find sellers by fullname
     * @param keyword The keyword to filter by fullname
     * @return A list of Sellers filtered by their name
     */
    @Query("SELECT s FROM Seller s WHERE CONCAT(s.lastName1, ' ', s.lastName2, ' ', s.name) LIKE %?1% ORDER BY s.id DESC")
    public List<Seller> findByName(String keyword);
}
