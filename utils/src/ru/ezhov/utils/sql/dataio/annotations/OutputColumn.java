package ru.ezhov.utils.sql.dataio.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для определения поля как установка значения из столбца таблицы
 * <p>
 * @author ezhov_da
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OutputColumn {

    String nameColumn();
}
