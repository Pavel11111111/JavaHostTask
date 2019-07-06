package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.response.AddAndPutVehicleResponseBody;
import org.springframework.stereotype.Component;

/**
 * Маппер для мапинга данных из объекта типа Vehicle
 * в оьъект типа AddAndPutVehicleResponseBody
 * (так как это маппер, реализует интерфес Mapper)
 */
@Component
public class AddAndPutVehicleToVehicleResponseBodyMapper implements Mapper<Vehicle, AddAndPutVehicleResponseBody> {
    /**
     * Метод который получает объект типа Vehicle, и создаёт из него
     * объект типа AddAndPutVehicleResponseBody с помощью методов set и get
     *
     * @param vehicle Объект типа Vehicle
     * @return созданный и заполненный данными из Vehicle объект типа AddAndPutVehicleResponseBody
     */
    @Override
    public AddAndPutVehicleResponseBody map(Vehicle vehicle) {
        //создаём новый AddAndPutVehicleResponseBody
        AddAndPutVehicleResponseBody vehicleResponse = new AddAndPutVehicleResponseBody();
        //с помощью методов set и get заполняем его данными из Vehicle
        vehicleResponse.setGuid(vehicle.getGuid());
        vehicleResponse.setVehicleType(vehicle.getVehicletypej().getVehicleType());
        vehicleResponse.setMarque(vehicle.getMarqueModelj().getMarquej().getMarque());
        vehicleResponse.setModel(vehicle.getMarqueModelj().getModelj().getModel());
        vehicleResponse.setEngine(vehicle.getEnginej().getEngine());
        vehicleResponse.setEnginePowerBhp(vehicle.getEnginej().getEnginePowerBhp());
        vehicleResponse.setTopSpeedMph(vehicle.getTopSpeedMph());
        vehicleResponse.setCostUsd(vehicle.getCostUsd());
        vehicleResponse.setPrice(vehicle.getPrice());
        vehicleResponse.setDateInsert(vehicle.getDateInsert());
        vehicleResponse.setDatePurchase(vehicle.getDatePurchase());
        vehicleResponse.setStatus(vehicle.getStatusj().getStatus());
        //возвращаем его из метода
        return vehicleResponse;
    }

    @Override
    public void mapFromInputInOutput(Vehicle vehicle, AddAndPutVehicleResponseBody vehicleResponseBody) {

    }
}
