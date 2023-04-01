package com.schedulesalon.prototype.hour;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AvailableDate {
    private int day;
    private int month;
    private int year;

    //TODO Para solucionar e implementar o horario de pausa, caso solucione, remover initialHour, finalHour, breakHour
    private int initialHourOnFirstRound;
    private int finalHourOnFirstRound;
    private int initialHourOnSecondRound;
    private int finalHourOnSecondRound;

    private int intervalBetweenTheHours;
}
