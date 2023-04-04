package com.schedulesalon.prototype.util;

import com.schedulesalon.prototype.hour.AvailableDate;
import com.schedulesalon.prototype.model.Hour;
import com.schedulesalon.prototype.model.Professional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UtilAvailableHour {
    public static final int MAX_HOUR = 23;
    public static final int MAX_DAY = 31;

    public static final int STANDART_MINUTE = 0;


    public static final Professional getFirstAndSecondRoundAndBreakHourToCreateTheRoundsHours(Professional professional, AvailableDate availableDate) throws Exception {
        verifyAvailableDateProps(availableDate);
        List<Hour> hours = new ArrayList<>();
        hours.addAll(makeHoursOnSpecificDate(professional, availableDate.getInitialHourOnFirstRound(), availableDate.getFinalHourOnFirstRound(), availableDate));
        hours.addAll(makeHoursOnSpecificDate(professional, availableDate.getInitialHourOnSecondRound(), availableDate.getFinalHourOnSecondRound(), availableDate));
        professional.setHours(hours);
        return professional;
    }

    public static final List<Hour> makeHoursOnSpecificDate(Professional professional, int initialHour, int finalHour, AvailableDate availableDate) throws Exception {
        return loopThroughInitialAndFinalHourToCreateTheHours(professional, initialHour, finalHour, availableDate);
    }

    private static List<Hour> loopThroughInitialAndFinalHourToCreateTheHours(Professional professional, int initialHour, int finalHour, AvailableDate availableDate) throws Exception {
        List<Hour> hours = new ArrayList<>();
        LocalDateTime begin = null;
        LocalDateTime end = null;

        for (int hour = initialHour; hour <= finalHour; hour++) {
            if (begin == null) {
                begin = makeHour(professional, availableDate.getYear(), availableDate.getMonth(), availableDate.getDay(), hour, UtilAvailableHour.STANDART_MINUTE);
            } else if (end == null) {
                end = makeHour(professional, availableDate.getYear(), availableDate.getMonth(), availableDate.getDay(), hour, UtilAvailableHour.STANDART_MINUTE);
            }

            if (begin != null && end != null) {
                Hour hourToAdd = new Hour(begin, end, professional);
                begin = end;
                end = null;
                hours.add(hourToAdd);
            }
        }
        return hours;
    }

    public static final LocalDateTime makeHour(Professional professional, int year, int month, int day, int hour, int minute) throws Exception {
        String[] params = {String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), String.valueOf(minute)};
        UtilParam.throwExceptionIfStringParamsAreNotFilled(params);
        
        LocalDateTime hourCreated = LocalDateTime.of(year, month, day, hour, minute);
        verifyActualDate(hourCreated);
        verifyIfTheProfessionalHaveTheHour(professional, hourCreated);
        return hourCreated;
    }

    public static void verifyIfTheProfessionalHaveTheHour(Professional professional, LocalDateTime hour) throws Exception {
        String[] exceptionParams = { professional.getPerson().getName(), hour.toString() };
        if (doesTheProfessionalHaveThisHour(professional, hour))
            UtilException.throwDefault(UtilException.ExceptionBuilder(
                    UtilException.PROFESSIONAL_ALREADY_HAVE_THIS_HOUR_WITH_PARAM,
                    exceptionParams
            ));
    }

    public static Boolean doesTheProfessionalHaveThisHour(Professional professional, LocalDateTime hourCreated) {
        List<Hour> professionalHours = professional.getHours();
        if (professionalHours != null)
            return professional.getHours().stream().anyMatch(hour -> hour.getBegin().equals(hourCreated) || hour.getEnd().equals(hourCreated));
        return false;
    }

    public static void verifyActualDate(LocalDateTime hour) throws Exception {
        String[] exceptionParams = { hour.format(UtilDate.getFormatter(UtilDate.ANO_MES_DIA_HORAS_MINUTOS_SEGUNDOS))};
        if (hour.isBefore(UtilDate.actualDate)) {
            UtilException.throwDefault(UtilException.ExceptionBuilder(
                    UtilException.DATE_CREATED_SMALLER_THAN_ACTUAL_WITH_PARAM,
                    exceptionParams
            ));
        }
    }

    public static void verifyAvailableDateProps(AvailableDate availableDate) throws Exception {
        verifyInitialAndFinalHour(availableDate.getInitialHourOnFirstRound(), availableDate.getFinalHourOnFirstRound());
        verifyInitialAndFinalHour(availableDate.getInitialHourOnSecondRound(), availableDate.getFinalHourOnSecondRound());
        verifyBreakHour(availableDate.getInitialHourOnFirstRound(), availableDate.getFinalHourOnFirstRound(), availableDate.getInitialHourOnSecondRound(), availableDate.getFinalHourOnSecondRound());
        //verifyInterval(availableDate.getIntervalBetweenTheHours(), availableDate.getInitialHour(), availableDate.getFinalHour()); TODO: Verificar se vai ser usado o intervalo entre os horarios
        verifyDay(availableDate.getDay());
    }

    public static final void verifyInitialAndFinalHour(int initialHour, int finalHour) throws Exception {
        if (isInitialHourOrFinalHourBiggerThanMaxHour(initialHour, finalHour))
            UtilException.throwDefault(UtilException.INVALID_HOURS_TO_ADD);
        if (isInitialHourBiggerThanFinalHour(initialHour, finalHour))
            UtilException.throwDefault(UtilException.INITIAL_HOUR_BIGGER_THAN_FINAL_HOUR);
        if (isBothHoursEqualOrZero(initialHour, finalHour))
            UtilException.throwDefault(UtilException.INITIAL_HOUR_AND_FINAL_HOUR_ZERO_OR_EQUALS);
    }

    public static final void verifyDay(int day) throws Exception {
        if (isDayBiggerThanMaxDay(day))
            UtilException.throwDefault(UtilException.DAY_BIGGER_THAN_THE_MAX_OF_THE_MONTH);
    }

    public static final void verifyInterval(int interval, int initialHour, int finalHour) throws Exception {
        if (isIntervalBiggerThanInitialAndFinalHourDifference(interval, initialHour, finalHour))
            UtilException.throwDefault(UtilException.INTERVAL_BIGGER_THAN_INITIAL_AND_FINAL_HOUR_DIFFERENCE);
    }

    private static void verifyBreakHour(int initialHourOnFirstRound, int finalHourOnFirstRound, int initialHourOnSecondRound, int finalHourOnSecondRound) throws Exception {
        String[] breakHourParams = {
                String.valueOf(initialHourOnFirstRound),
                String.valueOf(finalHourOnFirstRound),
                String.valueOf(initialHourOnSecondRound),
                String.valueOf(finalHourOnSecondRound),
        };
        String[] exceptionWhere = {"Na verificação do horário de intervalo entre os turnos."};
        UtilParam.throwExceptionWithParamIfStringParamsAreNotFilled(breakHourParams, exceptionWhere);

        if (isInitialHourSmallerThanFinalHour(initialHourOnSecondRound, finalHourOnFirstRound)) {
            String[] exceptionMessageParams = {"Primeiro horário no segundo turno", "ultimo horario no primeiro turno"};
            UtilException.throwDefault(UtilException.ExceptionBuilder(UtilException.INITIAL_HOUR_SMALLER_THAN_FINAL_HOUR_WITH_PARAM, exceptionMessageParams));
        }
        if (isInitialHourSmallerThanFinalHour(initialHourOnSecondRound, initialHourOnFirstRound)) {
            String[] exceptionMessageParams = {"Primeiro horário no segundo turno", "primeiro horario no primeiro turno"};
            UtilException.throwDefault(UtilException.ExceptionBuilder(UtilException.INITIAL_HOUR_SMALLER_THAN_FINAL_HOUR_WITH_PARAM, exceptionMessageParams));
        }
        if (isInitialHourSmallerThanFinalHour(finalHourOnSecondRound, finalHourOnFirstRound)) {
            String[] exceptionMessageParams = {"Último horário no segundo turno", "ultimo horario no primeiro turno"};
            UtilException.throwDefault(UtilException.ExceptionBuilder(UtilException.INITIAL_HOUR_SMALLER_THAN_FINAL_HOUR_WITH_PARAM, exceptionMessageParams));
        }
        if (isInitialHourSmallerThanFinalHour(finalHourOnSecondRound, initialHourOnFirstRound)) {
            String[] exceptionMessageParams = {"Último horário no segundo turno", "primeiro horario no primeiro turno"};
            UtilException.throwDefault(UtilException.ExceptionBuilder(UtilException.INITIAL_HOUR_SMALLER_THAN_FINAL_HOUR_WITH_PARAM, exceptionMessageParams));
        }
    }

    private static Boolean isInitialHourSmallerThanFinalHour(int initialHour, int finalHour) {
        return initialHour < finalHour;
    }

    private static final Boolean isBothHoursEqualOrZero(int initialHour, int finalHour) {
        return (initialHour == finalHour) && (initialHour == 0 || finalHour == 0);
    }

    public static final Boolean isIntervalBiggerThanInitialAndFinalHourDifference(int interval, int initialHour, int finalHour) throws Exception {
        if (isInitialHourBiggerThanFinalHour(initialHour, finalHour))
            UtilException.throwDefault(UtilException.INITIAL_HOUR_BIGGER_THAN_FINAL_HOUR);
        int differenceBetweenFinalAndInitialHour = finalHour - initialHour;
        if (interval >= differenceBetweenFinalAndInitialHour) {
            return true;
        }
        return false;
    }

    private static final Boolean isDayBiggerThanMaxDay(int day) {
        return day > MAX_DAY;
    }

    private static final Boolean isInitialHourBiggerThanFinalHour(int initialHour, int finalHour) {
        return initialHour > finalHour;
    }

    private static final Boolean isInitialHourOrFinalHourBiggerThanMaxHour(int initialHour, int finalHour) {
        return initialHour > MAX_HOUR || finalHour > MAX_HOUR;
    }
}
