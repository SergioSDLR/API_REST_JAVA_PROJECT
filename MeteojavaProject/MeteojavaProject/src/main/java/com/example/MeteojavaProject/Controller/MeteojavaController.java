package com.example.MeteojavaProject.Controller;

import com.example.MeteojavaProject.Domain.MeditionDTO;
import com.example.MeteojavaProject.Service.MeteojavaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mediciones")
public class MeteojavaController {
    @Autowired
    private MeteojavaService service;

    @GetMapping(path = "{id}")
    public Optional<MeditionDTO> getMedicionById(@PathVariable Long id){
        return this.service.getByID(id);
    }

    @ResponseBody
    @GetMapping(value="delete/{id}")
    public void deleteMedicionById(@PathVariable Long id){
        Long idAEliminar=id;
        boolean ok = this.service.deleteMedicion(id);
        if(ok){
            System.out.println("Medicion: "+ idAEliminar + " eliminada");
        }else {
            System.out.println("Error");
        }
    }
    @GetMapping("all")
    public ResponseEntity<List<MeditionDTO>> getAll(){
        List<MeditionDTO> mediciones = this.service.getAll();

        if (mediciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            //ResponseEntity.ok crea la respuesta HTTP con codigo de estado 200. Como cuerpo de la respuesta
            //se pasa el objeto pasado como argumento, en este caso la lista mediciones.
            return ResponseEntity.ok(mediciones);
        }
    }
    @GetMapping(value="anio/{anio}")
    public ResponseEntity <List<MeditionDTO>> filterByAnio(@PathVariable short anio){
        List<MeditionDTO> mediciones = this.service.filterByAnio(anio);

        if (mediciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            //ResponseEntity.ok crea la respuesta HTTP con codigo de estado 200. Como cuerpo de la respuesta
            //se pasa el objeto pasado como argumento, en este caso la lista mediciones.
            return ResponseEntity.ok(mediciones);
        }
    }

    @PutMapping (value="update/{id}")
    public MeditionDTO updateMedicionById(@RequestBody MeditionDTO request, @PathVariable ("id") Long id){
        return this.service.updateMedicion(request,id);
    }

    @PostMapping("create")
    public void create(@RequestBody MeditionDTO medicion) {
        service.create(medicion);
        System.out.println("Medicion creada.");
    }



}
