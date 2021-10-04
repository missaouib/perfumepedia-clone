package org.perfumepedia.DataBase.model;

import lombok.Data;
import javax.persistence.*;


@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_role")
    private int idRole;
    @Column(name = "role")
    private String role;
}
