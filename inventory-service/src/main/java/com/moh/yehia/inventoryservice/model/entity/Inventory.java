package com.moh.yehia.inventoryservice.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String skuCode;

    private int quantity;
}
