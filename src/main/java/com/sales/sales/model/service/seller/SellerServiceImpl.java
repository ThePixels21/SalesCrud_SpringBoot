package com.sales.sales.model.service.seller;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.sales.model.entity.Seller;
import com.sales.sales.model.repository.ISellerDao;

@Service
public class SellerServiceImpl implements ISellerService {

    private ISellerDao repo;

    public SellerServiceImpl(ISellerDao repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seller> getSellers() {
        return this.repo.findAll();
    }

    @Override
    @Transactional
    public void saveSeller(Seller seller) {
        this.repo.save(seller);
    }

    @Override
    public Seller getSellerById(Long id) {
        return this.repo.findById(id).orElse(null);
    }

    @Override
    public Seller updateSeller(Seller seller) {
        return this.repo.save(seller);
    }

    @Override
    public void deleteSeller(Long id) {
        this.repo.deleteById(id);
    }
    
}