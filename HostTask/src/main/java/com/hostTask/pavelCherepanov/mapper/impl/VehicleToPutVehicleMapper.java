package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.dao.*;
import com.hostTask.pavelCherepanov.persistence.model.entity.*;
import com.hostTask.pavelCherepanov.persistence.model.request.PutVehicleRequestBody;
import org.springframework.stereotype.Component;

/**
 * Маппер для мапинга данных из объекта типа PutVehicleRequestBody
 * в оьъект типа Vehicle(из старого объекта в обновлённы)
 * (так как это маппер, реализует интерфес Mapper)
 */
@Component
public class VehicleToPutVehicleMapper implements Mapper<PutVehicleRequestBody, Vehicle> {
    /**
     * переменные с необходимыми для работы в этом маппере бинами.
     */
    private final VehicleTypeRepository vehicleTypeRepository;
    private final EngineRepository engineRepository;
    private final MarqueModelRepository marqueModelRepository;
    private final MarqueRepository marqueRepository;
    private final ModelRepository modelRepository;
    private final StatusRepository statusRepository;

    /**
     * Конструктор класса. С его помощью мы инициализируем необходимые нам для работы в этой классе бины
     */
    public VehicleToPutVehicleMapper(
            VehicleTypeRepository vehicleTypeRepository,
            EngineRepository engineRepository,
            MarqueModelRepository marqueModelRepository,
            MarqueRepository marqueRepository,
            ModelRepository modelRepository,
            StatusRepository statusRepository
    ) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.engineRepository = engineRepository;
        this.marqueModelRepository = marqueModelRepository;
        this.marqueRepository = marqueRepository;
        this.modelRepository = modelRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Vehicle map(PutVehicleRequestBody putVehicleRequestBody) {
        return null;
    }

