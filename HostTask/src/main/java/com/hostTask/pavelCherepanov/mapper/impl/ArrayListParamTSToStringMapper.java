package com.hostTask.pavelCherepanov.mapper.impl;

import com.hostTask.pavelCherepanov.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Маппер для мапинга данных из <ArrayList<String>
 * в String
 * (так как это маппер, реализует интерфес Mapper)
 */

@Component
public class ArrayListParamTSToStringMapper implements Mapper<ArrayList<String>, String> {
    /**
     * Метод создающий из ArrayList<String> новый объект типа String
     * Метод последовательно перебирает все значеня из ArrayList<String>(который содержит в себе
     * все встречающиеся в базе данных записи(напимер тип тс)
     * и возвращает то значение, которое встречается в этот списочном массиве чаще всего.
     *
     * @param countVehicleType списочный массив(например с типами ТС) в котором необходимо найти самую часто популярную запись
     * @return самая популярная запись
     */
    @Override
    public String map(ArrayList<String> countVehicleType) {
        //инициализируем необходимые нам для работы переменные
        Integer value = 0;
        String responseValue = null;
        Map<String, Integer> countMap = new HashMap<>();
        //последовательно перебираем с помощью цикла полученный нами списочный массив
        for (String item : countVehicleType) {
            //если HashMap уже содержит такой ключ
            if (countMap.containsKey(item))
                //то мы изменяем значение у этого кдюча добавляя к нему единицу(+1 встретившаяся запись)
                countMap.put(item, countMap.get(item) + 1);
                //если такого ключа в Hashmap не обнаружено
            else
                //то добавляем ключ и ставим к нему значение 1(первая запись с таким значением была найдена)
                countMap.put(item, 1);
        }
        //последовательно перебираем с помощью цикла полученный нами HashMap и получаем пары ключ-значение
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            //если найдена запись с большим количеством повторений, чем есть на текущий момент цикла
            if (entry.getValue() > value) {
                //то мы помещаем в переменную название этой записи
                responseValue = entry.getKey();
                // и количество её повторений(чтобы на последующих шагах цикла сравнивать уже её количество повторений с другими записями)
                value = entry.getValue();
            }
        }
        return responseValue;
    }

    @Override
    public void mapFromInputInOutput(ArrayList<String> countVehicleType, String popularParamResponseBodies) {
    }
}
