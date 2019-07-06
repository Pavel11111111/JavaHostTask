package com.hostTask.pavelCherepanov.persistence.model.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Объект-отображение таблицы с транспортными средствами в базе данных.
 */
@ToString
@Data
@Entity
@Table(name = "vehicle")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Vehicle {
	/**
	 * Аннотация обозначающая первичный ключ.
	 */
	@Id
	/**
	 * для указания соответствия между сущностью и столбцом таблицы базы данных указываем аннотацию @column и в ней имя столбца в бд
	 */
	@Column(name = "guid")
	String guid;
	/**
	 * Поля объекта, соответствующие полям таблицы в БД.
	 */
	/**
	 * указываем связь многие к одному с таблицей VehicleType
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicleTypeID")
	VehicleType vehicletypej;

	/**
	 * указываем связь многие к одному с таблицей Engine
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "engineID")
	Engine enginej;

	/**
	 * указываем связь многие к одному с таблицей Status
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "statusID")
	Status statusj;

	/**
	 * указываем связь многие к одному с таблицей MarqueModel
	 */
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "marqueModelID")
	MarqueModel marqueModelj;

	@Column(name = "dateInsert")
	LocalDateTime dateInsert;

	@Column(name = "datePurchase")
	LocalDateTime datePurchase;

	@Column(name = "topSpeedMph")
	Integer topSpeedMph;

	@Column(name = "costUsd")
	Integer costUsd;

	@Column(name = "price")
	Integer price;
}
