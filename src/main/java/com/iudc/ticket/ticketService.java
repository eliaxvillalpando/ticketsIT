package com.iudc.ticket;

import com.iudc.cliente.ClienteRepository;
import com.iudc.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.iudc.entidades.Ticket;
import com.iudc.usuario.MyUserDetails;

@Service
@Transactional
public class ticketService {

    @Autowired
    private ticketRepository repo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private MyUserDetails repoUser;

    public List<Ticket> listarTicketsPorUsuarioSistemas(String usuario) {

        return (List<Ticket>) repo.listTicketsPorUsuarioSistemas(usuario);

    }

    public List<Ticket> listarTickets() {
        return (List<Ticket>) repo.findAll();
    }

    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteRepo.findAll();
    }

    public Ticket guardar(Ticket ticket) {
        return repo.save(ticket);
    }

    public Ticket getTicket(Integer id) throws Exception {
        try {
            return repo.findById(id).get();
        } catch (Exception e) {
            throw new Exception("No fue posible encontrar un ticket con el ID " + id);
        }
    }

    public void getNombreUsuarioTicket(Integer id) {

    }

    public String getAreaUsuarioTicket(Integer id) {

        String area = repo.findAreaById(id);
        return area;

    }

    public List<Ticket> listarAreaUsuarioTicket() {

        return (List<Ticket>) repo.listArea();

    }

}
