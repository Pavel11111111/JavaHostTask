package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект-отображение таблицы с статусами ТС в базе данных.
 */
@ToString
@Data
@Entity
@Table(name = "status")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Status {
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
    @Column(name = "statusID")
    Integer statusID;
    /**
     * Поля объекта, соответствующие полям таблицы в БД.
     */
    @Column(name = "status")
    String status;
    /**
     * указываем связь один ко многим с таблицей vehicle
     */
    @OneToMany(mappedBy = "statusj", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}

