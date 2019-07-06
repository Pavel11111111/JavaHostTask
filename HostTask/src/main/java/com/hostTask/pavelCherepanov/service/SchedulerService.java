package com.hostTask.pavelCherepanov.service;

import com.hostTask.pavelCherepanov.persistence.dao.VehicleRepository;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * Класс для выполнения задач по расписанию
 */
@Service
public class SchedulerService {
    /**
     * Создаём логгер этого класса и бин репозитория таблицы Vehicle
     */
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final VehicleRepository vehicleRepository;

    /**
     * Конструктор класса, для заполнения бинов
     */
    public SchedulerService(
            VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        //логируем информацию, о том что сервис был создан
        log.info("SchedulerService was created!");
    }

    //аннотацией sheduled указываем что этот метод будет выполняться по расписанию
    //Параметр initialDelayString задаёт время в миллисекундах, которое пройдёт перед первым запуском данной задачи
    //Параметр fixedDelayString задаёт время в миллисекундах между запусками данной задачи.
    //${scheduler.delay} - означает что это время указано в application.properties
    @Scheduled(initialDelayString = "${scheduler.delay}", fixedDelayString = "${scheduler.delay}")
    //указал аннотацию для устанения ошибки, из-за которой отсутсвовал доступ к репозиторию из этого метода
    @Transactional
    /**
     * Метод выполняющийся по расписанию
     */
    public void doWork() throws InterruptedException {
        //логируем информацию что процесс запущен
        log.info("Start process");
        //получаем случайное GUID случайного ТС из БД
        Map<String, Object> result = vehicleRepository.queryRandomTSSheduler();
        //с помощью метода getOne находим запись с этими guid в базе данных
        Vehicle vehicle = vehicleRepository.getOne(result.get("guid").toString());
        //устанавливаем этой записи price в 0
        vehicle.setPrice(0);
        //сохраняем запись
        vehicleRepository.save(vehicle);
        //логируем информацию что процесс завершён
        log.info("End process");
    }
}
