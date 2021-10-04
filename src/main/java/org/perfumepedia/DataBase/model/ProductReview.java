package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_review")
    private Long idReview;
    @ManyToOne
    @JoinColumn(name= "id_user", insertable = false, updatable = false)
    private User user;
    @Column(name = "id_product")
    private Long idProduct;
    private short score;
    @Column(name = "textreview")
    private String textReview;
    @Column(name = "data_review")
    private Date dataReview;
}
