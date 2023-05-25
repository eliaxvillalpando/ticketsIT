package com.iudc.cliente;

import com.iudc.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ClienteTestRepository {
    
    
     @Autowired
    private TestEntityManager entityManager;
    
     
     @Autowired
     private ClienteRepository repo;
     
     
     
     @Test
     public void testCreateCliente() {
      
         Cliente cliente = new Cliente();
         cliente.setActivo(true);
         cliente.setArea("Planeación");
         cliente.setNombre("Rony García Sandoval");
         
         Cliente savedCliente = repo.save(cliente);
         Cliente existUser = entityManager.find(Cliente.class, savedCliente.getId());
         assertThat(cliente.getNombre()).isEqualTo(existUser.getNombre());
      
         
      
      }
     
     
     
     @Test
     public void desactivarCliente(){
     
         Cliente cliente = repo.findById(1).get();
         cliente.setActivo(false);
         Cliente savedCliente = repo.save(cliente);
         assertThat(savedCliente.isActivo()).isEqualTo(false);
     
     
     
     }
    
         @Test
     public void activarCliente(){
     
         Cliente cliente = repo.findById(1).get();
         cliente.setActivo(true);
         Cliente savedCliente = repo.save(cliente);
         assertThat(savedCliente.isActivo()).isEqualTo(true);
     
     }
     
     
      @Test
      public void activarClienteconRepo(){
      
         
         repo.actualizarEstatus(1, true);
         
      
      }
      
 
       @Test
       public void encontrarAreaConNombre(){
       
           Cliente cliente = repo.findAreaByName("Rony García Sandoval");
           String area = cliente.getArea();
           System.out.println(area);
           
           
       }
   
      
    
}
