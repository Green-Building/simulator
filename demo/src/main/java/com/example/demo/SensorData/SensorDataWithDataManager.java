package com.example.demo.SensorData;

import java.util.Date;

public class SensorDataWithDataManager {
    private long sensorId;
    private String unit;
    private Double data;
    private String type;
    private Date date;

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
