package com.example.SpringnovablesProject.Domain;

import java.util.ArrayList;
import java.util.List;

public class MeditionFinal {

    //Estos campos tienen que salir de una consulta a un servicio REST
    private long pk_MedicionID;
    private String latitud;
    private String longitud;
    private short anio;
    private short temperature;
    private short wind;
    private short precipitation;

    //Este campo tiene que salir de un fichero JSON
    private short radiation;

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
    public short getRadiation() {
        return radiation;
    }
    public void setRadiation(short radiation) {
        this.radiation = radiation;
    }


    public MeditionDTO toDTO(){
        MeditionDTO medicionDTO=new MeditionDTO();
        medicionDTO.setPk_MedicionID(this.pk_MedicionID);
        medicionDTO.setLatitud(this.latitud);
        medicionDTO.setLongitud(this.longitud);
        medicionDTO.setAnio(this.anio);
        medicionDTO.setTemperature(this.temperature);
        medicionDTO.setWind(this.wind);
        medicionDTO.setPrecipitation(this.precipitation);
        return medicionDTO;
    }
    public MeditionFinal fromDTO(MeditionDTO medicionDTO) {
        MeditionFinal medicionFinal = new MeditionFinal();
        medicionFinal.setPk_MedicionID(medicionDTO.getPk_MedicionID());
        medicionFinal.setLatitud(medicionDTO.getLatitud());
        medicionFinal.setLongitud(medicionDTO.getLongitud());
        medicionFinal.setAnio(medicionDTO.getAnio());
        medicionFinal.setTemperature(medicionDTO.getTemperature());
        medicionFinal.setWind(medicionDTO.getWind());
        medicionFinal.setPrecipitation(medicionDTO.getPrecipitation());

        return medicionFinal;
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