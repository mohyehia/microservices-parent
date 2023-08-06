package com.moh.yehia.inventoryservice.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "SKU_CODE")
    private String skuCode;

    @Column(name = "QUANTITY")
    private int quantity;
}
