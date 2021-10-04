package org.perfumepedia.DataBase.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;
    private String token;
    @Column(name = "is_consumed")
    private Boolean isConsumed;
    @Column(name = "expired_date")
    private LocalDateTime expiredDateTime;
    @Column(name = "issued_date")
    private LocalDateTime issuedDateTime;
    @Column(name = "confirmed_date")
    private LocalDateTime confirmedDateTime;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_user")
    private User user;

    public VerificationToken(){
        this.token = UUID.randomUUID().toString();
        this.issuedDateTime = LocalDateTime.now();
        this.expiredDateTime = this.issuedDateTime.plusDays(1);
        this.isConsumed = false;
    }
}
