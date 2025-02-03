package com.example.SpringnovablesProject.Controller;

import com.example.SpringnovablesProject.Domain.MeditionDTO;
import com.example.SpringnovablesProject.Domain.MeditionFinal;
import com.example.SpringnovablesProject.Service.SpringnovablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/springnovables")
public class PagesController {

    @Autowired
    SpringnovablesService service;


    @GetMapping("all")
    public ResponseEntity<List<MeditionFinal>> getAll(){
        List<MeditionFinal> mediciones = this.service.findAll();

        if (mediciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            //ResponseEntity.ok crea la respuesta HTTP con codigo de estado 200. Como cuerpo de la respuesta
            //se pasa el objeto pasado como argumento, en este caso la lista mediciones.
            System.out.println("Mediciones encontradas");
            return ResponseEntity.ok(mediciones);
        }
    }

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

    @GetMapping(value = "delete/{id}")
    public void deleteMedicionById(@PathVariable Long id) {
        var medicion = service.findById(id); // Obtener la medición antes de eliminarla
        boolean ok = this.service.deleteMedicion(id); // Eliminar la medición
        if (ok && medicion.isPresent()) {
            System.out.println("Medición " + medicion + " eliminada");
        } else {
            System.out.println("Error al eliminar la medición con ID " + id);
        }
    }


    //TODO ESTOS SON LOS METODOS DE THYMELEAF APARTE

   //Muestra mediciones
    @GetMapping("/mediciones")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String listarMediciones(Model model, Authentication authentication) {
        List<MeditionFinal> mediciones = service.findAll();
        model.addAttribute("mediciones", mediciones);
        // Obtener el rol del usuario autenticado
        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        // Redirigir a la vista correspondiente
        if (esAdmin) {
            return "mediciones";  // Vista para administradores
        } else {
            return "medicionesUser";  // Vista para usuarios
        }
    }

    @GetMapping("/mediciones/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public String crearMedicionForm(Model model) {
        // Se crea un objeto vacío de MeditionFinal para mostrar en el formulario de creación
        model.addAttribute("medicion", new MeditionFinal());
        return "FormularioCrearMedicion";
    }

    // Mostrar formulario de edición
    @GetMapping("/mediciones/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<MeditionFinal> medicionOpt = service.findById(id);

        if (medicionOpt.isPresent()) {
            model.addAttribute("medicion", medicionOpt.get());
            return "FormularioEditarMedicion"; // Cargar el formulario con los datos
        } else {
            return "redirect:/springnovables/mediciones"; // Redirigir si no existe
        }
    }

    // Manejar la actualización con PUT
    @PostMapping("/mediciones/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String actualizarMedicion(@PathVariable Long id, @ModelAttribute MeditionFinal medicion) {
        service.updateMedicion(medicion, id);
        return "redirect:/springnovables/mediciones"; // Volver a la lista después de actualizar
    }

    //Guarda la nueva medicion. Este metodo recibe los datos del formulario y los guarda en la base de datos.
    // Guardar una nueva medición
    @PostMapping("/mediciones/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarMedicion(@ModelAttribute MeditionFinal medicion) {
        try {
            service.create(medicion);  // Crear la nueva medición en la base de datos
            return "redirect:/springnovables/mediciones";  // Redirigir a la lista de mediciones
        } catch (Exception e) {
            e.printStackTrace();  // Log para depuración
            return "error";  // Si ocurre un error, redirigir a una vista de error
        }
    }

    // Eliminar una medición
    @RequestMapping(value = "/mediciones/eliminar/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarMedicion(@PathVariable Long id) {
        try {
            service.deleteMedicion(id);  // Método que elimina la medición
            return "redirect:/springnovables/mediciones";  // Redirigir a la lista de mediciones
        } catch (Exception e) {
            e.printStackTrace();
            return "error";  // Redirigir a una página de error si algo sale mal
        }
    }

}