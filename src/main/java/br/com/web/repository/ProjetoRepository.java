package br.com.web.repository;

import br.com.web.model.Projeto;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjetoRepository extends PagingAndSortingRepository<Projeto, Long> {
}
