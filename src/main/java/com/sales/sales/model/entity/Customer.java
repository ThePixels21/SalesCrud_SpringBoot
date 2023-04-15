package com.sales.sales.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "customers", indexes = { @Index(name = "index_name_customer", columnList = "name"),
        @Index(name = "index_name_lastname1_customer", columnList = "name, last_Name_1"),
        @Index(name = "index_fullname_customer", columnList = "name, last_Name_1, last_Name_2") })
public class Customer implements Serializable {

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

    @Column(nullable = true, length = 10)
    private Integer category;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Customer() {
        this.orders = new ArrayList<>();
    }

}