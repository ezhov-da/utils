package ru.ezhov.utils.sql.dataio.io;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.ezhov.utils.sql.dataio.annotations.InputColumn;
import ru.ezhov.utils.sql.dataio.annotations.InputDataObject;

/**
 * Класс вносит список объектов в базу данных
 * <p>
 * @author ezhov_da
 * @param <T> - объект, который вносим в базу данных
 */
public class InputObjectDatabase<T> {

    private static final Logger LOG = Logger.getLogger(InputObjectDatabase.class.getName());
    private final Connection connection;

    public InputObjectDatabase(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("подключение не должно быть null");
        }
        this.connection = connection;
    }

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
     * @param list - список объектов для внесения
     * @param query - запрос для внесения, должен выглядеть как стандартный
     * запрос java
     * @param sizeBatch - какого размера делать butch
     * <p>
     * @return возвращаем true в случае внесения и false в случае ошибок
     * <p>
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public synchronized boolean setDataToBase(List<T> list, String query, int sizeBatch)
            throws SQLException {
        connection.setAutoCommit(false);
        //получаем объект для выполнения запроса
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            int sizeList = list.size(); //получаем размер списка
            for (int i = 0; i < sizeList; i++) {
                T c = (T) list.get(i);  //получаем объект класса
                Class clazz = c.getClass(); // получаем класс
                if (clazz.isAnnotationPresent(InputDataObject.class)) //проверяем анотацию на классе
                {
                    Field[] fields = clazz.getDeclaredFields(); //получаем список  полей
                    for (Field field : fields) // перебираем поля
                    {
                        field.setAccessible(true); //делаем поле доступным
                        if (field.isAnnotationPresent(InputColumn.class)) //проверяем аннтотациб на классе
                        {
                            InputColumn inputColumn = field.getAnnotation(InputColumn.class);   // получаем экземпляр аннотации
                            int num = inputColumn.value(); //получаем значение
                            Object object = field.get(c); //получаем значение поля
                            preparedStatement.setObject(num, object);   //добавляем параметр
                        }
                    }
                    preparedStatement.addBatch(); //добавляем строку в группу
                    if (i % sizeBatch == 0) //смотрим, нужно ли выполнять группу
                    {
                        preparedStatement.executeBatch();
                    }
                } else {
                    throw new ClassNotFoundException("объект не аннотирован аннотацией InputDataObject");
                }
            }
            preparedStatement.executeBatch(); //выполняем если вдруг меньше данных, чем в установленой группе
            connection.commit();                // делаем применение
            connection.setAutoCommit(true); //делаем автокоммит true
            return true;
        } catch (ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
