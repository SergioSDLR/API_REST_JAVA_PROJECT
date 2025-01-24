package com.example.MeteojavaProject.Repository;

import com.example.MeteojavaProject.Domain.MeditionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeditionRepository extends JpaRepository<MeditionDTO,Long> {

    Optional<MeditionDTO> findByPkMeditionID (Long pkMedicionID);
    void deleteByPkMeditionID(long pkMedicionID);

    @Query("SELECT m FROM MeditionDTO m WHERE m.anio = :anio")
    List<MeditionDTO> findByAnio(@Param("anio") short anio);
}
