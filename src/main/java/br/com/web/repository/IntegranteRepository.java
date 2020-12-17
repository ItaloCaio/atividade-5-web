package br.com.web.repository;

import br.com.web.model.Aluno;
import br.com.web.model.Integrante;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IntegranteRepository extends PagingAndSortingRepository<Integrante, Long> {

}
