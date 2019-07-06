package com.hostTask.pavelCherepanov.persistence.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddVehicleRequestBody {
    String vehicleType;
    String marque;
    String model;
    String engine;
    int enginePowerBhp;
    int topSpeedMph;
    int costUsd;
    int price;
    String status;
}
