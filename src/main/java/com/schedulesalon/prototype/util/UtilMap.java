package com.schedulesalon.prototype.util;

import java.util.HashMap;
import java.util.Map;

public class UtilMap {

    public static Map<String, Integer> returnAStringIntegerMap(String[] props, Integer[]values) throws Exception {
        Map<String, Integer> mapCreated = new HashMap<>();
        if (props.length == values.length) {
            for(String prop : props) {
                mapCreated.put(prop, values[prop.indexOf(prop)]);
            }
        } else {
            String[] exceptionWhere = {" criação de um Map<String, Integer>"};
            UtilException.throwDefault(UtilException.ExceptionBuilder(
                    UtilException.LENGTH_PARAMS_NOT_EQUAL_AT,
                    exceptionWhere
            ));
        }
        return mapCreated;
    }
}
