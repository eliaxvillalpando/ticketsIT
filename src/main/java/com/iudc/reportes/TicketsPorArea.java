package com.iudc.reportes;

import org.springframework.beans.factory.annotation.Value;

public interface TicketsPorArea {

    //@Value("#{target.area_cliente} #{target.area_cliente}") -> Esto duplica en MVC, es suficiente poner en la query as Estatus
    String getAnswer();

    int getCnt();
    
    int getCntTotal();
    
    //@Value("#{target.estatus} #{target.estatus}") -> Esto duplica en MVC, es suficiente poner en la query as Estatus
    String getEstatus();
    
    
    

}
