package ru.ezhov.utils.sql.dataio.io;

import java.math.BigDecimal;
import ru.ezhov.utils.sql.dataio.annotations.InputColumn;
import ru.ezhov.utils.sql.dataio.annotations.InputDataObject;

/**
 *
 * @author ezhov_da
 */
@InputDataObject
public class InputObject {

    @InputColumn(1)
    private String name;
    @InputColumn(2)
    private BigDecimal price;

    public InputObject(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
    
    
}
