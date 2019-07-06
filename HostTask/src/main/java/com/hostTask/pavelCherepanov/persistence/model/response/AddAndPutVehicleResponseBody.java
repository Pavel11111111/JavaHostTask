package com.hostTask.pavelCherepanov.persistence.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddAndPutVehicleResponseBody {
    String guid;
    String vehicleType;
    String marque;
    String model;
    String engine;
    int enginePowerBhp;
    int topSpeedMph;
    int costUsd;
    int price;
    LocalDateTime dateInsert;
    LocalDateTime datePurchase;
    String status;
}
