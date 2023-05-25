
package com.iudc.usuario;

import com.iudc.entidades.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService{
    
     @Autowired
    private UserRepository userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario user = userRepository.getUserByUseremail(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("No se encontro el usuario");
        }
         
        return new MyUserDetails(user);
    }
    
    
    
}
