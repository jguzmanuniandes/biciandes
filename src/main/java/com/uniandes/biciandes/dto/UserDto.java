package com.uniandes.biciandes.dto;

import com.uniandes.biciandes.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserDto {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    public User toUserEntity() {

        User user = new User();
        user.setName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);

        //TODO: Fill more fields
        return user;
    }

}
