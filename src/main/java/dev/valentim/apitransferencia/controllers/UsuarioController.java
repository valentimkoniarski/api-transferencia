package dev.valentim.apitransferencia.controllers;


import dev.valentim.apitransferencia.auth.LoginDto;
import dev.valentim.apitransferencia.auth.TokenDto;
import dev.valentim.apitransferencia.auth.TokenService;
import dev.valentim.usuario.UsuarioService;
import dev.valentim.usuario.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;


    @Autowired
    public UsuarioController(UsuarioService usuarioService,
                             TokenService tokenService,
                             AuthenticationManager authManager) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
        this.authManager = authManager;
    }

    @PostMapping("/autenticar")
    public ResponseEntity<TokenDto> authentication(@RequestBody @Valid final LoginDto loginDto)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken loginData = loginDto.convert();
        Authentication authentication = authManager.authenticate(loginData);
        String token = tokenService.createToken(authentication);
        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        usuarioService.registrarUsuario(usuarioDto);
        return ResponseEntity.ok().build();
    }


}
