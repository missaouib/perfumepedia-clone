package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_page")
    private Integer idPage;
    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "page_name")
    private String pageName;
    @NotNull
    @Size(max = 5000)
    @Column(name = "page_content")
    private String pageContent;
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "page_title")
    private String pageTitle;
}
