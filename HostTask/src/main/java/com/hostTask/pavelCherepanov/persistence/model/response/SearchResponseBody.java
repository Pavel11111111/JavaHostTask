package com.hostTask.pavelCherepanov.persistence.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchResponseBody {
    String guid;
    String vehicleType;
    String marque;
    String model;
    String engine;
    int enginePowerBhp;
    int topSpeedMph;
    int costUsd;
    LocalDateTime dateInsert;
}
