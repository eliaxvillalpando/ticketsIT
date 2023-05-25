package com.iudc.equipos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class equiposController {


   @GetMapping("/reportar-equipo")
   private String viewReportarEquipo(){
   
       return "equipos/reportarEquipo";
   
   }
   


    
}
