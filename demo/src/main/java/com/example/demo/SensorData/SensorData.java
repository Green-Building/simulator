package com.example.demo.SensorData;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@JsonPropertyOrder({
        "id",
        "sensor_id",
        "node_id",
        "cluster_id",
        "type",
        "unit",
        "sensor_data",
        "date"
})

@Entity
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long sensor_id;
    private long node_id;
    private long cluster_id;
    private String type;
    private String unit;
    private Double sensordata;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(long sensor_id) {
        this.sensor_id = sensor_id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getSensordata() {
        return sensordata;
    }

    public void setSensordata(Double sensordata) {
        this.sensordata = sensordata;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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


