package ru.ezhov.utils.sql.dataio.io;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.utils.sql.dataio.annotations.*;

/**
 * Класс вносит данные в БД
 * <p>
 * @author ezhov_da
 * @param <T> - объект, который вносим в базу данных
 */
public class InputObjectDatabase<T> {

    private static final Logger LOG = Logger.getLogger(InputObjectDatabase.class.getName());
    private Connection connection;
    private Collection<? super T> collection;
    private String query;
    private int sizeBatch;

    /**
     * Закрываем подключение в случае необходимости
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex); //по факту нам всеравно на это исключение
            }
        }
    }

    /**
     * Вносим данные в базу
     * <p>
     * @param connection - подключение к БД
     * @param collection - список объектов для внесения
     * @param query - запрос для внесения, должен выглядеть как стандартный
     * запрос java
     * @param sizeBatch - какого размера делать batch
     * @throws Exception - перехватываем все исключения, так как только при
     * корректном внесении применяем данные
     */
    public synchronized void addCollection(Connection connection, Collection<? super T> collection, String query, int sizeBatch)
            throws Exception {
        this.connection = connection;
        this.collection = collection;
        this.query = query;
        this.sizeBatch = sizeBatch;
        execute();
    }

    @SuppressWarnings("unchecked")
    private void execute() throws Exception {
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = null;
        int counterBatch = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            Iterator<? super T> iterator = collection.iterator();
            while (iterator.hasNext()) {
                counterBatch++;
                T c = (T) iterator.next();
                addToBatch(c, preparedStatement, counterBatch);
            }
            preparedStatement.executeBatch();
            connection.commit();
        } finally {
            connection.setAutoCommit(true);
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void addToBatch(T c, PreparedStatement preparedStatement, int counterBatch) throws Exception {
        Class clazz = c.getClass();
        if (clazz.isAnnotationPresent(InputDataObject.class)) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(InputColumn.class)) {
                    InputColumn inputColumn = field.getAnnotation(InputColumn.class);
                    int num = inputColumn.value();
                    Object object = field.get(c);
                    preparedStatement.setObject(num, object);
                }
            }
            preparedStatement.addBatch();
            if (counterBatch % sizeBatch == 0) {
                preparedStatement.executeBatch();
            }
        } else {
            throw new ClassNotFoundException("объект " + clazz.getName() + " не аннотирован аннотацией InputDataObject");
        }
    }

    public synchronized void addObject(Connection connection, T objectToAdd, String query) throws Exception {
        this.connection = connection;
        this.query = query;
        this.sizeBatch = 1;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            addToBatch(objectToAdd, preparedStatement, 0);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
}
