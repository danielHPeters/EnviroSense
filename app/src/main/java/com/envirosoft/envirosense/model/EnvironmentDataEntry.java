package com.envirosoft.envirosense.model;

import java.util.Date;

/**
 * Created by admin on 31.08.2017.
 */
public class EnvironmentDataEntry {

    private Date entryDate;

    private String entryPressure;

    private String entryLight;

    private String entryTemperature;


    /**
     *
     * @param entryPressure
     * @param entryLight
     * @param entryTemperature
     */
    public EnvironmentDataEntry(String entryPressure, String entryLight, String entryTemperature) {
        this.entryDate = new Date();
        this.entryPressure = entryPressure;
        this.entryLight = entryLight;
        this.entryTemperature = entryTemperature;
    }

    /**
     *
     * @param entryPressure
     * @param entryLight
     * @param entryTemperature
     */
    public EnvironmentDataEntry(Date date, String entryPressure, String entryLight, String entryTemperature) {
        this.entryDate = date;
        this.entryPressure = entryPressure;
        this.entryLight = entryLight;
        this.entryTemperature = entryTemperature;
    }

    /**
     *
     * @return
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     *
     * @param entryDate
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }


    /**
     *
     * @return
     */
    public String getEntryPressure() {
        return entryPressure;
    }

    /**
     *
     * @param entryPressure
     */
    public void setEntryPressure(String entryPressure) {
        this.entryPressure = entryPressure;
    }

    /**
     *
     * @return
     */
    public String getEntryLight() {
        return entryLight;
    }

    /**
     *
     * @param entryLight
     */
    public void setEntryLight(String entryLight) {
        this.entryLight = entryLight;
    }

    /**
     *
     * @return
     */
    public String getEntryTemperature() {
        return entryTemperature;
    }

    /**
     *
     * @param entryTemperature
     */
    public void setEntryTemperature(String entryTemperature) {
        this.entryTemperature = entryTemperature;
    }
}
