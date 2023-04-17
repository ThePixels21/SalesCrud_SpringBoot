package com.sales.sales.model.service.seller;

import java.util.List;

import org.springframework.data.domain.Sort;
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
    public List<Seller> getSellers(String keyword) {
        if(keyword != null) {
            return this.repo.findByName(keyword);
        }
        return this.repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional
    public void saveSeller(Seller seller) {
        this.repo.save(seller);
    }

    @Override
    @Transactional
    public Seller updateSeller(Seller seller) {
        return this.repo.save(seller);
    }

    @Override
    @Transactional
    public void deleteSeller(Long id) {
        this.repo.deleteById(id);
    }

    @Override
    public Seller getSellerById(Long id) {
        return this.repo.findById(id).orElse(null);
    }

}
