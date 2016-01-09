package ru.ezhov.utils.sql.dataio.io;

import java.math.BigDecimal;
import ru.ezhov.utils.sql.dataio.annotations.OutputColumn;

/**
 *
 * @author ezhov_da
 */
public class OutputObjectWithoutAnnotation {

    @OutputColumn(nameColumn = "ID")
    private int id;
    @OutputColumn(nameColumn = "PRODUCT_NAME")
    private String productName;
    @OutputColumn(nameColumn = "PRICE")
    private BigDecimal price;

    public OutputObjectWithoutAnnotation() {
    }

    @Override
    public String toString() {
        return "ObjectFromBase{" + "id=" + id + ", productName=" + productName + ", price=" + price + '}';
    }
}
