package ru.ezhov.utils.sql.dataio.io;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ezhov_da
 */
public class InputObjectDatabaseTest {

    public InputObjectDatabaseTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of closeConnection method, of class InputObjectDatabase.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        InputObjectDatabase instance = null;
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDataToBase method, of class InputObjectDatabase.
     */
    @Test
    public void testSetDataToBase() throws Exception {

    }
}