    /**
     * Метод обновляющий запись в бд
     *
     * @param vehiclePut Объект из которого мы будем брать данные для обновления записи
     * @return обновленный объект Vehicle
     */
    @Override
    public void mapFromInputInOutput(PutVehicleRequestBody vehiclePut, Vehicle vehicle) {
        //создаём необходимые нам переменные, в которые мы будем помещать id найденных в базе данных записей
        //или null, если таких записей нет
        Integer findId = null;
        Integer findId2 = null;
        Integer findId3;
        //если в теле запроса есть vehicletype,то
        if (vehiclePut.getVehicleType() != null) {
            //создаём новый экземпляр класса VehicleType
            VehicleType vehicleType = new VehicleType();
            //если на данный момент в записи vehicle не стоит такой же тип, что и пришёл нам в теле запроса
            if (!(vehicle.getVehicletypej().getVehicleType().equals(vehiclePut.getVehicleType()))) {
                //то ищем в БД запись с таким типом ТС
                findId = vehicleTypeRepository.findByVehicleType(vehiclePut.getVehicleType());
                //если такая запись была найдена
                if (findId != null) {
                    //то находим её с помощью метода репозитория и помещаем в переменную
                    vehicleType = vehicleTypeRepository.getOne(findId);
                }
                //если же записи с таким типом ТС в БД не обнаружено
                else {
                    //то заполняем поля vehicleType данными, которые пришли в теле запроса(создаём новый vehicleType)
                    vehicleType.setVehicleType(vehiclePut.getVehicleType());
                }
                //сохраняем получившийся vehicleType в vehicle для последующего сохранения данных в бд
                vehicle.setVehicletypej(vehicleType);
            }
        }

        //если в теле запроса есть марка и модель
        if (vehiclePut.getMarque() != null && vehiclePut.getModel() != null) {
            //то создаём новый экземпляр класса Mode
            Marque marque = new Marque();
            //ищем в базе данных марку с таким же именем как и в теле запроса
            findId = marqueRepository.findByMarque(vehiclePut.getMarque());
            //если такая марка была найдена
            if (findId != null) {
                //то ищем её в бд по id и помещаем в переменную
                marque = marqueRepository.getOne(findId);
            }
            //если же такой марки не было найдено
            else {
                //то заполняем поля экземпляра класса Marque из данных полученных нами в теле запроса
                marque.setMarque(vehiclePut.getMarque());
            }
            //создаём новый экземпляр класса Model
            Model model = new Model();
            //ищем в базе данных модель с таким же именем как и в теле запроса
            findId2 = modelRepository.findByModel(vehiclePut.getModel());
            //если такая модель была найдена
            if (findId2 != null) {
                //то ищем её в бд по id и помещаем в переменную
                model = modelRepository.getOne(findId2);
            }
            //если же такой модели не было найдено
            else {
                //то заполняем поля экземпляра класса Model из данных полученных нами в теле запроса
                model.setModel(vehiclePut.getModel());
            }

            //создаём новый экземпляр класса MarqueModel
            MarqueModel marqueModel = new MarqueModel();
            //если и марка и модель с таким параметрам как в теле запроса уже были найдены в бд
            if (findId != null && findId2 != null) {
                //то ищем запись по id в таблица MarqueModel
                findId3 = marqueModelRepository.findByMarqueIDModelID(findId, findId2);
                //если такая запись была найдена
                if (findId3 != null) {
                    //то находим её по id и сохраняем в переменную
                    marqueModel = marqueModelRepository.getOne(findId3);
                } else {
                    //то заполняем поля экземпляра класса MarqueModel данными
                    marqueModel.setModelj(model);
                    marqueModel.setMarquej(marque);
                }
            }
            //если findId равны null, то есть либо марка, либо модель не была найдена и мы создавали новую
            //то логично предположить что в таблице MarqueModel мы также ничего не найдём, поэтому просто
            //заполняем поля экземпляра класса MarqueModel созданными нами маркой и моделью
            else {
                marqueModel.setModelj(model);
                marqueModel.setMarquej(marque);
            }
            //сохраняем в vehicle
            vehicle.setMarqueModelj(marqueModel);
        }

        //если в теле запроса есть поле engine
        if (vehiclePut.getEngine() != null) {
            //создаём новый экземпляр класса Engine
            Engine engine = new Engine();
            //если на данный момент в записи Vehicle находится связь на таблицу Engine с другими значениями
            if (!(vehicle.getEnginej().getEngine().equals(vehiclePut.getEngine())) || vehicle.getEnginej().getEnginePowerBhp() != vehiclePut.getEnginePowerBhp()) {
                //то производим поиск в базе данных записи в таблице Engine с значениями полей, такими же как в теле запроса
                findId = engineRepository.findByEngineAndEnginePowerBhp(vehiclePut.getEngine(), vehiclePut.getEnginePowerBhp());
                //если нашли
                if (findId != null) {
                    //то по id получаем запись из бд и сохраняем её в переменную
                    engine = engineRepository.getOne(findId);
                }
                //есои же такая запись не была найдена
                else {
                    //то заполняем поля экземпляра класса Engine значениями, полученными в теле запроса
                    engine.setEngine(vehiclePut.getEngine());
                    engine.setEnginePowerBhp(vehiclePut.getEnginePowerBhp());
                }
                //помещаем получившийся экзепляр класса Engine в vehicle для последующего сохранения
                vehicle.setEnginej(engine);
            }
        }
        //если в теле запроса есть поле status
        if (vehiclePut.getStatus() != null) {
            //то создаём новый экземпляр класса Status
            Status status = new Status();
            //если на данный момент в записи Vehicle находится связь на таблицу Status с другими значениями
            if (!(vehicle.getStatusj().getStatus().equals(vehiclePut.getStatus()))) {
                //то ищем таблицу Status с таким же значением статус как и в теле запроса
                findId = statusRepository.findByStatus(vehiclePut.getStatus());
                //если нашли
                if (findId != null) {
                    //то помещаем в переменную найденную по id запись
                    status = statusRepository.getOne(findId);
                } else {
                    //если нет, то заполняем поля данными полученными в теле запроса
                    status.setStatus(vehiclePut.getStatus());
                }
                //помещаем получившийся экзепляр класса Status в vehicle для последующего сохранения
                vehicle.setStatusj(status);
            }
        }
        //если в теле запроса найдены следующие поля, то мы помещаем в vehicle их значения

        if (vehiclePut.getTopSpeedMph() != 0) {
            vehicle.setTopSpeedMph(vehiclePut.getTopSpeedMph());
        }

        if (vehiclePut.getCostUsd() != 0) {
            vehicle.setCostUsd(vehiclePut.getCostUsd());
        }

        if (vehiclePut.getPrice() != 0) {
            vehicle.setPrice(vehiclePut.getPrice());
        }
    }
}
