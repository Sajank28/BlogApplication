package com.springboot.blog.security;

import com.springboot.blog.model.User;
import com.springboot.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail ) throws UsernameNotFoundException {
        //find/get user from database if exists
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username or email: "+usernameOrEmail));



        //convert set<roles> to set<grantedAuthorities>

        //map - convert one obj to another
        Set<GrantedAuthority> authorities = user.getRoles().stream().map((role)->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        //convert the user to spring security provided user obj
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                        user.getPassword(),
                                                                            authorities);
    }
}
