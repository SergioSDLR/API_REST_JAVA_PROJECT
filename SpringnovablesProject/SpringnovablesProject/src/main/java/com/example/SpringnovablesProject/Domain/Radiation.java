package com.example.SpringnovablesProject.Domain;

public class Radiation {
    private String longitud;
    private String latitud;
    private short anio;
    private short radiation;

    public Radiation() {
    }
    public Radiation(String longitud, String latitud, short anio, short radiation) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.anio = anio;
        this.radiation = radiation;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public short getRadiation() {
        return radiation;
    }

    public void setRadiation(short radiation) {
        this.radiation = radiation;
    }
}
