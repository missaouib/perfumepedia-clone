package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Capacity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_capacity")
    private long idCapacity;
    @Column(name = "id_product")
    private int idProduct;
    private String ean;
    private String ean2;
    @Column(name = "capacity_value")
    private short capacityValue;
    private boolean tester;
    @ManyToMany
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private List<Product> productList = new ArrayList<Product>();

}
