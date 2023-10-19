package dev.valentim.apitransferencia.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class LoginDto {
    private final String email;
    private final String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}