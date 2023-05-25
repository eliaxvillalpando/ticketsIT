/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iudc.entidades;

/**
 *
 * @author elias
 */
public enum Marcas {
    
       
    Dell("Dell"),
    HP("HP"),
    MAC("Mac"),
    LENOVO("Lenovo"),
    SAMSUNG("Samsung"),
    KYOCERA("Kyocera"),
    BROTHER("Brother"),
    EPSON("Epson"),
    XEROX("Xerox"),
    GRANDSTREAM("Grandstream"),
    DENWA("Denwa"),
    ACER("Acer"),
    CHEWEI("Chewi"),
    GENERICO("Generico");
    
    
    
    
    
    
    private final String displayValue;

    private Marcas(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
    
    
    
    
}
