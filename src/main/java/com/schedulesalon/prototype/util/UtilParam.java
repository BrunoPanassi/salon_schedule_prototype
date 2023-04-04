package com.schedulesalon.prototype.util;

import java.util.Arrays;

public class UtilParam {

    public static Boolean allStringParamsAreFilled(String[] params) {
        long howManyParamsAreFilled = howManyStringParamsAreFilled(params);
        long paramsCount = stringParamsCount(params);
        return howManyParamsAreFilled == paramsCount;
    }

    public static void throwExceptionIfStringParamsAreNotFilled(String[] params) throws Exception {
        if (!UtilParam.allStringParamsAreFilled(params)) {
            UtilException.throwDefault(UtilException.ALL_PARAMS_ARE_NOT_FILLED);
        }
    }

    public static void throwExceptionWithParamIfStringParamsAreNotFilled(String[] params, String[] exceptionWhere) throws Exception {
        if (!UtilParam.allStringParamsAreFilled(params)) {
            UtilException.
                    throwDefault(UtilException.ExceptionBuilder(
                    UtilException.ALL_PARAMS_ARE_NOT_FILLED_WITH_PARAM,
                    exceptionWhere
            ));
        }
    }

    public static void throwExceptionIfObjectParamIsNull(Object object) throws Exception {
        if (object == null) {
            UtilException.throwDefault(UtilException.ALL_PARAMS_ARE_NOT_FILLED);
        }
    }

    public static Long stringParamsCount(Object[] params) {
        return Arrays.stream(params).count();
    }

    public static Long howManyStringParamsAreFilled(String[] params) {
        return Arrays.stream(params).filter(p -> p.length() > 0).count();
    }
}
