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
    public void testGetCollectionList() throws Exception {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String query = "select * from TEST_SELECT";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            OutputObjectDatabase instance = new OutputObjectDatabase();
            Collection<OutputObject> ioCollection = instance.getCollection(resultSet, OutputObject.class, new ArrayList());
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
    public void testGetCollectionSet() throws Exception {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String query = "select * from TEST_SELECT";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            OutputObjectDatabase instance = new OutputObjectDatabase();
            Collection<OutputObject> ioCollection = instance.getCollection(resultSet, OutputObject.class, new HashSet());
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
    public void testGetCollectionErrorAnnotation() throws Exception {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String query = "select * from TEST_SELECT";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            OutputObjectDatabase instance = new OutputObjectDatabase();
            Collection<OutputObjectWithoutAnnotation> ioCollection
                    = instance.getCollection(resultSet, OutputObjectWithoutAnnotation.class, new HashSet());
            assertTrue(ioCollection.isEmpty());
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
    public void testGetCollection() throws Exception {
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
