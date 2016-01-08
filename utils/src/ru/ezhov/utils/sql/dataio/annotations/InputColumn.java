package ru.ezhov.utils.sql.dataio.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, которой помечаются поля для внесения в базу данных
 * <p>
 * @author ezhov_da
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputColumn {

    /**
     * номер параметра для внесения
     * <p>
     * @return порядковый номер параметра для внесения
     */
    int value();
}
