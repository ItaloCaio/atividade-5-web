package br.com.web.service;


import br.com.web.model.Aluno;
import br.com.web.model.Professor;
import br.com.web.repository.AlunoRepository;
import br.com.web.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public CustomUserDetailsService(AlunoRepository userRepository, ProfessorRepository professorRepository) {
        this.alunoRepository = userRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        Aluno aluno = alunoRepository.findByUsuario(username);
        Professor professor = null;
        if(aluno == null){
             professor = Optional.ofNullable(professorRepository.findByUsuario(username))
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        List<GrantedAuthority> grantedAuthorityAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> grantedAuthorityUser = AuthorityUtils.createAuthorityList("ROLE_USER");

        if (aluno != null)
            userDetails = new org.springframework.security.core.userdetails.User( aluno.getUsuario(), aluno.getSenha(), grantedAuthorityUser);
        else
        {
            userDetails = new org.springframework.security.core.userdetails.User( professor.getUsuario(), professor.getSenha(), grantedAuthorityAdmin);
        }
        return userDetails;
    }
}
