package com.schedulesalon.prototype.hour;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AvailableDate {
    private int day;
    private int month;
    private int year;

    private int initialHourOnFirstRound;
    private int finalHourOnFirstRound;
    private int initialHourOnSecondRound;
    private int finalHourOnSecondRound;

    private int initialHourOnSingleBooking;
    private int finalHourOnSingleBooking;

    private int intervalBetweenTheHours;

    public String getYearInString() {
        return String.valueOf(this.year);
    }

    public String getMonthInString() {
        return String.valueOf(this.month);
    }

    public String getDayInString() {
        return String.valueOf(this.day);
    }

    public String getInitialHourSingleInString() {
        return String.valueOf(this.initialHourOnSingleBooking);
    }

    public String getFinalHourSingleInString() {
        return String.valueOf(this.finalHourOnSingleBooking);
    }
}
