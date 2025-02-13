
package com.example.MeteojavaProject;

import com.example.MeteojavaProject.Domain.MeditionDTO;
import com.example.MeteojavaProject.Repository.MeditionRepository;
import com.example.MeteojavaProject.Service.MeteojavaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
    public class DatabaseSeeder implements CommandLineRunner {

    //ESTA CLASE ESTA CREADA SIMPLEMENTE PARA GENERAR ALGUNAS MEDICIONES EN LA BASE DE DATOS Y QUE NO APAREZCA VACIA

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
        }
    }