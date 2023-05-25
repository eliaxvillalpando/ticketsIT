package com.iudc.entidades;


public enum Area {
    
    RH("Recursos Humanos"),
    Planeación("Planeación"),
    Contabilidad("Contabilidad"),
    Compras("Compras"),
    Ventas("Ventas"),
    Finanzas("Finanzas"),
    Diseño("Diseño"),
    Calidad("Calidad"),
    Almacén("Almacén"),
    Sistemas("Sistemas"),
    Producción("Producción"),
    Mantenimiento("Mantenimiento"),
    Direccion("Dirección");
    
    
    
    
    
    private final String displayValue;

    private Area(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
    
}
