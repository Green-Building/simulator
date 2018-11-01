package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clusterId;
    private Long nodeId;
    private Long sensorId;
    private String sensorType;
    private Double sensorData;
    private Date date;
    private String unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public Double getSensorData() {
        return sensorData;
    }

    public void setSensorData(Double sensorData) {
        this.sensorData = sensorData;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", clusterId=" + clusterId +
                ", nodeId=" + nodeId +
                ", sensorId=" + sensorId +
                ", sensorType='" + sensorType + '\'' +
                ", sensorData=" + sensorData +
                '}';
    }

    public void updateSensorDataRandom() {
        this.sensorData = Math.random()*10;
    }
}


