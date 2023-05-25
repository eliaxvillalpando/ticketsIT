package com.iudc.cliente;

import com.iudc.entidades.Cliente;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listarClientes() {
        return (List<Cliente>) repo.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    public Cliente getCliente(Integer id) throws Exception {
        try {
            return repo.findById(id).get();
        } catch (Exception e) {
            throw new Exception("No fue posible encontrar un cliente con el ID " + id);
        }
    }

    public void actualizarEstatus(Integer id, boolean enabled) {
        repo.actualizarEstatus(id, enabled);
    }

}
