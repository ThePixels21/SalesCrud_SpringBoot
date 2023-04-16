package com.sales.sales.model.service.seller;

import java.util.List;

import com.sales.sales.model.entity.Seller;

public interface ISellerService {
    
    public List<Seller> getSellers(String keyword);

    public void saveSeller(Seller seller);

    public Seller getSellerById(Long id);

    public Seller updateSeller(Seller seller);

    public void deleteSeller(Long id);

}
