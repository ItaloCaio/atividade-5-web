package br.com.web.endpoint;

import br.com.web.error.CustomErrorType;
import br.com.web.error.ResourceNotFoundException;
import br.com.web.model.Professor;
import br.com.web.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("professores")
public class ProfessorEndpoint {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorEndpoint(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(professorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProfessorById(@PathVariable("id") long id) {
        verifyIfTeacherExists(id);
        Professor professor = professorRepository.findOne(id);
        if (professor == null) {
            return new ResponseEntity<>(new CustomErrorType("Teacher not found "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Professor professor) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        professor.setSenha(bCryptPasswordEncoder.encode(professor.getSenha()));


        return new ResponseEntity<>(professorRepository.save(professor), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        verifyIfTeacherExists(id);
        professorRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Professor professor) {
        verifyIfTeacherExists(professor.getId());
        professorRepository.save(professor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfTeacherExists(Long id){
        if (professorRepository.findOne(id) == null)
            throw new ResourceNotFoundException("Professor not found for ID: " + id);
    }

}
