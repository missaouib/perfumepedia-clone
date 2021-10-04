package org.perfumepedia.DataBase.model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.Entity;

@Data
@Builder
public class ListNoteWithVoteScore {
    private Integer score;
    private ProductNote productNote;
}
