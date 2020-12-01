package br.com.web.service;


import br.com.web.model.Aluno;
import br.com.web.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

    private final AlunoRepository userRepository;

    @Autowired
    public CustomUserDetailsService(AlunoRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        Aluno user = Optional.ofNullable(userRepository.findByUsuario(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        System.out.println(username);

        System.out.println(user.getUsuario());
        System.out.println(user.getSenha());

        List<GrantedAuthority> grantedAuthorityAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> grantedAuthorityUser = AuthorityUtils.createAuthorityList("ROLE_USER");

         userDetails = new org.springframework.security.core.userdetails.User( user.getUsuario(), user.getSenha(), grantedAuthorityAdmin);
        System.out.println(userDetails);
        return userDetails;
    }
}
