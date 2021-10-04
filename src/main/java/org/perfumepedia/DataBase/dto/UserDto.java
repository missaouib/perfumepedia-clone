package org.perfumepedia.DataBase.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.perfumepedia.DataBase.component.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Data
public class UserDto {

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

    @NotNull
    @Length(min = 8, message = "{errors.field.must.have.min.value.char}")
    @NotEmpty(message = "{errors.not.empty}")
    private String password;
    private String cpassword;

    @AssertTrue(message = "{errors.agreeRegulation.AssertTrue}")
    @NotNull
    private boolean agreeRegulation;

}