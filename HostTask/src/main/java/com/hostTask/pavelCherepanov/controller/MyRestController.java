package com.hostTask.pavelCherepanov.controller;

import com.hostTask.pavelCherepanov.mapper.impl.*;
import com.hostTask.pavelCherepanov.persistence.dao.VehicleRepository;
import com.hostTask.pavelCherepanov.persistence.model.entity.Vehicle;
import com.hostTask.pavelCherepanov.persistence.model.request.AddVehicleRequestBody;
import com.hostTask.pavelCherepanov.persistence.model.request.PutVehicleRequestBody;
import com.hostTask.pavelCherepanov.persistence.model.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс-контроллер приложения.
 * Отвечает за обработку запросов по адресу service-address:port/vehicle
 */
@RestController
@RequestMapping("/vehicle")
public class MyRestController {

    /**
     * Логгер. Необходим для правильного вывода сообщений из кода.
     */
    public static final Logger logger = LoggerFactory.getLogger(MyRestController.class);
    /**
     * DateTimeFormatter, необоходим для преобразования даты и времени, согласно указанному паттерну
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * переменные с необходимыми нам бинами
     */
    private final AddVehicleRequestBodyToVehicleMapper vehicleRequestBodyToVehicleMapper;
    private final VehicleRepository vehicleRepository;
    private final AddAndPutVehicleToVehicleResponseBodyMapper vehicleToVehicleResponseBodyMapper;
    private final VehicleToPutVehicleMapper vehicleToPutVehicleMapper;
    private final VehicleToSearchGuidResponseMapper vehicleToSearchGuidResponseMapper;
    private final ArrayListParamTSToArrayListResponseBodyMapper arrayListParamTSToArrayListResponseBodyMapper;
    private final ArrayListParamTSToStringMapper arrayListParamTSToStringMapper;
    private final EntityManager entityManager;

    /**
     * Конструктор. Вызывается spring контекстом, когда контроллер создаётся.
     * Аннотация @Autowired необходима для того, чтобы spring контекст вставил сюда
     * подходящий по типу класс-bean. bean(бины) могут быть разных типов.
     * Их объединяет то, что все они находятся в контексте, как фасолины в банке.
     * spring сам решает, какой и когда вызывать в той или иной ситуации.
     * Класс, помеченный аннотацией @RestController также является бином и
     * находится в контексте приложения.
     * Но так как если в классе один конструктор, то он по умолчанию является @autowired,
     * поэтому явно указывать эту аннотацию здесь не нужно.
     */
    public MyRestController(AddVehicleRequestBodyToVehicleMapper vehicleRequestBodyToVehicleMapper,
                            AddAndPutVehicleToVehicleResponseBodyMapper vehicleToVehicleResponseBodyMapper,
                            VehicleToPutVehicleMapper vehicleToPutVehicleMapper,
                            VehicleToSearchGuidResponseMapper vehicleToSearchGuidResponseMapper,
                            ArrayListParamTSToArrayListResponseBodyMapper arrayListParamTSToArrayListResponseBodyMapper,
                            ArrayListParamTSToStringMapper arrayListParamTSToStringMapper,
                            VehicleRepository vehicleRepository,
                            EntityManager entityManager) {
        this.entityManager = entityManager;
        this.arrayListParamTSToStringMapper = arrayListParamTSToStringMapper;
        this.arrayListParamTSToArrayListResponseBodyMapper = arrayListParamTSToArrayListResponseBodyMapper;
        this.vehicleRequestBodyToVehicleMapper = vehicleRequestBodyToVehicleMapper;
        this.vehicleToVehicleResponseBodyMapper = vehicleToVehicleResponseBodyMapper;
        this.vehicleToPutVehicleMapper = vehicleToPutVehicleMapper;
        this.vehicleToSearchGuidResponseMapper = vehicleToSearchGuidResponseMapper;
        this.vehicleRepository = vehicleRepository;
        //сообщаем о успешном создании контроллера
        logger.info("MyRestController was created!");
    }

