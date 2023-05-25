package com.iudc.usuario;

import com.iudc.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorUsuario {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/registrar-usuario-ti")
    private String mostrarPaginaRegistrar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registrar-usuario";
    }

    @PostMapping("/process_register")
    private String processRegister(Usuario user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    
        return "register_success";
        
    }
    
    
    
    
    
    
}
