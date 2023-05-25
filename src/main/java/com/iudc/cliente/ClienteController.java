/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iudc.cliente;

import com.iudc.entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;


@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private ClienteService serviceRepo;

    @GetMapping("/clientes")
    public String viewClientes(Model model) {

        listarClientes(model);
        return "cliente/clientes";

    }
    

    public void listarClientes(Model model) {

        Iterable<Cliente> iterableCliente = repo.findAll();
        model.addAttribute("clientes", iterableCliente);

    }
    
    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(Model model){
        
    model.addAttribute("clientes", new Cliente());
    model.addAttribute("tituloPagina", "Crear nuevo usuario");
		
	return "cliente/formaCliente";
    
    
    }
    
    
    

    @GetMapping("/clientes/editar/{id}")
    public String editarCliente(@PathVariable(name = "id") Integer id, Model model,
            RedirectAttributes ra) {
        try {
            Cliente cliente = serviceRepo.getCliente(id);

            model.addAttribute("clientes", cliente);
            model.addAttribute("tituloPagina", "Edtiar cliente (ID: " + id + ")");

            return "cliente/formaCliente";
        } catch (Exception ex) {
            ra.addFlashAttribute("mensaje", "No fue posible encontrar un cliente con ese ID");
            return "cliente/clientes";
        }

    }

    @PostMapping("/clientes/guardar")
    public String saveCliente(Cliente cliente, RedirectAttributes ra, Model model) {

        serviceRepo.guardar(cliente);
        ra.addFlashAttribute("mensaje", "El usuario ha sido guardado con exito.");
        listarClientes(model);

        return "redirect:/clientes";
    }
    
    
    @GetMapping("/clientes/{id}/activar/{activo}")
    public String actualizarEstado(@PathVariable("id") Integer id,
			@PathVariable("activo") boolean enabled, RedirectAttributes redirectAttributes) {
		serviceRepo.actualizarEstatus(id, enabled);
                String status = enabled ? "habilitado" : "deshabilitado";
		String mensajeMostrar = "El usuario ID " + id + " ha sido " + status;
		redirectAttributes.addFlashAttribute("mensaje", mensajeMostrar);
		
		return "redirect:/clientes";
	}
    

    
}
