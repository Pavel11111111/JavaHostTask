package com.hostTask.pavelCherepanov.applicationRunner;

import com.hostTask.pavelCherepanov.persistence.dao.StatusRepository;
import com.hostTask.pavelCherepanov.persistence.dao.VehicleTypeRepository;
import com.hostTask.pavelCherepanov.persistence.model.entity.Status;
import com.hostTask.pavelCherepanov.persistence.model.entity.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner приложения.
 * Отвечает за действия которые будут происходить при запуске сервиса.
 */
@Component
public class Loader implements ApplicationRunner {

    //создаём переменную-logger для этого класса.
    private static final Logger log = LoggerFactory.getLogger(Loader.class);

    //создаём переменные для необходимых нам репозиториев.
    private VehicleTypeRepository vehicleTypeRepository;
    private StatusRepository statusRepository;

    //создаём конструктор класса, и указываем аннотацию @Autowired
    @Autowired
    public Loader(VehicleTypeRepository vehicleTypeRepository, StatusRepository statusRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.statusRepository = statusRepository;
    }

    /**
     * метод класса ApplicationRunner, отвечающий за действия которые будут происходить при запуске сервиса.
     *
     * @param args аргументы командной строки, передаваемые при запуске приложения
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //если у нас нет ни одного типа ТС в бд
        if (vehicleTypeRepository.count() == 0) {
            //добавляем необходимые типы и сохраняем их в бд
            VehicleType vehicleType = new VehicleType();
            vehicleType.setVehicleType("supercar");
            vehicleTypeRepository.save(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setVehicleType("jet");
            vehicleTypeRepository.save(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setVehicleType("ship");
            vehicleTypeRepository.save(vehicleType);
            vehicleType = new VehicleType();
            vehicleType.setVehicleType("helicopter");
            vehicleTypeRepository.save(vehicleType);
        }
        //если у нас нет ни одного статуса ТС в бд
        if (statusRepository.count() == 0) {
            //добавляем необходимые статусы и сохраняем их в бд
            Status status = new Status();
            status.setStatus("in stock");
            statusRepository.save(status);
            status = new Status();
            status.setStatus("sold");
            statusRepository.save(status);
            status = new Status();
            status.setStatus("reserved");
            statusRepository.save(status);
        }
    }
}
