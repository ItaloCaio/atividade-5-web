package br.com.web.endpoint;

import br.com.web.error.CustomErrorType;
import br.com.web.error.ResourceNotFoundException;
import br.com.web.model.Integrante;
import br.com.web.model.Professor;
import br.com.web.model.Projeto;
import br.com.web.repository.IntegranteRepository;
import br.com.web.repository.ProfessorRepository;
import br.com.web.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projetos")
public class ProjetoEndpoint {

    private final ProjetoRepository projetoRepository;
    private final ProfessorRepository professorRepository;
    private final IntegranteRepository integranteRepository;

    @Autowired
    public ProjetoEndpoint(ProjetoRepository projetoRepository, ProfessorRepository professorRepository, IntegranteRepository integranteRepository) {
        this.projetoRepository = projetoRepository;
        this.professorRepository = professorRepository;
        this.integranteRepository = integranteRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(projetoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProjetoById(@PathVariable("id") long id) {
        verifyIfProjetoExists(id);
        Projeto projeto = projetoRepository.findOne(id);
        if (projeto == null) {
            return new ResponseEntity<>(new CustomErrorType("Projeto not found "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projeto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Projeto projeto) {

        Professor professor = professorRepository.findByUsuario(userDetails.getUsername());
        projeto.setCoordenador(professor);

        return new ResponseEntity<>(projetoRepository.save(projeto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/{id}/addAluno")
    public ResponseEntity<?> addAluno(@PathVariable long id, @RequestBody Integrante integrante) {

        verifyIfProjetoExists(id);
        Projeto projeto = projetoRepository.findOne(id);
        if (projeto == null) {
            return new ResponseEntity<>(new CustomErrorType("Projeto not found "), HttpStatus.NOT_FOUND);
        }
        integrante.setProjeto(projeto);
        integranteRepository.save(integrante);

        return new ResponseEntity<>(projetoRepository.save(projeto), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        verifyIfProjetoExists(id);
        projetoRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Projeto projeto) {
        verifyIfProjetoExists(projeto.getId());
        projetoRepository.save(projeto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfProjetoExists(Long id){
        if (projetoRepository.findOne(id) == null)
            throw new ResourceNotFoundException("Projeto not found for ID: " + id);
    }
}
