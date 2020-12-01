package br.com.web.repository;

import br.com.web.model.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlunoRepository extends PagingAndSortingRepository<Aluno, Long> {
    Aluno findByUsuario(String usuario);
}