    /**
     * Метод, отвечающий за обработку post запросов по адресу
     * service-address:port/vehicle Метод принимает объект типа AddVehicleRequestBody
     * в теле запроса замаршаленный в json объект
     *
     * @param vehicleRequest принимаемый объект. spring демаршалит его за нас.
     * @param request        параметры запроса.
     * @return созданное и заполненное данными тело ответа, с информацией о добавленной записи
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddAndPutVehicleResponseBody> postVehicle(
            @RequestBody AddVehicleRequestBody vehicleRequest,
            HttpServletRequest request) {
        //создаём объект типа Vehicle, и, с помощью созданного нами маппера, заполняем его данными из тела запроса.
        Vehicle vehicle = vehicleRequestBodyToVehicleMapper.map(vehicleRequest);
        //добавляем в наш Vehicle данные, которых нет в теле запроса.
        vehicle.setGuid(UUID.randomUUID().toString());
        LocalDateTime date = LocalDateTime.now();
        String formattedDateTime = date.format(formatter);
        date = LocalDateTime.parse(formattedDateTime, formatter);
        vehicle.setDateInsert(date);
        vehicle.setDatePurchase(date);
        //сохраняем нашу запись через репозиторий в бд.
        vehicleRepository.save(vehicle);
        //логируем информацию что была создана новая запись и указываем guid новой записи
        logger.info("Vehicle with guid={} was created!", vehicle.getGuid());
        //возвращаем в теле ответа специально созданный нами responsebody, c информацией о новой записи, который мы заполняем данными с помощью маппера
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicleToVehicleResponseBodyMapper.map(vehicle));
    }

    /**
     * Метод, отвечающий за обработку put запросов по адресу
     * service-address:port/vehicle Метод принимает объект типа AddVehicleRequestBody.
     *
     * @param vehiclePut принимаемый объект. spring демаршалит его за нас.
     * @param request    параметры запроса.
     * @return созданное и заполненное данными тело ответа, с информацией о обновлённой записи
     */

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<AddAndPutVehicleResponseBody> putVehicle(
            @RequestBody PutVehicleRequestBody vehiclePut,
            HttpServletRequest request) {
        //по Guid находим в БД нужную нам запись
        Vehicle vehicle = vehicleRepository.getOne(vehiclePut.getGuid());
        //с помощью созданного нами маппера изменяем данные в найденной записи
        vehicleToPutVehicleMapper.mapFromInputInOutput(vehiclePut, vehicle);
        //обновлеяем DatePurchase
        LocalDateTime date = LocalDateTime.now();
        String formattedDateTime = date.format(formatter);
        date = LocalDateTime.parse(formattedDateTime, formatter);
        vehicle.setDatePurchase(date);
        //с помощью метода репозитория обновляем запись в базе данных
        vehicle = vehicleRepository.save(vehicle);
        //логируем информацию что запись была обновлена и указываем guid обновлённой записи
        logger.info("Vehicle with guid={} was purchase!", vehicle.getGuid());
        //возвращаем в теле ответа специально созданный нами responsebody, c информацией о обновлённой записи, который мы заполняем данными с помощью маппера
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicleToVehicleResponseBodyMapper.map(vehicle));
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * service-address:port/vehicle/search?{vehicleType}&{marque}&{model}&{engine}&{status}
     * Метод принимает параметры поиска
     * и возвращает ТС из базы данных в виде json объекта.
     *
     * @param vehicleType,marque,model,engine,status - даннные, по которым будем искать ТС(status является необязательным)
     * @param request                                параметры запроса.
     * @return созданное и заполненное данными тело ответа, с найденными записями
     */

