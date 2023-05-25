package com.iudc.entidades;


public enum Solicitud {
    
    Alta_de_etiquetas("Alta de etiqueta en SAI"),
    Alta_de_MP("Alta de MP"),
    Actualizacion_precio("Actualización de precio"),
    Cancelacion_factura("Cancelación de factura"),
    Autorizacion_SAI("Autorización en SAI"),
    Soporte_correo("Soporte con correo"),
    Soporte_equipo("Soporte con equipo"),
    Soporte_impresora("Soporte con impresora"),
    Soporte_SAI("Soporte en SAI"),
    Soporte_Sispro("Soporte en Sispro"),
    Soporte_red("Soporte con internet"),
    Soporte_telefono("Soporte con telefono"),
    Soporte_office("Soporte con paqueteria office")
    ;
    
    
    
    
    
    private final String displayValue;

    
    
    
    private Solicitud(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
    
    
    
    
    
}
