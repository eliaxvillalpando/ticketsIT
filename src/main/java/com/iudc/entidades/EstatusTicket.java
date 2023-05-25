package com.iudc.entidades;
import javax.persistence.*;

@Embeddable
public enum EstatusTicket {

ABIERTO("Abierto"),
ESPERANDO_RESPUESTA_EXTERNA("Esperando respuesta externa"),
CERRADO("Cerrado");


 private final String displayValue;
    
    private EstatusTicket(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
    


}
