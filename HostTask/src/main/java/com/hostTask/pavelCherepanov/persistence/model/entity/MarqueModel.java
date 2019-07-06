package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект-отображение таблицы с id марки и модели в базе данных.
 */
@ToString
@Data
@Entity
@Table(name = "marqueModel")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MarqueModel {
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
    @Column(name = "marqueModelID")
    Integer marqueModelID;
    /**
     * указываем связь многие к одному с таблицей marque
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "marqueID")
    Marque marquej;
    /**
     * указываем связь многие к одному с таблицей model
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelID")
    Model modelj;
    /**
     * указываем связь один ко многим с таблицей vehicle
     */
    @OneToMany(mappedBy = "marqueModelj", fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
}

