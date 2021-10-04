package org.perfumepedia.DataBase.repository;

import org.perfumepedia.DataBase.model.VoteNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteNoteRepository extends JpaRepository<VoteNote, Long> {
    @Query(value = "SELECT SUM(vote_note.vote_value) as suma FROM `vote_note`" +
            " WHERE vote_note.id_product = :idProduct and vote_note.id_note = :idNote",
            nativeQuery = true)
    Integer getSumVoteToNote(@Param("idProduct") Long idProduct, @Param("idNote") Integer idNote);

}
