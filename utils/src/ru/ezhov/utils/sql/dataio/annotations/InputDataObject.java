package ru.ezhov.utils.sql.dataio.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Этой аннотацией помечается класс, который будет готов для внесения его данных в базу
 * <p>
 * @author ezhov_da
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputDataObject
{
}
