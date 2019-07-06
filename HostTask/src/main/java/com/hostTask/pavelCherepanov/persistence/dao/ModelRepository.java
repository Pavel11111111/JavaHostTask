package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    //sql запрос для поиска id модели по её названию
    @Query("SELECT modelID FROM Model u WHERE u.model = :model")
    Integer findByModel(
            @Param("model") String model
    );
}
