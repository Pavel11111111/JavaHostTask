package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import com.hostTask.pavelCherepanov.persistence.model.response.GetTSPopularParamResponseBody;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Маппер для мапинга данных из <ArrayList<String>
 * в ArrayList<GetTSPopularParamResponseBody>
 * (так как это маппер, реализует интерфес Mapper)
 */
@Component
public class ArrayListParamTSToArrayListResponseBodyMapper implements Mapper<ArrayList<String>, ArrayList<GetTSPopularParamResponseBody>> {

    /**
     * Метод создающий из ArrayList<String> новый объект типа ArrayList<GetTSPopularParamResponseBody>
     *
     * @param countVehicleType списочный массив(например с типом ТС) из которого на необходимо сделать тело ответа
     * @return списочный массив с объектами типа GetTSPopularParamResponseBody(в котором есть 2 поля
     * с наименованием(например типа ТС) и его количеством)
     */
    @Override
    public ArrayList<GetTSPopularParamResponseBody> map(ArrayList<String> countVehicleType) {
        //создаём списочный массив с GetTSPopularParamResponseBody, и сам объект
        ArrayList<GetTSPopularParamResponseBody> tsTypeResponseBody = new ArrayList<>();
        GetTSPopularParamResponseBody typeResponseBody = new GetTSPopularParamResponseBody();
        //создаём HashMap, чтобы в нём при переборе массива параметр String соответсвовал определённому
        //полю(например типу ТС) и параметр Integer его количеству в базе данных
        Map<String, Integer> countMap = new HashMap<>();
        //с помощью цикла for each последовательно перебираем все элементы списочного массива
        for (String item : countVehicleType) {
            //если HashMap уже содержит такой ключ
            if (countMap.containsKey(item)) {
                //то мы изменяем значение у этого кдюча добавляя к нему единицу(+1 встретившаяся запись)
                countMap.put(item, countMap.get(item) + 1);
            }
            //если такого ключа в Hashmap не обнаружено
            else
                //то добавляем ключ и ставим к нему значение 1(первая запись с таким значением была найдена)
                countMap.put(item, 1);
        }
        //с помощью цикл перебираем получившийся HashMap, получая ключ, и значение каждой записи
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            //устанавливаем полю name в GetTSPopularParamResponseBody соответсвующее ему значение из HashMap(в данном случае ключ)
            typeResponseBody.setName(entry.getKey());
            //устанавливаем полю count в GetTSPopularParamResponseBody соответсвующее ему значение из HashMap(в данном случае значение)
            typeResponseBody.setCount(entry.getValue());
            //добавляем заполненный экземляр в списочный массив
            tsTypeResponseBody.add(typeResponseBody);
            //создаём новую ссылку на объект, в случае если цикл еще не закончен мы будем изменять поля у уже другого объекта
            typeResponseBody = new GetTSPopularParamResponseBody();
        }
        return tsTypeResponseBody;
    }

    @Override
    public void mapFromInputInOutput(ArrayList<String> countVehicleType, ArrayList<GetTSPopularParamResponseBody> popularParamResponseBodies) {
    }
}
