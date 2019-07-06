package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EngineRepository extends JpaRepository<Engine, Integer> {
    //sql запрос для поиска engineID по названию двигателя и его мощности
    @Query("SELECT engineID FROM Engine u WHERE u.engine = :engine AND u.enginePowerBhp = :enginePowerBhp")
    Integer findByEngineAndEnginePowerBhp(
            @Param("engine") String engine,
            @Param("enginePowerBhp") Integer enginePowerBhp
    );
}
