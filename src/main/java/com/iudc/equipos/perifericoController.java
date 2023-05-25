package com.iudc.equipos;

import com.iudc.FileUploadUtil;
import com.iudc.cliente.ClienteRepository;
import com.iudc.entidades.Cliente;
import com.iudc.entidades.Periferico;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class perifericoController {

    @Autowired
    PerifericoRepository periRepo;

    @Autowired
    ClienteRepository clienteRepo;

    /*
    public void displayPapeles(Model model) {
        Iterable<Papel> iterablePapel = papelRepo.findAll();
        model.addAttribute("papeles", iterablePapel);
    }
     */
    @GetMapping("/periferico")
    private String viewEntregaraPeriferico(Model model) {

        
        listarPerifericos(model);
        return "equipos/periferico";

    }

    @GetMapping("/agregarPeriferico")
    private String viewNuevoPeriferico(Model model) {
        displayClientes(model);
        model.addAttribute("periferico", new Periferico());
        return "equipos/nuevoPeriferico";

    }

    @PostMapping("/crearPeriferico")
    private String nuevoPeriferico(Periferico periferico, BindingResult result, RedirectAttributes ra, Model model,
            @RequestParam(name = "foto1", required = false) MultipartFile multipartFile1,
            @RequestParam(name = "foto2", required = false) MultipartFile multipartFile2,
            @RequestParam(name = "foto3", required = false) MultipartFile multipartFile3,
            @RequestParam(name = "foto4", required = false) MultipartFile multipartFile4) throws IOException {

        String foto1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        String foto2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
        String foto3 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
        String foto4 = StringUtils.cleanPath(multipartFile4.getOriginalFilename());

        periferico.setFoto1(foto1);
        periferico.setFoto2(foto2);
        periferico.setFoto3(foto3);
        periferico.setFoto4(foto4);

        Periferico periSaved = periRepo.save(periferico);
        String uploadDir1 = "Perifericos/" + periSaved.getId() + "/1/";
        String uploadDir2 = "Perifericos/" + periSaved.getId() + "/2/";
        String uploadDir3 = "Perifericos/" + periSaved.getId() + "/3/";
        String uploadDir4 = "Perifericos/" + periSaved.getId() + "/4/";

        ra.addFlashAttribute("mensaje", "El periferico ha sido guardado con exito.");

        listarPerifericos(model);
        FileUploadUtil.saveFile(uploadDir1, foto1, multipartFile1);
        
        if (!foto2.isEmpty()){
            FileUploadUtil.saveFile(uploadDir2, foto2, multipartFile2);
        }else if(!foto3.isEmpty()) {
        FileUploadUtil.saveFile(uploadDir3, foto3, multipartFile3);
        }
        else if(!foto4.isEmpty()) {
        FileUploadUtil.saveFile(uploadDir4, foto4, multipartFile4);
        }
        
        
        
        

        return "redirect:/periferico";


    }

    public void listarPerifericos(Model model) {

         
        Iterable<Periferico> iterablePeri = periRepo.findAll();     
        model.addAttribute("perifericos", iterablePeri);
       

    }

    public void displayClientes(Model model) {

        Iterable<Cliente> iterableUsuario = clienteRepo.findAll();
        model.addAttribute("clientes", iterableUsuario);

    }
    
    
    public void dsiplayUsuario(Model model, Periferico peri){
        
        ArrayList<String> usuario = peri.getUsuario();
        String listString = String.join(", ", usuario);
        model.addAttribute("usuariosFormato", listString);
    
    }    
    
    
    
    

}
