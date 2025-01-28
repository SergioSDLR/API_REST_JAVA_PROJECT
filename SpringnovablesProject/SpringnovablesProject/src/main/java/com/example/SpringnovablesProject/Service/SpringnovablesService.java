package com.example.SpringnovablesProject.Service;

import com.example.SpringnovablesProject.Domain.MeditionFinal;
import com.example.SpringnovablesProject.Domain.Radiation;
import com.example.SpringnovablesProject.Repository.MeditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SpringnovablesService {
    @Autowired
    private MeditionRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();

    //METODOS

    //ENCUENTRA UNA MEDICION POR ID
    public Optional <MeditionFinal> findById(Long id){
        return repository.findByPkMeditionID(id);
    }

    //ACTUALIZA UNA MEDICION POR ID
    public void updateMedicion(MeditionFinal request, Long id) {
        Optional <MeditionFinal> optionalMedition = repository.findByPkMeditionID(id);

        //AQUI TENEMOS LOS DATOS QUE HAY QUE COMPARAR CON EL JSON
        System.out.println("El optionalMeditionFinal tiene valores de Longitud, latitud y año: "
                + optionalMedition.get().getLatitud() + ","
                + optionalMedition.get().getLongitud()+ ","
                + optionalMedition.get().getAnio()+ ","
                + optionalMedition.get().getRadiation());
        Radiation oldRadiation= new Radiation();
        oldRadiation= repository.findByRadiation(optionalMedition.get().getLatitud(),optionalMedition.get().getLongitud(),optionalMedition.get().getAnio(),optionalMedition.get().getRadiation());

        //Validar si existe la medicion
        if (optionalMedition.isEmpty()){
            throw new NoSuchElementException("Medicion con ID" + id + "no encontrada");
        }else {
            //Actualizar campos
            MeditionFinal meditionFinal=optionalMedition.get();
            meditionFinal.setAnio(request.getAnio());
            meditionFinal.setAnio(request.getAnio());
            meditionFinal.setLatitud(request.getLatitud());
            meditionFinal.setLongitud(request.getLongitud());
            meditionFinal.setTemperature(request.getTemperature());
            meditionFinal.setPrecipitation(request.getPrecipitation());
            meditionFinal.setWind(request.getWind());
            meditionFinal.setRadiation(request.getRadiation());

            //Creamos la nueva radiacion con los datos que queremos actualizar
            Radiation newRadiation= new Radiation();
            newRadiation.setLatitud(request.getLatitud());
            newRadiation.setLongitud(request.getLongitud());
            newRadiation.setAnio(request.getAnio());
            newRadiation.setRadiation(request.getRadiation());
            repository.update(meditionFinal, oldRadiation, newRadiation);
        }
    }

    //CREA UNA MEDICION
    public void create(MeditionFinal request) {
        repository.save(request);
    }

    //BORRA UNA MEDICION
    public boolean deleteMedicion(Long id) {
        try {
            Optional <MeditionFinal> optionalMedition = repository.findByPkMeditionID(id);
            Radiation radiationToDelete= new Radiation();
            radiationToDelete= repository.findByRadiation(optionalMedition.get().getLatitud(),
                                                    optionalMedition.get().getLongitud(),
                                                    optionalMedition.get().getAnio(),
                                                    optionalMedition.get().getRadiation());
            repository.deleteByPkMeditionID(id,radiationToDelete);
            return true;
        }catch (Exception e) {
            System.out.println("ERROR EN EL METODO DE LA CLASE SERVIDORA");
            return false;
        }
    }

    //ENCUENTRA TODAS LAS MEDICIONES
    public List<MeditionFinal> findAll() {
        return repository.findAll();
    }
}
