
package com.iudc.usuario;

import com.iudc.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<Usuario, Integer>{
    //JpaRepository
     @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario getUserByUseremail(@Param("email") String username);
    
    
    
    
    
}
