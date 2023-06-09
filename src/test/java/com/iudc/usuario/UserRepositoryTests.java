package com.iudc.usuario;

import com.iudc.entidades.Usuario;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

 @Autowired
private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        
        Usuario user = new Usuario();
        user.setEmail("santiagogaspar@iudc.com.mx");
        user.setPassword("santi");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setNombre("Santiago");
        user.setApellido("Gaspar");

        Usuario savedUser = repo.save(user);
        Usuario existUser = entityManager.find(Usuario.class, savedUser.getId());
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
    
    
    
    

}
