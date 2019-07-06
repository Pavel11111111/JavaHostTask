package com.hostTask.pavelCherepanov.persistence.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Объект-отображение таблицы с двигателями ТС в базе данных
 */
@ToString
@Data
@Entity
@Table(name = "engine")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Engine {
    /**
     * Аннотация обозначающая первичный ключ.
     */
    @Id
    /**
     * Указываем что хотим, чтобы при добавлении новой записи ID генерировался автоматически.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * для указания соответствия между сущностью и столбцом таблицы базы данных указываем аннотацию @column и в ней имя столбца в бд
     */
    @Column(name = "engineID")
    Integer engineID;
    /**
     * Поля объекта, соответствующие полям таблицы в БД.
     */
    @Column(name = "engine")
    String engine;

    @Column(name = "enginePowerBhp")
    Integer enginePowerBhp;

    /**
     * указываем связь один ко многим с таблицей vehicle
     */
    @OneToMany(mappedBy = "enginej", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}