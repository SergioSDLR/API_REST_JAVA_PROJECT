package com.example.MeteojavaProject.Domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SSRMediciones")
public class MeditionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pkMeditionID;

    private String latitud;
    private String longitud;
    private short anio;
    private short temperature;
    private short wind;
    private short precipitation;


    public long getPk_MedicionID() {
        return pkMeditionID;
    }

    public void setPk_MedicionID(long pk_MedicionID) {
        this.pkMeditionID = pk_MedicionID;
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

    //Metodo para retornar una medicion en Lista
    public List<Object> toList(MeditionDTO medicion1){
        List<Object> listaMedicion= new ArrayList();
        listaMedicion.add(medicion1.getPk_MedicionID());
        listaMedicion.add(medicion1.getLatitud());
        listaMedicion.add(medicion1.getLongitud());
        listaMedicion.add(medicion1.getAnio());
        listaMedicion.add(medicion1.getTemperature());
        listaMedicion.add(medicion1.getWind());
        listaMedicion.add(getWind());
        return listaMedicion;
    }
}
