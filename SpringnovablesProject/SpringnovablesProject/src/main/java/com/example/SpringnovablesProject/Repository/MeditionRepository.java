package com.example.SpringnovablesProject.Repository;

import com.example.SpringnovablesProject.Domain.MeditionDTO;
import com.example.SpringnovablesProject.Domain.MeditionFinal;
import com.example.SpringnovablesProject.Domain.Radiation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MeditionRepository {
    //TODO ESTA CLASE GESTIONA OBJETOS MEDICION FINALES, TOMANDO DATOS TANTO DE LA BBDD COMO DEL FICHERO JSON PARA CONSTRUIR UN OBJETO MEDICION FINAL

    @Autowired
    private RadiationRepository repoRadiation;
    @Autowired
    private MeditionRestRepository repoMeditionAPI;


    public MeditionDTO findByID(int id) {
        return null;
    }

    public void save(MeditionFinal medicion) {
        //TODO REVISAR ESTE medicion.toDTO
        repoMeditionAPI.save(medicion.toDTO());
        repoRadiation.saveRadiation(medicion.getLongitud(), medicion.getLatitud(), medicion.getAnio(), medicion.getRadiation());
    }

    //TODO ACTUALIZAR LOS DATOS DE LOS REPOSITORIOS
    public void update(MeditionFinal medicion, Radiation oldRadiation, Radiation newRadiation){

        /*System.out.println("La medicion actualizada es: "+ medicion.getAnio()+ ", " +
                medicion.getLatitud()+ ", " +
                medicion.getLatitud()+ ", " +
                medicion.getTemperature()+ ", " +
                medicion.getPrecipitation()+ ", " +
                medicion.getWind()+ ", " +
                medicion.getRadiation());*/
        repoMeditionAPI.update(medicion.toDTO());

        repoRadiation.actualizarRadiation(newRadiation,oldRadiation);
        // repoRadiation.updateRadiation(medicion.getLongitud(), medicion.getLatitud(), medicion.getAnio(), medicion.getRadiation());
    }



    public Optional<MeditionFinal> findByPkMedicionID(Long id) {
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

}
