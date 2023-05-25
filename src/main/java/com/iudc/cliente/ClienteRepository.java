/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iudc.cliente;

import com.iudc.entidades.Cliente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author elias
 */
public interface ClienteRepository extends CrudRepository<Cliente, Integer>{
    
    
    public Long countById(Integer id);
    
        @Query("UPDATE Cliente c SET c.activo = ?2 WHERE c.id = ?1")
	@Modifying
	public void actualizarEstatus(Integer id, boolean enabled);
    
        
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    public Cliente findAreaByName(String nombre);
    
    
}


/*

@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);

*/