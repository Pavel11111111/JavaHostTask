package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.dao.*;
import com.hostTask.pavelCherepanov.persistence.model.entity.*;
import com.hostTask.pavelCherepanov.persistence.model.request.AddVehicleRequestBody;
import org.springframework.stereotype.Component;

/**
 * Маппер для мапинга данных из объекта типа AddVehicleRequestBody
 * в оьъект типа Vehicle
 * (так как это маппер, реализует интерфес Mapper)
 */
@Component
public class AddVehicleRequestBodyToVehicleMapper implements Mapper<AddVehicleRequestBody, Vehicle> {
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
    public AddVehicleRequestBodyToVehicleMapper(
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

    /**
     * Метод создающий из RequestBody новый объект типа Vehicle
     *
     * @param vehicleRequestBody Объект типа AddVehicleRequestBody(тело запроса)
     * @return созданный и заполненный данными Vehicle
     */
    @Override
    public Vehicle map(AddVehicleRequestBody vehicleRequestBody) {
        //создаём новый объект Vehicle
        Vehicle vehicle = new Vehicle();
        //ищем в таблице VehicleType запись по её полю vehicletype
        Integer findId = vehicleTypeRepository.findByVehicleType((vehicleRequestBody.getVehicleType()));
        //создаём новый объект VehicleType
        VehicleType vehicleType = new VehicleType();
        //Если такая запись была найдена(такой vehicletype уже существует, например supercar)
        if (findId != null) {
            //то с помощью репозитория мы её находим, и помещаем в нашу переменную
            vehicleType = vehicleTypeRepository.getOne(findId);
        }
        //если такой записи не было найдено
        else {
            //то заполняем объект VehicleType значением из vehicleRequestBody
            vehicleType.setVehicleType(vehicleRequestBody.getVehicleType());
        }
        //сохраняем наш vehicleType в vehicle
        vehicle.setVehicletypej(vehicleType);

        //создаём новый объект Engine
        Engine engine = new Engine();
        //ищем в таблице Engine запись по её полям Engine и EnginePowerBhp
        findId = engineRepository.findByEngineAndEnginePowerBhp(vehicleRequestBody.getEngine(), vehicleRequestBody.getEnginePowerBhp());
        //Если такая запись была найдена
        if (findId != null) {
            //то с помощью репозитория мы её находим, и помещаем в нашу переменную
            engine = engineRepository.getOne(findId);
        }
        //если такой записи не было найдено
        else {
            //то заполняем объект Engine значением из vehicleRequestBody
            engine.setEngine(vehicleRequestBody.getEngine());
            engine.setEnginePowerBhp(vehicleRequestBody.getEnginePowerBhp());
        }
        //сохраняем наш engine в vehicle
        vehicle.setEnginej(engine);

        //ищем в таблице marque запись по её параметрам, находим помещаем в переменную, если нет, создаём новую
        Marque marque = new Marque();
        findId = marqueRepository.findByMarque(vehicleRequestBody.getMarque());
        if (findId != null) {
            marque = marqueRepository.getOne(findId);
        } else {
            marque.setMarque(vehicleRequestBody.getMarque());
        }

        Model model = new Model();
        //создаём переменную findId2 чтобы потом искать в таблице marqueModel запись
        //по найденным нами id марки и модели(либо же создавать новую, если такой записи не было найдено
        Integer findId2 = modelRepository.findByModel(vehicleRequestBody.getModel());
        //ищем в таблице model запись по её параметрам, находим помещаем в переменную, если нет, создаём новую
        if (findId2 != null) {
            model = modelRepository.getOne(findId);
        } else {
            model.setModel(vehicleRequestBody.getModel());
        }

        MarqueModel marqueModel = new MarqueModel();
        //если при поиске в таблицах были найдены и марка и модель с указанными в vehicleRequestBody значениями
        if (findId != null && findId2 != null) {
            //то мы ищем запись в таблице marquemodel по id марки и id моделт
            Integer findId3 = marqueModelRepository.findByMarqueIDModelID(findId, findId2);
            //если такая запись была найдена
            if (findId3 != null) {
                //то находим её с помощью репозитория и помещаем в переменную
                marqueModel = marqueModelRepository.getOne(findId3);
            }
            //если такой записи найдено на было, то создаём новую
            else {
                marqueModel.setModelj(model);
                marqueModel.setMarquej(marque);
            }
        }
        //если при поиске не было найдено марки, или модели, то нам очевидно наобходимо создавать новую запись, что мы и делаем
        else {
            marqueModel.setModelj(model);
            marqueModel.setMarquej(marque);
        }
        //сохраняем marqueModel в vehicle
        vehicle.setMarqueModelj(marqueModel);

        //сохраняем в переменную получанный нами в vehicleRequestBody статус ТС.
        String statusR = vehicleRequestBody.getStatus();
        //если статус не был передан
        if (statusR == null) {
            //то находим в репозитории стандартный статус in stock
            findId = statusRepository.findByStatus("in stock");
            //и устанавливаем его в vehicle
            vehicle.setStatusj(statusRepository.getOne(findId));
        }
        //если поле со статусом есть в vehicleRequestBody
        else {
            //ищем с помощью репозитория в базе данных запись с таким статусом
            findId = statusRepository.findByStatus(vehicleRequestBody.getStatus());
            Status status = new Status();
            //если такой статус был найден
            if (findId != null) {
                //то мы находим его и помещаем в переменную
                status = statusRepository.getOne(findId);
            }
            //если такой статус отсутсвует
            else {
                //то создаём новый
                status.setStatus(vehicleRequestBody.getStatus());
            }
            //сохраняем status в vehicle
            vehicle.setStatusj(status);
        }
        //перемещаем остальные поля из vehicleRequestBody в vehicle
        vehicle.setTopSpeedMph(vehicleRequestBody.getTopSpeedMph());
        vehicle.setCostUsd(vehicleRequestBody.getCostUsd());
        vehicle.setPrice(vehicleRequestBody.getPrice());

        return vehicle;
    }

    @Override
    public void mapFromInputInOutput(AddVehicleRequestBody vehicleRequestBody, Vehicle vehicle) {

    }
}
