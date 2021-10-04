package org.perfumepedia.DataBase.model;

import lombok.Data;
import org.hibernate.annotations.Formula;
import org.perfumepedia.DataBase.repository.ProductReviewRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product", nullable = false)
    private Long idProduct;
    @Column(name= "product_name", nullable = false)
    private String productName;
    @Column(name = "id_brand", nullable = false)
    private int idBrand;
    @ManyToOne
    @JoinColumn(name = "id_brand", insertable = false, updatable = false)
    private Brand brand;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    private String description;
    @Column(name = "id_type", nullable = false)
    private int idType;
    @Column(name = "id_author", nullable = false)
    private int idAuthor;
    private int sex;
    @ManyToOne
    @JoinColumn(name= "id_type", insertable = false, updatable = false)
    private Type type;
    @OneToMany
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private List<Capacity> capacityList = new ArrayList<Capacity>();
    @OneToMany
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private List<ProductImage> images = new ArrayList<ProductImage>();
    @OneToMany
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private List<ProductReview> comments = new ArrayList<ProductReview>();
    @ManyToOne
    @JoinColumn(name = "id_author", insertable = false, updatable = false)
    private Author author;
}
