package dev.valentim.apitransferencia;

import dev.valentim.apitransferencia.auth.TokenService;
import dev.valentim.cliente.Cliente;
import dev.valentim.cliente.ClienteRepository;
import dev.valentim.cliente.ClienteService;
import dev.valentim.key.Key;
import dev.valentim.usuario.Usuario;
import dev.valentim.usuario.UsuarioRepository;
import dev.valentim.usuario.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackageClasses = {Usuario.class, Cliente.class, Key.class})
@EnableJpaRepositories(basePackageClasses = {UsuarioRepository.class, ClienteRepository.class})
public class ApiTransferenciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiTransferenciaApplication.class, args);
    }

    @Autowired
    private ApplicationContext appContext;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UsuarioService usuarioService() {

        UsuarioRepository usuarioRepository = appContext.getBean(UsuarioRepository.class);
        ModelMapper modelMapper = appContext.getBean(ModelMapper.class);

        BCryptPasswordEncoder bean = appContext.getBean(BCryptPasswordEncoder.class);

        return new UsuarioService(usuarioRepository, modelMapper, bean);
    }

    @Bean
    public ClienteService clienteService() {
        ClienteRepository clienteRepository = appContext.getBean(ClienteRepository.class);
        ModelMapper modelMapper = appContext.getBean(ModelMapper.class);
        return new ClienteService(modelMapper, clienteRepository);
    }

    @Bean
    public TokenService tokenService() {
        UsuarioRepository usuarioRepository = appContext.getBean(UsuarioRepository.class);
        return new TokenService();
    }

}
