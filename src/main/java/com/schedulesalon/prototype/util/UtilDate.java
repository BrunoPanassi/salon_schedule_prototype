package com.schedulesalon.prototype.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilDate {
    public static final String ANO_MES_DIA_HORAS_MINUTOS_SEGUNDOS = "yyyy-MM-dd HH:mm:ss";

    public static final LocalDateTime actualDate = LocalDateTime.now();

    public static DateTimeFormatter getFormatter(String format) {
        return DateTimeFormatter.ofPattern(format);
    }
}
