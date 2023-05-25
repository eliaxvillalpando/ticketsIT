package com.iudc.entidades;

public enum Comunicacion {

    Whatsapp("Whatsapp"),
    Correo("Correo"),
    En_persona("En persona"),
    Telefono("Telefono"),
    Teams_chat("Teams chat");

    private final String displayValue;

    private Comunicacion(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}