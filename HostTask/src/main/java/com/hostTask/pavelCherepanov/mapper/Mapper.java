package com.hostTask.pavelCherepanov.mapper;

/**
 * Маппер(Объект, который управляет сообщением между независимыми друг от друга объектами.)
 *
 * @param <typeInput>  входной тип данных
 * @param <typeOutput> выходной тип данных
 */

public interface Mapper<typeInput, typeOutput> {
    /**
     * Маппинг в новый объект.
     *
     * @param input входной объект
     * @return выходной объект
     */
    typeOutput map(typeInput input);

    /**
     * Мапинг из одного готового объекта в другой готовый объект.
     *
     * @param input  входной объект
     * @param output выходной объект
     */
    void mapFromInputInOutput(typeInput input, typeOutput output);
}
