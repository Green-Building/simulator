package com.example.demo.SimulatingStructure;

import com.example.demo.SensorData.SensorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class SensorNested {
    private long id;
    private long node_id;
    private long cluster_id;
    private String name;
    private String type;
    private String series_number;
    private Date install_time;
    private String status;
    private SensorData sensorData;

    public SensorNested(Sensor sensor, SensorData sensorData) {
        this.id = sensor.getId();
        this.node_id = sensor.getNode_id();
        this.cluster_id = sensor.getCluster_id();
        this.name = sensor.getName();
        this.type = sensor.getType();
        this.series_number = sensor.getSeries_number();
        this.install_time = sensor.getInstall_time();
        this.status = sensor.getStatus();
        this.sensorData = sensorData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNode_id() {
        return node_id;
    }

    public void setNode_id(long node_id) {
        this.node_id = node_id;
    }

    public long getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(long cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeries_number() {
        return series_number;
    }

    public void setSeries_number(String series_number) {
        this.series_number = series_number;
    }

    public Date getInstall_time() {
        return install_time;
    }

    public void setInstall_time(Date install_time) {
        this.install_time = install_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData sensorData) {
        this.sensorData = sensorData;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}
