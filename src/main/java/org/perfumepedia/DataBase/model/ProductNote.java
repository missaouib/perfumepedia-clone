package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_note")
public class ProductNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product_note")
    private Long idProductNote;
    @Column(name = "id_product")
    private Long idProduct;
    @Column(name = "id_note")
    private int idNote;
    @ManyToOne
    @JoinColumn(name = "id_note", insertable = false, updatable = false)
    private Note note;
    @Column(name = "is_base_note")
    private boolean isBaseNote;
    @Column(name = "is_head_note")
    private boolean isHeadNote;
    @Column(name = "is_heart_note")
    private boolean isHeartNote;
}
