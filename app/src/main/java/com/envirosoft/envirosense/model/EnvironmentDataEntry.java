package com.envirosoft.envirosense.model;

import java.util.Date;

/**
 * Created by admin on 31.08.2017.
 */
public class EnvironmentDataEntry {

    private Date entryDate;

    private String entryTemperature;

    private String entryPressure;

    public EnvironmentDataEntry(String entryTemperature, String entryPressure) {
        this.entryDate = new Date();
        this.entryTemperature = entryTemperature;
        this.entryPressure = entryPressure;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryTemperature() {
        return entryTemperature;
    }

    public void setEntryTemperature(String entryTemperature) {
        this.entryTemperature = entryTemperature;
    }

    public String getEntryPressure() {
        return entryPressure;
    }

    public void setEntryPressure(String entryPressure) {
        this.entryPressure = entryPressure;
    }
}
