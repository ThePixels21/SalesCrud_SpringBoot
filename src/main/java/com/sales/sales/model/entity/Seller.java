package com.sales.sales.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "sellers", indexes = { @Index(name = "index_name_seller", columnList = "name"),
        @Index(name = "index_name_lastname_seller", columnList = "name, last_Name_1"),
        @Index(name = "index_fullname_seller", columnList = "name, last_Name_1, last_Name_2") })
public class Seller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Column(name = "last_Name_1", nullable = false, length = 100)
    private String lastName1;

    @Column(name = "last_Name_2", nullable = true, length = 100)
    private String lastName2;

    @Column(nullable = true, length = 100)
    private String city;

    @Column(nullable = true)
    private Float commission;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Seller() {
        this.orders = new ArrayList<>();
    }

}