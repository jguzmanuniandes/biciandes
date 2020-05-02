package com.uniandes.biciandes.settings;

import com.uniandes.biciandes.config.ProductoConfig;
import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private ProductoConfig productoConfig;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository, ProductoConfig productoConfig) {
        this.userRepository = userRepository;
        this.productoConfig = productoConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                getAuthorities("USER"));
    }

    private List<GrantedAuthority> getAuthorities (String... roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        authorities.add(new SimpleGrantedAuthority(productoConfig.getVersion()));
        return authorities;
    }

}
