package com.example.SpringnovablesProject.Domain;

public class MeditionDTO {

    //Estos campos tienen que salir de una consulta a un servicio REST
    private long pk_MedicionID;
    private String latitud;
    private String longitud;
    private short anio;
    private short temperature;
    private short wind;
    private short precipitation;

    public long getPk_MedicionID() {
        return pk_MedicionID;
    }

    public void setPk_MedicionID(long pk_MedicionID) {
        this.pk_MedicionID = pk_MedicionID;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public short getTemperature() {
        return temperature;
    }

    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    public short getWind() {
        return wind;
    }

    public void setWind(short wind) {
        this.wind = wind;
    }

    public short getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(short precipitation) {
        this.precipitation = precipitation;
    }



}
