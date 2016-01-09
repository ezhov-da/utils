package ru.ezhov.utils.sql.dataio.io;

import java.math.BigDecimal;
import ru.ezhov.utils.sql.dataio.annotations.InputColumn;

/**
 *
 * @author ezhov_da
 */
public class InputObjectWithoutAnnotation {

    @InputColumn(1)
    private String name;
    @InputColumn(2)
    private BigDecimal price;

    public InputObjectWithoutAnnotation(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
    
    
}
