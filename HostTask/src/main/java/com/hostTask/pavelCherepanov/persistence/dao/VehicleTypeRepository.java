package com.hostTask.pavelCherepanov.persistence.dao;

import com.hostTask.pavelCherepanov.persistence.model.entity.Engine;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
    //sql запрос для поиска id типа ТС по его названию
    @Query("SELECT vehicleTypeID FROM VehicleType u WHERE u.vehicleType = :vehicletype")
    Integer findByVehicleType(
            @Param("vehicletype") String vehicleType
    );
}
