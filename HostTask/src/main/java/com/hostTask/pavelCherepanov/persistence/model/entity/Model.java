package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект-отображение таблицы с моделями ТС в базе данных.
 */
@ToString
@Data
@Entity
@Table(name = "model")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Model {
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
    @Column(name = "modelID")
    Integer modelID;
    /**
     * Поля объекта, соответствующие полям таблицы в БД.
     */
    @Column(name = "model")
    String model;
    /**
     * указываем связь один ко многим с таблицей marqueModel
     */
    @OneToMany(mappedBy = "modelj", fetch = FetchType.EAGER)
    private List<MarqueModel> marqueModels = new ArrayList<>();
}
