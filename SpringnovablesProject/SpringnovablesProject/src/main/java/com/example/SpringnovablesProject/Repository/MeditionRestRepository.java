package com.example.SpringnovablesProject.Repository;

import com.example.SpringnovablesProject.Domain.MeditionDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Repository
public class MeditionRestRepository {

    //TODO RELLENAR CON LA API (QUE CONECTA CON LA BASE DE DATOS) Y HACERLO MEDIANTE INYECCIONES @VALUE, AUTOWIRED ETC.
    private final String URL_ENDPOINT_SPRINGNOVABLES = "http://localhost:8080/mediciones";
    private final RestTemplate restTemplate = new RestTemplate();



    //OBTENER LISTADO DE LAS MEDICIONES DE LA API
    public List<MeditionDTO> getAllMediciones() {
        ResponseEntity<List<MeditionDTO>> response = restTemplate.exchange(URL_ENDPOINT_SPRINGNOVABLES+"/all", HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }



    //CREACION DE UNA MEDICION EN LA API
    public void save(MeditionDTO medicion) {
        try {
            // Realiza la solicitud POST al endpoint
            restTemplate.postForEntity(
                    URL_ENDPOINT_SPRINGNOVABLES + "/create", // URL de la API
                    medicion, // Cuerpo de la solicitud
                    Void.class // No esperamos un cuerpo en la respuesta
            );
            System.out.println("Medición guardada exitosamente en la API.");
        } catch (Exception e) {
            // Manejo de errores en caso de fallo en la solicitud
            System.err.println("Error al guardar la medición: " + e.getMessage());
        }
    }

    //ACTUALIZA UNA MEDICION EN LA API
    public void update(MeditionDTO medicion) {
        try {
            // Realiza la solicitud PUT al endpoint
            restTemplate.exchange(
                    URL_ENDPOINT_SPRINGNOVABLES + "/update/" + medicion.getPk_MedicionID(), // Endpoint con el ID de la medición
                    HttpMethod.PUT, // Método HTTP PUT
                    new org.springframework.http.HttpEntity<>(medicion), // Cuerpo de la solicitud
                    Void.class // No esperamos un cuerpo en la respuesta
            );
            System.out.println("Medición actualizada exitosamente en la API.");
        } catch (Exception e) {
            // Manejo de errores en caso de fallo en la solicitud
            System.err.println("Error al actualizar la medición con ID " + medicion.getPk_MedicionID() + ": " + e.getMessage());
        }
    }

    //SE BUSCA UNA MEDICION EN LA API CON EL ID ASIGNADO
    public Optional<MeditionDTO> findByPKMedicionID(Long id) {
        try {
            // Realiza la solicitud GET al endpoint concatenando el ID
            MeditionDTO medicion = restTemplate.getForObject(URL_ENDPOINT_SPRINGNOVABLES + "/" + id, MeditionDTO.class);
            // Si se encuentra la medición, envolverla en un Optional
            return Optional.ofNullable(medicion);
        } catch (Exception e) {
            // Manejo básico de errores
            System.err.println("Error al obtener la medición con ID: " + id);
            // Devolver un Optional vacío en caso de error
            return Optional.empty();
        }
    }

    public void deleteMedition(Long id) {
        try {
            // Realiza la solicitud GET al endpoint para eliminar
            restTemplate.getForObject(URL_ENDPOINT_SPRINGNOVABLES + "/delete/" + id, Void.class);
            System.out.println("Medición con ID " + id + " eliminada exitosamente de la API.");
        } catch (Exception e) {
            // Manejo básico de errores
            System.err.println("Error al eliminar la medición con ID " + id + ": " + e.getMessage());
        }
    }


}