    @GetMapping("/search")
    public ResponseEntity<List<GetVehicleByParamResponseBody>> getVehicleByParam(
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("marque") String marque,
            @RequestParam("model") String model,
            @RequestParam("engine") String engine,
            @RequestParam(value = "status", required = false) String status,
            HttpServletRequest request) {
        //создаём sql запрос для поиска в базе данных записей, которые соответсвуют указанным параметрам
        String sql = "SELECT vehicle.guid, vehicle_type.vehicle_type, marque.marque, model.model, " +
                "engine.engine, engine.engine_power_bhp, vehicle.top_speed_mph, vehicle.cost_usd, vehicle.price, " +
                "vehicle.date_insert, status.status  FROM vehicle " +
                "JOIN vehicle_type ON vehicle.vehicle_typeid = vehicle_type.vehicle_typeid " +
                "JOIN marque_model ON vehicle.marque_modelid = marque_model.marque_modelid " +
                "JOIN marque ON marque_model.marqueid = marque.marqueid " +
                "JOIN model ON marque_model.modelid = model.modelid " +
                "JOIN engine ON vehicle.engineid = engine.engineid " +
                "JOIN status ON vehicle.statusid = status.statusid " +
                "WHERE vehicle_type.vehicle_type = '" + vehicleType + "' " +
                "AND marque.marque = '" + marque + "' " +
                "AND model.model = '" + model + "' " +
                "AND engine.engine = '" + engine + "' ";
        //т.к. параметр status является необязательным, проверяем его наличие, и если он присутствует
        if (status != null) {
            //то подключаем его к поиску
            sql += "AND status.status = '" + status + "'";
        }
        //выполняем запрос к базе данных, и получаем все записи, которые соответсвуют введённым параментрам и возращаем их список с помощью метода getResultList
        List<GetVehicleByParamResponseBody> getVehicleByParamResponseBodies = entityManager.createNativeQuery(sql).getResultList();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(getVehicleByParamResponseBodies);
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * service-address:port/vehicle/{guid} Метод принимает guid ТС
     * и возвращает ТС из базы данных в виде json объекта.
     *
     * @param guid    - первичный ключ в бд объекта, по которому будем искать ТС.
     * @param request параметры запроса.
     * @return созданное и заполненное данными тело ответа, с найденной записью
     */
    @GetMapping("/{guid}")
    public ResponseEntity<SearchResponseBody> getVehicleByGuid(
            @PathVariable String guid,
            HttpServletRequest request) {
//ищем запись по guid и передаём её в маппер, который создаёт responsebody
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(vehicleToSearchGuidResponseMapper.map(vehicleRepository.getOne(guid)));
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * http://localhost:8080/vehicle/types Метод
     * подсчитывает и возвращает типы ТС, которые есть в сущности Vehicle
     *
     * @param request параметры запроса.
     * @return созданное и заполненное данными тело ответа, с найденными записями
     */
    @GetMapping("/types")
    public ResponseEntity<ArrayList<GetTSPopularParamResponseBody>> getTSByType(
            HttpServletRequest request) {
        //с помощью sql запроса в репозитории получаем все типы ТС, которые есть в сущности Vehicle
        ArrayList<String> countVehicleType = vehicleRepository.queryTypesTS();
        //подсчитываем количество найденных типов, мапим полученный ArrayList в Responsebody и возвращаем его
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrayListParamTSToArrayListResponseBodyMapper.map(countVehicleType));
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * http://localhost:8080/vehicle/marque. Метод
     * подсчитывает и возвращает марки ТС, которые есть в сущности Vehicle
     *
     * @param request параметры запроса.
     * @return созданное и заполненное данными тело ответа, с найденными записями
     */

    @GetMapping("/marque")
    public ResponseEntity<ArrayList<GetTSPopularParamResponseBody>> getTSByMarque(
            HttpServletRequest request) {
        //с помощью sql запроса в репозитории получаем все марки ТС, которые есть в сущности Vehicle
        ArrayList<String> countVehicleType = vehicleRepository.queryMarqueTS();
        //подсчитываем количество найденных марок, мапим полученный ArrayList в Responsebody и возвращаем его
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrayListParamTSToArrayListResponseBodyMapper.map(countVehicleType));
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * http://localhost:8080/vehicle/status. Метод
     * подсчитывает и возвращает статусы у ТС, которые есть в сущности Vehicle
     *
     * @param request параметры запроса.
     * @return созданное и заполненное данными тело ответа, с найденными записями
     */
    @GetMapping("/status")
    public ResponseEntity<ArrayList<GetTSPopularParamResponseBody>> getTSByStatus(
            HttpServletRequest request) {
        //с помощью sql запроса в репозитории получаем все статусы ТС, которые есть в сущности Vehicle
        ArrayList<String> countVehicleType = vehicleRepository.queryStatusTS();
        //подсчитываем количество найденных статусов, мапим полученный ArrayList в Responsebody и возвращаем его
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(arrayListParamTSToArrayListResponseBodyMapper.map(countVehicleType));
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * http://localhost:8080/vehicle/populartype. Метод
     * подсчитывает и возвращает самые популярные марки, типы и статусы транспортных средств
     *
     * @param request параметры запроса.
     * @return созданное и заполненное данными тело ответа, в котором находятся названия и количество
     * самых популярных марок, типок и статусов транспортных средств
     */
    @GetMapping("/populartype")
    public ResponseEntity<GetTSMostPopularParamResponseBody> getTSByMostPopular(
            HttpServletRequest request) {
        //создаём тело ответа
        GetTSMostPopularParamResponseBody tsMostPopularParamResponseBody = new GetTSMostPopularParamResponseBody();
        //с помощью sql запроса получаем все типы ТС
        ArrayList<String> countVehicleType = vehicleRepository.queryTypesTS();
        //в маппере находим самый популярный тип, и помещаем его в tsMostPopularParamResponseBody
        tsMostPopularParamResponseBody.setType(arrayListParamTSToStringMapper.map(countVehicleType));
        //с помощью sql запроса получаем все марки ТС
        countVehicleType = vehicleRepository.queryMarqueTS();
        //в маппере находим самый популярный тип, и помещаем его в tsMostPopularParamResponseBody
        tsMostPopularParamResponseBody.setMarque(arrayListParamTSToStringMapper.map(countVehicleType));
        //с помощью sql запроса получаем все типы ТС
        countVehicleType = vehicleRepository.queryStatusTS();
        //в маппере находим самую популярную марку, и помещаем её в tsMostPopularParamResponseBody
        tsMostPopularParamResponseBody.setStatus(arrayListParamTSToStringMapper.map(countVehicleType));
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tsMostPopularParamResponseBody);
    }

    /**
     * Метод, отвечающий за обработку get запросов по адресу
     * http://localhost:8080/vehicle/random. Метод
     * выбираtn случайную запись ТС из БД и заменяет в ответе значения всех строковых параметров на строки с символами в обратном порядке
     *
     * @param request параметры запроса.
     * @return созданное и заполненное данными тело ответа, в котором находится случайная запись,
     * с изменёнными строками
     */
    @GetMapping("/random")
    public ResponseEntity<Map<String, Object>> getRandom(
            HttpServletRequest request) {
        //с помощью sql запроса получаем поля случайной записи
        Map<String, Object> resultSql = vehicleRepository.queryRandomTS();
        //создаём новый hashmap для изменённой записи
        Map<String, Object> resultChange = new HashMap<>();
        //цикл последовательно перебирающий все значения из resultSql
        for (Map.Entry<String, Object> entry : resultSql.entrySet()) {
            //если найдена строка
            if (entry.getValue().getClass().getName() == "java.lang.String") {
                //выставляем символы в ней в обратном порядке
                String reversedString = new StringBuilder(entry.getValue().toString()).reverse().toString();
                //помещаем в resultChange
                resultChange.put(entry.getKey(), reversedString);
                //если же строки не найдено
            } else {
                //то просто помещаем в resultChange
                resultChange.put(entry.getKey(), entry.getValue());
            }
        }
        //возвращаем получивщийся HashMap
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultChange);
    }
}