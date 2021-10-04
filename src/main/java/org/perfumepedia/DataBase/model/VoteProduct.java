package org.perfumepedia.DataBase.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "vote_product")
public class VoteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_vote_product")
    private Long idVoteProduct;
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "id_product")
    private Long idProduct;
    private boolean ilove;
    private boolean ilike;
    private boolean idislike;
    private boolean ihave;
    private boolean ihad;
    private boolean iwant;
    private boolean winter;
    private boolean spring;
    private boolean summer;
    private boolean autumn;
    private boolean day;
    private boolean night;

    @Tolerate
    VoteProduct() {}
}
