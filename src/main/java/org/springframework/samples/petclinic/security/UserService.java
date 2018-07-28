package org.springframework.samples.petclinic.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();

                return authorities;
            }

            @Override
            public String getPassword() {
                return "$2a$04$PgDczau2CWrO2Hl4eWqvt.i/QGGhoX.hGiJgapirCgNPafpzzyutq";
            }

            @Override
            public String getUsername() {
                return "jaco";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                // we never lock accounts
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                // credentials never expire
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
