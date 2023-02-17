package com.schedulesalon.prototype.util;

import java.util.Arrays;

public class UtilParam {

    public static Boolean allStringParamsAreFilled(String[] params) {
        long howManyParamsAreFilled = howManyStringParamsAreFilled(params);
        long paramsCount = stringParamsCount(params);
        return howManyParamsAreFilled == paramsCount;
    }

    public static Long stringParamsCount(Object[] params) {
        return Arrays.stream(params).count();
    }

    public static Long howManyStringParamsAreFilled(String[] params) {
        return Arrays.stream(params).filter(p -> p.length() > 0).count();
    }
}
