package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idType;
	@Column(name="name_type", nullable = false)
    private String nameType;
	@Column(name="short_type", nullable = false)
    private String shortType;
	@Column(name="description_type", nullable = false)
    private String descriptionType;
	@OneToMany(mappedBy = "type")
    private List<Product> productList = new ArrayList<Product>();
	@OneToMany
    @JoinColumn(name = "id_product_note", insertable = false, updatable = false)
    private List<ProductNote> productNotes = new ArrayList<>();
}
