package com.example.SpringnovablesProject.Controller;

import com.example.SpringnovablesProject.Domain.MeditionDTO;
import com.example.SpringnovablesProject.Domain.MeditionFinal;
import com.example.SpringnovablesProject.Service.SpringnovablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/springnovables")
public class PagesController {

    @Autowired
    SpringnovablesService service;

    @ResponseBody
    @GetMapping(path = "{id}")
    public Optional <MeditionFinal> getMedicionById(@PathVariable Long id){
        return this.service.findById(id);
    }

    @PutMapping (value="update/{id}")
    public void updateMedicionById(@RequestBody MeditionFinal request, @PathVariable ("id") Long id){
        this.service.updateMedicion(request,id);
    }

    @PostMapping("create")
    public void create(@RequestBody MeditionFinal request) {
        this.service.create(request);
    }

    /* @GetMapping("mediciones")
    public ResponseEntity<List<MedicionDTO>> getAll(){
        List<MedicionDTO> mediciones = this.service.getAllMediciones();

        if (mediciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            //ResponseEntity.ok crea la respuesta HTTP con codigo de estado 200. Como cuerpo de la respuesta
            //se pasa el objeto pasado como argumento, en este caso la lista mediciones.
            System.out.println("Mediciones encontradas");
            return ResponseEntity.ok(mediciones);
        }
    }*/

}