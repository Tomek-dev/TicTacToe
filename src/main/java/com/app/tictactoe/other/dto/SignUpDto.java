package com.app.tictactoe.other.dto;

import com.app.tictactoe.other.validation.Email;
import com.app.tictactoe.other.validation.PasswordDetails;
import com.app.tictactoe.other.validation.Username;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpDto implements PasswordDetails {

    @Username
    @NotBlank
    @Size(min = 4, max = 24)
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 36)
    private String password;
    private String confirmPassword;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword;
    }
}