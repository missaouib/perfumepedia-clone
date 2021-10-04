package org.perfumepedia.DataBase.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Table(name = "User")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    @Length(min = 5)
    @NotEmpty
    private String login;
    @Email
    @NotEmpty
    private String email;
    @Length(min = 8)
    @NotEmpty
    private String password;
    private Boolean confirmed;
    @OneToMany(mappedBy = "user")
    private List<VerificationToken> verificationToken = new ArrayList<>();
    @Column(name = "add_date")
    @CreatedDate
    private Date addDate;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private Set<Role> roles;
    @AssertTrue
    @Column(name="agree_regulation")
    private Boolean agreeRegulation;
}
