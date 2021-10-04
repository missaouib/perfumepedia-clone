package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_author")
    private Integer idAuthor;
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "author_description")
    private String authorDescription;
    @OneToMany(mappedBy = "author")
    private List<Product> productList;

}
