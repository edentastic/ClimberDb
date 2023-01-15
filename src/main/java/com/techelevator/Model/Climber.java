package com.techelevator.Model;

import java.util.ArrayList;
import java.util.List;

public class Climber {
    private int climberId;
    private String climberName;
    private boolean isInjured;
    List<String> daysAvailable;

    public Climber(int climberId, String climberName, boolean isInjured) {
        this.climberId = climberId;
        this.climberName = climberName;
        this.isInjured = isInjured;
        daysAvailable = new ArrayList<>();
    }
    public Climber(String name){
        this.climberId=-1;
        this.climberName=name;
        this.isInjured=false;
    }

    public List<String> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(List<String> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public int getClimberId() {
        return climberId;
    }

    public void setClimberId(int climberId) {
        this.climberId = climberId;
    }

    public String getClimberName() {
        return climberName;
    }

    public void setClimberName(String climberName) {
        this.climberName = climberName;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }

    public void addDayAvailable(String day){
        daysAvailable.add(day);
    }

    @Override
    public String toString() {
        return getClimberName() + (isInjured ? " -Injured" : "");
    }
}
