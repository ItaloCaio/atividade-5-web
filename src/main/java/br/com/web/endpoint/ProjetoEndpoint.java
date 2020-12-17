package br.com.web.endpoint;

import br.com.web.error.CustomErrorType;
import br.com.web.error.ResourceNotFoundException;
import br.com.web.model.Projeto;
import br.com.web.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("projetos")
public class ProjetoEndpoint {

    private final ProjetoRepository projetoRepository;

    @Autowired
    public ProjetoEndpoint(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
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

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Projeto projeto) {

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
