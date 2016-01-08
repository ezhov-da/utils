package ru.ezhov.utils.sql.dataio.io;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author ezhov_da
 */
public class OutputObjectDatabaseTest {

    /**
     * тестируем корректное получение интерфейса List
     *
     * @throws Exception
     */
    @Test
    public void testGetCollectionFromBaseList() throws Exception {
        System.out.println("getCollectionFromBase");
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String query = "select * from TEST_SELECT";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            OutputObjectDatabase instance = new OutputObjectDatabase();
            Collection ioCollection = new ArrayList();
            assertEquals(ioCollection.size(), 0);
            ioCollection = instance.getCollectionFromBase(resultSet, ObjectFromBase.class, ioCollection);
            assertTrue(ioCollection.size() > 0);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * тестируем корректное получение интерфейса Set
     *
     * @throws Exception
     */
    @Test
    public void testGetCollectionFromBaseSet() throws Exception {
        System.out.println("getCollectionFromBase");
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String query = "select * from TEST_SELECT";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            OutputObjectDatabase instance = new OutputObjectDatabase();
            Collection ioCollection = new HashSet();
            assertEquals(ioCollection.size(), 0);
            ioCollection = instance.getCollectionFromBase(resultSet, ObjectFromBase.class, ioCollection);
            assertTrue(ioCollection.size() > 0);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * тестируем исключение Exception
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void testGetCollectionFromBase() throws Exception {
        System.out.println("getCollectionFromBase");
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String query = "select * from TEST_SELECT1";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
