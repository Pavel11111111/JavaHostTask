package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    //sql запрос для поиска id статуса по его названию
    @Query("SELECT statusID FROM Status u WHERE u.status = :status")
    Integer findByStatus(
            @Param("status") String status
    );
}
