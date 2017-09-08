package com.envirosoft.envirosense.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by admin on 31.08.2017.
 */
public class EnvironmentDataEntry implements Comparable<EnvironmentDataEntry> {

    public static final String LOCATION_UNKNOWN = "unknown";

    private UUID id;

    private Date date;

    private String location;

    private String pressure;

    private String luminance;

    private String temperature;


    /**
     * @param pressure
     * @param luminance
     * @param temperature
     */
    public EnvironmentDataEntry(String location, String pressure, String luminance, String temperature) {
        this.id = UUID.randomUUID();
        this.date = new Date();
        this.location = location;
        this.pressure = pressure;
        this.luminance = luminance;
        this.temperature = temperature;
    }

    /**
     * @param pressure
     * @param luminance
     * @param temperature
     */
    public EnvironmentDataEntry(Date date, String location, String pressure, String luminance, String temperature) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.location = location;
        this.pressure = pressure;
        this.luminance = luminance;
        this.temperature = temperature;
    }

    /**
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @return
     */
    public String getPressure() {
        return pressure;
    }

    /**
     * @return
     */
    public String getLuminance() {
        return luminance;
    }

    /**
     * @return
     */
    public String getTemperature() {
        return temperature;
    }

    @Override
    public int compareTo(EnvironmentDataEntry another) {
        return Long.compare(this.date.getTime(), another.getDate().getTime());
    }

    @Override
    public String toString(){
        return "EnvironmentDataEntry [id=" + id + ", date=" + date.toString() + ", location= "
                +location + ", pressure=" + pressure + ", luminance=" + luminance + ", temperature=" + temperature + "]";
    }
}
