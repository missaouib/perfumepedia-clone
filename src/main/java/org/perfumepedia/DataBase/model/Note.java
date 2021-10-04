package org.perfumepedia.DataBase.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_note")
    private int idNote;
    @Column(name = "id_category_note")
    private int idCategoryNote;
    @Column(name = "name_note")
    private String nameNote;
    @Column(name = "note_description")
    private String noteDescription;
    @OneToMany
    private List<ProductNote> productNotes;
}
