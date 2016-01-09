package ru.ezhov.utils.sql.dataio.io;

import java.lang.reflect.*;
import java.sql.*;
import java.util.Collection;
import java.util.logging.*;
import ru.ezhov.utils.sql.dataio.annotations.*;

/**
 * Класс формирует список объектов из базы данных
 * <p>
 * @author ezhov_da
 * @param <T> - объект который получает из базы данных
 */
public class OutputObjectDatabase<T> {

    private static final Logger LOG = Logger.getLogger(OutputObjectDatabase.class.getName());
    private ResultSet resultSet;
    private Class objectCreate;
    private Collection<? super T> outputCollection;

    /**
     * Получаем список объектов базы данных
     *
     * @param resultSet - набор данных из БД
     * @param objectCreate - объект для формирования
     * @param outputCollection - коллекция для заполнения
     * @return outputCollection
     * @throws java.sql.SQLException - ошибка при работе с БД
     */
    public synchronized Collection<? super T> getCollection(
            ResultSet resultSet,
            Class objectCreate,
            Collection<T> outputCollection
    ) throws SQLException {
        this.resultSet = resultSet;
        this.objectCreate = objectCreate;
        this.outputCollection = outputCollection;
        return execute();
    }

    private Collection<? super T> execute() throws SQLException {
        try {
            if (isAnnotationPresent()) {
                fillCollection();
            } else {
                throw new ClassNotFoundException("объект не аннотирован аннотацией OutputDataObject");
            }
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.WARNING, "ошибка аннотации", ex);
        }
        return this.outputCollection;
    }

    @SuppressWarnings("unchecked")
    private boolean isAnnotationPresent() {
        return objectCreate.isAnnotationPresent(OutputDataObject.class);
    }

    @SuppressWarnings("unchecked")
    private void fillCollection() throws SQLException {
        try {
            while (resultSet.next()) {
                T c = (T) objectCreate.newInstance();
                Field[] fields = objectCreate.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(OutputColumn.class)) {
                        OutputColumn f = field.getAnnotation(OutputColumn.class);
                        field.set(c, resultSet.getObject(f.nameColumn()));
                    }
                }
                outputCollection.add(c);
            }
        } catch (InstantiationException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
