package org.perfumepedia.DataBase.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.perfumepedia.DataBase.component.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
public class EditUserDto {
    @Autowired
    private MessageGenerator messageGenerator;

    @NotNull
    @Length(min = 5, message = "{errors.field.must.have.min.value.char}")
    @NotEmpty(message = "{errors.not.empty}")
    private String login;

    @NotNull
    @NotEmpty(message = "{errors.not.empty}")
    @Email(message = "{errors.email.valid}")
    private String email;

    @Length(min = 8, message = "{errors.field.must.have.min.value.char}")
    private String password;
    private String cpassword;
}
