package org.perfumepedia.DataBase.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
public class RememberPasswordDto {
    @NotNull
    @NotEmpty(message = "{errors.not.empty}")
    @Email(message = "{errors.email.valid}")
    private String email;
}
