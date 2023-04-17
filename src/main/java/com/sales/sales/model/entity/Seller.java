package com.sales.sales.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sellers", indexes = { @Index(name = "index_name_seller", columnList = "name"),
        @Index(name = "index_name_lastname_seller", columnList = "name, last_name_1"),
        @Index(name = "index_fullname_seller", columnList = "last_name_1, last_name_2, name") })
public class Seller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Size(max = 100)
    @Column(name = "last_name_1", nullable = false, length = 100)
    private String lastName1;

    @Size(max = 100)
    @Column(name = "last_name_2", nullable = true, length = 100)
    private String lastName2;

    @Size(max = 100)
    @Column(nullable = true, length = 100)
    private String city;

    @Column(nullable = true)
    private Float commission;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Seller() {
        this.orders = new ArrayList<>();
    }

    public Seller(Long id, @NotEmpty @Size(max = 100) String name, @NotEmpty @Size(max = 100) String lastName1,
            @Size(max = 100) String lastName2, @Size(max = 100) String city, Float commission, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.city = city;
        this.commission = commission;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Float getCommission() {
        return commission;
    }

    public void setCommission(Float commission) {
        this.commission = commission;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    

}