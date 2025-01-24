package com.example.SpringnovablesProject.Repository;

import com.example.SpringnovablesProject.Domain.Radiation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Repository
public class RadiationRepository {

    private final Path pathJson;

    //ESTA CLASE GESTIONA EL FICHERO JSON
    public RadiationRepository(@Value("${pathjson}") Path pathJson){
        this.pathJson=pathJson;
    }

    //Todo RELLENAR TODO ESTE CODIGO
    //Devuelve una radiacion aportandole todos estos parametros
   /* public short findBy(String longitud, String latitud, short anio){
        return 0;
    }*/


    public Radiation findRadiation(String latitud,String longitud,  short anio) {
        // Simulando la lectura del archivo JSON con un repositorio en memoria
        List<Radiation> radiaciones = findAll(); // Método que devuelve todas las radiaciones del archivo JSON

        /*  System.out.println("DATOS QUE HAY QUE COMPARAR: "+ "Latidud: " + latitud + ", Longitud: "+ longitud+", Anio: "+ anio);
           Imprime todas las radiaciones para depuración
        System.out.println("Radiaciones cargadas del JSON:");
        radiaciones.forEach(r -> System.out.println("Radiation: Latitud=" + r.getLatitud()
                + ", Longitud=" + r.getLongitud() + ", Año=" + r.getAnio()+ ", radiation= "+r.getRadiation()));
        */

        // Busca una radiación que coincida con los parámetros
        Radiation encontrada = radiaciones.stream()
                .filter(r -> {
                    System.out.println("Comparando con: Latitud=" + r.getLatitud()
                            + ", Longitud=" + r.getLongitud() + ", Año=" + r.getAnio());
                    return r.getLatitud().equals(latitud)
                            && r.getLongitud().equals(longitud)
                            && r.getAnio() == anio;
                })
                .findFirst()
                .orElse(null); // Devuelve null si no se encuentra

        if (encontrada == null) {
            System.out.println("No se encontró ninguna radiación con los parámetros: Latitud="
                    + latitud + ", Longitud=" + longitud + ", Año=" + anio);
        } else {
            System.out.println("Radiación encontrada: Latitud=" + encontrada.getLatitud()
                    + ", Longitud=" + encontrada.getLongitud()
                    + ", Año=" + encontrada.getAnio()
                    + ", Radiacion=" + encontrada.getRadiation());
        }

        return encontrada;
    }

    //ESTE METODO DEVUELVE LA LISTA DE OBJETOS RADIACION BUSCANDO EN EL JSON
    public List<Radiation> findAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Lee el contenido del archivo JSON
            String jsonContent = Files.readString(pathJson);

            // Convierte el JSON en una lista de objetos Radiation
            return objectMapper.readValue(jsonContent, new TypeReference<List<Radiation>>() {});
        } catch (IOException e) {
            // Manejo de errores: podrías lanzar una excepción personalizada
            throw new RuntimeException("Error leyendo el archivo JSON", e);
        }
    }

    //ESTE METODO GUARDA UNA NUEVA RADIACION EN EL FICHERO JSON
    public void saveRadiation(String longitud, String latitud, short anio, short radiation){
        // Leer todas las radiaciones existentes
        List<Radiation> radiationlist = findAll();

        // Crear un nuevo objeto Radiation
        Radiation newRadiation = new Radiation(longitud,latitud,anio,radiation);

        //Añadir objeto a la lista
        radiationlist.add(newRadiation);

        //Escribir lista actualizada en JSON
        saveJSON(radiationlist);
    }


    public void actualizarRadiation(Radiation nuevaRadiacion, Radiation viejaRadiacion){
        System.out.println("ESTA ES LA NUEVA RADIACION:"
                +nuevaRadiacion.getLatitud()+", "
                +nuevaRadiacion.getLongitud()+", "
                +nuevaRadiacion.getAnio()+" ,"
                +nuevaRadiacion.getRadiation());

        System.out.println("ESTA ES LA VIEJA RADIACION:"
                +viejaRadiacion.getLatitud()+", "
                +viejaRadiacion.getLongitud()+", "
                +viejaRadiacion.getAnio()+" ,"
                +viejaRadiacion.getRadiation());

        List<Radiation> radiaciones = findAll();
        // Buscar y actualizar el elemento
        Radiation actualizado = radiaciones.stream()
                .filter(r -> r.getRadiation()==viejaRadiacion.getRadiation()
                        && r.getLongitud().equals(viejaRadiacion.getLongitud())
                        && r.getLatitud().equals(viejaRadiacion.getLatitud())
                        && r.getAnio() == viejaRadiacion.getAnio())
                .findFirst()
                .orElse(null);
        if (actualizado != null) {
            actualizado.setLatitud(nuevaRadiacion.getLatitud());
            actualizado.setLongitud(nuevaRadiacion.getLongitud());
            actualizado.setAnio(nuevaRadiacion.getAnio());
            actualizado.setRadiation(nuevaRadiacion.getRadiation());
            saveJSON(radiaciones);
        }else {

            System.out.println("Error actualizar radiacion");
        }


    }

    //ESTE METODO ELIMINA UNA RADIACION YA EXISTENTE EN EL FICHERO JSON
    public void deleteRadiation(String longitud, String latitud, short anio, short radiation){
        List<Radiation> radiaciones = findAll();
        // Buscar y actualizar el elemento
        Radiation toDelete = radiaciones.stream()
                .filter(r -> r.getRadiation()==radiation
                        && r.getLongitud().equals(longitud)
                        && r.getLatitud().equals(latitud)
                        && r.getAnio() == anio)
                .findFirst()
                .orElse(null);

        if (toDelete != null) {
            radiaciones.remove(toDelete);
            saveJSON(radiaciones);
        }else {
            System.out.println("Error actualizar radiacion");
        }
    }
    private void saveJSON (List<Radiation> radiations){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Sobrescribir el archivo con los datos actualizados
            String jsonContent = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(radiations);
            Files.writeString(pathJson, jsonContent);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo JSON", e);
        }
    }
}