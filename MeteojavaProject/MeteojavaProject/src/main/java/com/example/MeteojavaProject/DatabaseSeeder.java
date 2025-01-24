package com.example.MeteojavaProject;

import com.example.MeteojavaProject.Domain.MeditionDTO;
import com.example.MeteojavaProject.Repository.MeditionRepository;
import com.example.MeteojavaProject.Service.MeteojavaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
    public class DatabaseSeeder implements CommandLineRunner {

        private final MeditionRepository repository;
        private final MeteojavaService service=new MeteojavaService();

        public DatabaseSeeder(MeditionRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... args) throws Exception {
            MeditionDTO medicion = new MeditionDTO();
            medicion.setLatitud("1");
            medicion.setLongitud("1");
            medicion.setAnio((short) 2020);
            medicion.setTemperature((short) 25);
            medicion.setWind((short) 5);
            medicion.setPrecipitation((short) 2);
            repository.save(medicion);

            MeditionDTO medicion2 = new MeditionDTO();
            medicion2.setLatitud("24");
            medicion2.setLongitud("-1.6593");
            medicion2.setAnio((short) 2024);
            medicion2.setTemperature((short) 30);
            medicion2.setWind((short) 3);
            medicion2.setPrecipitation((short) 1);
            repository.save(medicion2);

       /* Medicion medicion3 = new Medicion();
        medicion3.setLatitud("99.3269");
        medicion3.setLongitud("51.6593");
        medicion3.setAnio((short) 1996);
        medicion3.setTemperature((short) 32);
        medicion3.setWind((short) 8);
        medicion3.setPrecipitation((short) 4);*/


            System.out.println("Datos iniciales guardados: " + repository.findAll());
            System.out.println("Medicion " + medicion.getPk_MedicionID()+ ": " + medicion.toList(medicion));
            System.out.println("Medicion " + medicion2.getPk_MedicionID()+ ": " + medicion2.toList(medicion2));



        /*repository.findAll().stream()
                .map(medicion1 -> new Object[]{
                        medicion1.getAnio(),
                        medicion1.getLatitud(),
                        medicion1.getLatitud(),
                        medicion1.getId(),
                        medicion1.getPrecipitation(),
                        medicion1.getTemperature(),
                        medicion1.getWind()
                })  // Convertimos cada objeto Persona en un array con sus valores
                .forEach(campos -> System.out.println(Arrays.toString(campos)));*/
        }


    }