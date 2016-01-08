package ru.ezhov.utils.sql.dataio.io;

import java.math.BigDecimal;
import ru.ezhov.utils.sql.dataio.annotations.OutputColumn;
import ru.ezhov.utils.sql.dataio.annotations.OutputDataObject;

/**
 *
 * @author ezhov_da
 */
@OutputDataObject
public class ObjectFromBase {

    @OutputColumn(nameColumn = "ID")
    private int id;
    @OutputColumn(nameColumn = "PRODUCT_NAME")
    private String productName;
    @OutputColumn(nameColumn = "PRICE")
    private BigDecimal price;

    public ObjectFromBase() {
    }

    @Override
    public String toString() {
        return "ObjectFromBase{" + "id=" + id + ", productName=" + productName + ", price=" + price + '}';
    }
}
