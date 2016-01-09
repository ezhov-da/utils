package ru.ezhov.utils.sql.dataio.io;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ezhov_da
 */
public class InputObjectDatabaseTest {

    @Test
    public void testAddCollection() throws Exception {
        InputObjectDatabase inputObjectDatabase = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String queryDelete = "delete from TEST_INSERT";
            String queryInsert = "insert into TEST_INSERT (PRODUCT_NAME, PRICE) values (?,?)";
            connection.createStatement().execute(queryDelete);
            inputObjectDatabase = new InputObjectDatabase();
            Collection<InputObject> collection = new ArrayList<InputObject>();
            for (int i = 1; i < 6; i++) {
                collection.add(new InputObject(String.valueOf(i), BigDecimal.valueOf(i)));
            }
            inputObjectDatabase.addCollection(connection, collection, queryInsert, 5);
        } finally {
            if (inputObjectDatabase != null) {
                inputObjectDatabase.closeConnection();
            }
        }
    }

    @Test(expected = ClassNotFoundException.class)
    public void testAddCollectionWithoutAnnotation() throws Exception {
        InputObjectDatabase inputObjectDatabase = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String queryDelete = "delete from TEST_INSERT";
            String queryInsert = "insert into TEST_INSERT (PRODUCT_NAME, PRICE) values (?,?)";
            connection.createStatement().execute(queryDelete);
            inputObjectDatabase = new InputObjectDatabase();
            Collection<InputObjectWithoutAnnotation> collection = new ArrayList<InputObjectWithoutAnnotation>();
            for (int i = 1; i < 6; i++) {
                collection.add(new InputObjectWithoutAnnotation(String.valueOf(i), BigDecimal.valueOf(i)));
            }
            inputObjectDatabase.addCollection(connection, collection, queryInsert, 5);
        } finally {
            if (inputObjectDatabase != null) {
                inputObjectDatabase.closeConnection();
            }
        }
    }

    @Test(expected = SQLException.class)
    public void testAddCollectionSqlException() throws Exception {
        InputObjectDatabase inputObjectDatabase = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String queryDelete = "delete from TEST_INSERT";
            String queryInsert = "insert into TEST_INSERT1 (PRODUCT_NAME, PRICE) values (?,?)";
            connection.createStatement().execute(queryDelete);
            inputObjectDatabase = new InputObjectDatabase();
            Collection<InputObjectWithoutAnnotation> collection = new ArrayList<InputObjectWithoutAnnotation>();
            for (int i = 1; i < 6; i++) {
                collection.add(new InputObjectWithoutAnnotation(String.valueOf(i), BigDecimal.valueOf(i)));
            }
            inputObjectDatabase.addCollection(connection, collection, queryInsert, 5);
        } finally {
            if (inputObjectDatabase != null) {
                inputObjectDatabase.closeConnection();
            }
        }
    }

    @Test
    public void testAddObject() throws Exception {
        InputObjectDatabase inputObjectDatabase = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String queryDelete = "delete from TEST_INSERT";
            String queryInsert = "insert into TEST_INSERT (PRODUCT_NAME, PRICE) values (?,?)";
            connection.createStatement().execute(queryDelete);
            inputObjectDatabase = new InputObjectDatabase();
            inputObjectDatabase.addObject(connection, new InputObject(String.valueOf(5), BigDecimal.valueOf(0, 05)), queryInsert);
        } finally {
            if (inputObjectDatabase != null) {
                inputObjectDatabase.closeConnection();
            }
        }
    }

    @Test(expected = ClassNotFoundException.class)
    public void testAddObjectWithoutAnnotation() throws Exception {
        InputObjectDatabase inputObjectDatabase = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String queryDelete = "delete from TEST_INSERT";
            String queryInsert = "insert into TEST_INSERT (PRODUCT_NAME, PRICE) values (?,?)";
            connection.createStatement().execute(queryDelete);
            inputObjectDatabase = new InputObjectDatabase();
            inputObjectDatabase.addObject(connection, new InputObjectWithoutAnnotation(String.valueOf(5), BigDecimal.valueOf(0, 05)), queryInsert);
        } finally {
            if (inputObjectDatabase != null) {
                inputObjectDatabase.closeConnection();
            }
        }
    }

    @Test(expected = SQLException.class)
    public void testAddObjectSqlException() throws Exception {
        InputObjectDatabase inputObjectDatabase = null;
        try {
            Class.forName("org.h2.Driver");
            File db = new File("src_for_test_sql/test_base");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + db.getAbsolutePath());
            String queryDelete = "delete from TEST_INSERT";
            String queryInsert = "insert into TEST_INSERT1 (PRODUCT_NAME, PRICE) values (?,?)";
            connection.createStatement().execute(queryDelete);
            inputObjectDatabase = new InputObjectDatabase();
            inputObjectDatabase.addObject(connection, new InputObjectWithoutAnnotation(String.valueOf(5), BigDecimal.valueOf(0, 05)), queryInsert);
        } finally {
            if (inputObjectDatabase != null) {
                inputObjectDatabase.closeConnection();
            }
        }
    }
}
