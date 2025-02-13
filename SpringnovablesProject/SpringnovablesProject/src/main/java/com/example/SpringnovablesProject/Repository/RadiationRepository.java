package com.example.SpringnovablesProject.Repository;

import com.example.SpringnovablesProject.Domain.Radiation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RadiationRepository {

    private final String pathJson;

    /**Con @Value("${pathjson}") Spring inyecta un valor desde el application.properties en la variable pathJson**/
    public RadiationRepository(@Value("${pathjson}") String pathJson) {
        this.pathJson = pathJson;
    }


    //Metodo para devolver una radiacion especifica en el JSON que coincida por lat, long y anio
    public Radiation findRadiation(String latitud, String longitud, short anio) {
        List<Radiation> radiaciones = findAll();

        return radiaciones.stream()
                .filter(r -> r.getLatitud().equals(latitud)
                        && r.getLongitud().equals(longitud)
                        && r.getAnio() == anio)
                .findFirst()
                .orElse(null);
    }

    //Metodo para leer el archivo JSON y convertirlo en objetos JAVA
    public List<Radiation> findAll() {
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Paths.get(pathJson);

        if (!Files.exists(filePath)) {
            return new ArrayList<>(); // Si el archivo no existe, devolver lista vacía
        }

        /** El objectMapper se usa para convertir el JSON en una lista de Radiation **/

        try (InputStream inputStream = Files.newInputStream(filePath)) {
            return objectMapper.readValue(inputStream, new TypeReference<List<Radiation>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo JSON", e);
        }
    }

    //Metodo para guardar una radiacion nueva en el archivo JSON
    public void saveRadiation(String longitud, String latitud, short anio, short radiation) {
        List<Radiation> radiationList = findAll();
        Radiation newRadiation = new Radiation(longitud, latitud, anio, radiation);
        radiationList.add(newRadiation);
        saveJSON(radiationList);
    }

    //Metodo para modificar los datos de una radiacion comparando sus antiguos datos con los nuevos datos
    public void updateRadiation(Radiation nuevaRadiacion, Radiation viejaRadiacion) {
        List<Radiation> radiaciones = findAll();
        Radiation actualizado = radiaciones.stream()
                .filter(r -> r.getLatitud().equals(viejaRadiacion.getLatitud())
                        && r.getLongitud().equals(viejaRadiacion.getLongitud())
                        && r.getAnio() == viejaRadiacion.getAnio()
                        && r.getRadiation() == viejaRadiacion.getRadiation())
                .findFirst()
                .orElse(null);

        if (actualizado != null) {
            actualizado.setLatitud(nuevaRadiacion.getLatitud());
            actualizado.setLongitud(nuevaRadiacion.getLongitud());
            actualizado.setAnio(nuevaRadiacion.getAnio());
            actualizado.setRadiation(nuevaRadiacion.getRadiation());
            saveJSON(radiaciones);
        } else {
            System.out.println("Error: no se encontró la radiación para actualizar.");
        }
    }

    //Metodo para eliminar una radiacion comparandola con una del archivo JSON
    public void deleteRadiation(Radiation radiation) {
        List<Radiation> radiaciones = findAll();
        boolean removed = radiaciones.removeIf(r -> r.getLatitud().equals(radiation.getLatitud())
                && r.getLongitud().equals(radiation.getLongitud())
                && r.getAnio() == radiation.getAnio()
                && r.getRadiation() == radiation.getRadiation());

        if (removed) {
            saveJSON(radiaciones);
        } else {
            System.out.println("Error: No se encontró la radiación para eliminar.");
        }
    }

    private void saveJSON(List<Radiation> radiations) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(radiations);
            Files.writeString(Paths.get(pathJson), jsonContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo JSON", e);
        }
    }
}