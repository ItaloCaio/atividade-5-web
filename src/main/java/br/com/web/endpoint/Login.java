package br.com.web.endpoint;

import br.com.web.model.Aluno;
import br.com.web.model.AuthError;
import br.com.web.model.JwtToken;
import br.com.web.repository.AlunoRepository;
import br.com.web.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("login")
public class Login {

    private final AlunoRepository alunoRepository;
    private final SecretKey secreteKey = Keys.hmacShaKeyFor("QXRpdmlkYWRlV2ViSldUUGFyYVNlZ3VyYW5jYURhQXBsaWNhw6fDo28="
            .getBytes(StandardCharsets.UTF_8));

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public Login(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @PostMapping(path = "/")
    public ResponseEntity<?> login(@RequestBody Aluno aluno) {
        try{
            if(alunoRepository.findByUsuario(aluno.getUsuario()) != null){
                String jwtToken = Jwts.builder()
                        .setSubject(aluno.getUsuario())
                        .setIssuer("localhost:8080")
                        .setIssuedAt(new Date())
                        .setExpiration(
                                Date.from(
                                        LocalDateTime.now().plusMinutes(15L)
                                                .atZone(ZoneId.systemDefault())
                                                .toInstant()))
                        .signWith(secreteKey, SignatureAlgorithm.HS256)
                        .compact();

                final UserDetails userDetails = customUserDetailsService
                        .loadUserByUsername(aluno.getUsuario());

                return new ResponseEntity<>(new JwtToken(jwtToken), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(new AuthError("Usuario e/ou senha invalidos"), HttpStatus.UNAUTHORIZED);
        }
        catch (Error err){
            System.out.println(err);
        }
        return new ResponseEntity<>(alunoRepository.save(aluno), HttpStatus.OK);
    }
}
