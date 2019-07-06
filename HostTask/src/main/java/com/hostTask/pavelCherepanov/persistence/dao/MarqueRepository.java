package com.hostTask.pavelCherepanov.persistence.dao;


import com.hostTask.pavelCherepanov.persistence.model.entity.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarqueRepository extends JpaRepository<Marque, Integer> {
    //sql запрос для поиска marqueID по названию марки
    @Query("SELECT marqueID FROM Marque u WHERE u.marque = :marque")
    Integer findByMarque(
            @Param("marque") String marque
    );
}