package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.response.SearchResponseBody;
import org.springframework.stereotype.Component;

/**
 * Маппер для мапинга данных из объекта типа Vehicle
 * в оьъект типа SearchResponseBody
 * (так как это маппер, реализует интерфес Mapper)
 */
@Component
public class VehicleToSearchGuidResponseMapper implements Mapper<Vehicle, SearchResponseBody> {
    /**
     * Метод который мапит поля из Vehicle в SearchResponseBody
     *
     * @param vehicle Объект типа Vehicle
     * @return созданный и заполненный данными из Vehicle объект типа SearchResponseBody
     */
    @Override
    public SearchResponseBody map(Vehicle vehicle) {
        //cоздаём экземпляр класса SearchResponseBody и заполняем его данными из Vehicle
        //с помощью методов get и set
        SearchResponseBody searchResponseBody = new SearchResponseBody();
        searchResponseBody.setCostUsd(vehicle.getCostUsd());
        searchResponseBody.setDateInsert(vehicle.getDateInsert());
        searchResponseBody.setEngine(vehicle.getEnginej().getEngine());
        searchResponseBody.setEnginePowerBhp(vehicle.getEnginej().getEnginePowerBhp());
        searchResponseBody.setGuid(vehicle.getGuid());
        searchResponseBody.setMarque(vehicle.getMarqueModelj().getMarquej().getMarque());
        searchResponseBody.setModel(vehicle.getMarqueModelj().getModelj().getModel());
        searchResponseBody.setTopSpeedMph(vehicle.getTopSpeedMph());
        searchResponseBody.setVehicleType(vehicle.getVehicletypej().getVehicleType());
        return searchResponseBody;
    }

    @Override
    public void mapFromInputInOutput(Vehicle vehicle, SearchResponseBody searchResponseBody) {

    }
}
