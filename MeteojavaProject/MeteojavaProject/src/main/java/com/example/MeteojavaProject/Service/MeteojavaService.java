package com.example.MeteojavaProject.Service;

import com.example.MeteojavaProject.Domain.MeditionDTO;
import com.example.MeteojavaProject.Repository.MeditionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MeteojavaService {
    @Autowired
    private MeditionRepository repository;

    public Optional<MeditionDTO> getByID(Long id){
        return repository.findByPkMeditionID(id);
    }

    public List<MeditionDTO> filterByAnio(short anio){
        return repository.findByAnio(anio);
    }

    public List<MeditionDTO> getAll (){
        return repository.findAll();
    }

    public MeditionDTO updateMedicion (MeditionDTO request, long id){
        //Buscar la medicion por ID
        Optional <MeditionDTO> optionalMedicion = repository.findByPkMeditionID(id);

        //Validar si existe la medicion
        if (optionalMedicion.isEmpty()){
            throw new NoSuchElementException("Medicion con ID" + id + "no encontrada");
        }

        //Actualizar los campos
        MeditionDTO m1 = optionalMedicion.get();
        m1.setAnio(request.getAnio());
        m1.setLatitud(request.getLatitud());
        m1.setLongitud(request.getLongitud());
        m1.setTemperature(request.getTemperature());
        m1.setPrecipitation(request.getPrecipitation());
        m1.setWind(request.getWind());
        return repository.save(m1);
    }

    public void create(MeditionDTO medicion) {
        repository.save(medicion);
    }
    @Transactional
    public Boolean deleteMedicion (Long id){
        try {
            System.out.println(repository.findByPkMeditionID(id));
            repository.deleteByPkMeditionID(id);
            return true;
        }catch (Exception e) {
            System.out.println("ERROR EN EL METODO DE LA CLASE SERVIDORA");
            return false;
        }
    }



}