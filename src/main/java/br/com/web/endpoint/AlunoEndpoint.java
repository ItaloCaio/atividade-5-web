package br.com.web.endpoint;

import br.com.web.error.CustomErrorType;
import br.com.web.error.ResourceNotFoundException;
import br.com.web.model.Aluno;
import br.com.web.model.AuthError;
import br.com.web.model.JwtToken;
import br.com.web.repository.AlunoRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("alunos")
public class AlunoEndpoint {

    private final AlunoRepository alunoRepository;
    private final SecretKey secreteKey = Keys.hmacShaKeyFor("QXRpdmlkYWRlV2ViSldUUGFyYVNlZ3VyYW5jYURhQXBsaWNhw6fDo28="
            .getBytes(StandardCharsets.UTF_8));

    @Autowired
    public AlunoEndpoint(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(alunoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAlunoById(@PathVariable("id") long id) {
        verifyIfStudentExists(id);
        Aluno aluno = alunoRepository.findOne(id);
        if (aluno == null) {
            return new ResponseEntity<>(new CustomErrorType("Student not found "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(aluno, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Aluno aluno) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        aluno.setSenha(bCryptPasswordEncoder.encode(aluno.getSenha()));

        System.out.println(aluno.toString());

        return new ResponseEntity<>(alunoRepository.save(aluno), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        verifyIfStudentExists(id);
        alunoRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Aluno aluno) {
        verifyIfStudentExists(aluno.getId());
        alunoRepository.save(aluno);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id){
        if (alunoRepository.findOne(id) == null)
            throw new ResourceNotFoundException("Aluno not found for ID: " + id);
    }

}
