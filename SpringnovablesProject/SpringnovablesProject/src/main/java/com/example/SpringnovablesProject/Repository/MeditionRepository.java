package com.example.SpringnovablesProject.Repository;

import com.example.SpringnovablesProject.Domain.MeditionDTO;
import com.example.SpringnovablesProject.Domain.MeditionFinal;
import com.example.SpringnovablesProject.Domain.Radiation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MeditionRepository {

    //TODO ESTA CLASE GESTIONA OBJETOS MEDICION FINALES, TOMANDO DATOS TANTO DE LA BBDD COMO DEL FICHERO JSON PARA CONSTRUIR UN OBJETO MEDICION FINAL

    @Autowired
    private RadiationRepository repoRadiation;
    @Autowired
    private MeditionRestRepository repoMeditionAPI;


    public List<MeditionFinal> findAll(){
        List<MeditionFinal> listMeditionFinal=new ArrayList<>();

        // Obtener todas las mediciones de la API
        List<MeditionDTO> listMeditionDTO = repoMeditionAPI.getAllMediciones();
        //Recorrer cada medicion de la API
        for(MeditionDTO meditionDTO: listMeditionDTO){
            MeditionFinal medition = new MeditionFinal();
            medition.setPk_MedicionID(meditionDTO.getPk_MedicionID());
            medition.setLatitud(meditionDTO.getLatitud());
            medition.setLongitud(meditionDTO.getLongitud());
            medition.setAnio(meditionDTO.getAnio());
            medition.setTemperature(meditionDTO.getTemperature());
            medition.setWind(meditionDTO.getWind());
            medition.setPrecipitation(meditionDTO.getPrecipitation());

            // Buscar la radiación correspondiente en el repositorio de radiaciones
            Radiation radiation = repoRadiation.findRadiation(
                    medition.getLatitud(),
                    medition.getLongitud(),
                    medition.getAnio()
            );
            // Si se encuentra la radiación, asignarla
            if (radiation != null) {
                medition.setRadiation(radiation.getRadiation());
            } else {
                // Manejo de caso cuando no se encuentra radiación
                System.out.println("No se encontró radiación para la medición con ID: " + meditionDTO.getPk_MedicionID());
            }

            // Agregar el MeditionFinal a la lista
            listMeditionFinal.add(medition);
        }
    return listMeditionFinal;
    };
    public void save(MeditionFinal medicion) {
        //TODO REVISAR ESTE medicion.toDTO
        repoMeditionAPI.save(medicion.toDTO());
        repoRadiation.saveRadiation(medicion.getLongitud(), medicion.getLatitud(), medicion.getAnio(), medicion.getRadiation());
    }
    public void update(MeditionFinal medicion, Radiation oldRadiation, Radiation newRadiation){
        repoMeditionAPI.update(medicion.toDTO());
        repoRadiation.actualizarRadiation(newRadiation,oldRadiation);
    }
    public Optional<MeditionFinal> findByPkMeditionID(Long id) {
        // Buscar el MedicionDTO desde el repositorio de mediciones que conecta con la API
        Optional<MeditionDTO> optionalMedicionDTO = repoMeditionAPI.findByPKMedicionID(id);

        // Si no se encuentra la medición, devolver un Optional vacío
        if (optionalMedicionDTO.isEmpty()) {
            System.out.println("MedicionDTO no encontrada");
            return Optional.empty();
        }

        // Convertir MedicionDTO a MedicionFinal
        MeditionDTO medicionDTO = optionalMedicionDTO.get();
        MeditionFinal medicionFinal = new MeditionFinal();

        medicionFinal.setPk_MedicionID(medicionDTO.getPk_MedicionID());
        medicionFinal.setLatitud(medicionDTO.getLatitud());
        medicionFinal.setLongitud(medicionDTO.getLongitud());
        medicionFinal.setAnio(medicionDTO.getAnio());
        medicionFinal.setTemperature(medicionDTO.getTemperature());
        medicionFinal.setWind(medicionDTO.getWind());
        medicionFinal.setPrecipitation(medicionDTO.getPrecipitation());

        // Buscar la radiación correspondiente en el repositorio de radiaciones
        Radiation radiation = repoRadiation.findRadiation(medicionFinal.getLatitud(),medicionFinal.getLongitud(),medicionFinal.getAnio());

        // Si se encuentra la radiación, asignarla
        if (radiation != null) {
            medicionFinal.setRadiation(radiation.getRadiation());
        } else {
            // Manejo de caso cuando no se encuentra radiación
            System.out.println("Radiación no encontrada para la medición con ID: " + id);
        }

        // Devolver el MeditionFinal envuelto en un Optional
        return Optional.of(medicionFinal);
    }
    public Radiation findByRadiation(String longitud, String latitud, short anio, short radiation){

        Radiation radiationToFind=repoRadiation.findRadiation(longitud,latitud,anio);
       // System.out.println("BUSCANDO SI LOS VALORES SON LOS QUE HAY QUE COMPARAR"+ radiationToFind.getLongitud()+" "+ radiationToFind.getLatitud()+" "+ radiationToFind.getAnio()+" "+ radiationToFind.getRadiation());
        return radiationToFind;

    }
    public void deleteByPkMeditionID(Long id, Radiation radiation) {
        repoMeditionAPI.deleteMedition(id);
        repoRadiation.deleteRadiation(radiation);
    }
}
