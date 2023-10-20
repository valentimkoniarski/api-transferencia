package dev.valentim.apitransferencia.controllers;

import dev.valentim.cliente.ClienteService;
import dev.valentim.cliente.exceptions.ClienteValidacoesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transferencias")
public class TransferenciaController {

    private final ClienteService clienteService;

    @Autowired
    public TransferenciaController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<String> cadastrarCliente() {
        try {
            clienteService.salvar();
            return ResponseEntity.ok().build();
        } catch (ClienteValidacoesException.ClienteExistenteException |
                 ClienteValidacoesException.SaldoInicialInvalidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
