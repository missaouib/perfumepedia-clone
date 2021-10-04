package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_brand", nullable = false)
    private Long idBrand;
    @Column(name = "name_brand", nullable = false)
    private String nameBrand;
    @Column(name = "description_brand", nullable = false)
    private String descriptionBrand;
    @OneToMany(mappedBy = "brand")
    private List<Product> productsList = new ArrayList<Product>();
}
