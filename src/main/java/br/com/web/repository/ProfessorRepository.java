package br.com.web.repository;

import br.com.web.model.Professor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {
    Professor findByUsuario(String usuario);
}
