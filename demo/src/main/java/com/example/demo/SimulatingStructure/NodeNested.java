package com.example.demo.SimulatingStructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

public class NodeNested {
    private long id;
    private long cluster_id;
    private long room_id;
    private String name;
    private String type;
    private String series_number;
    private Date install_time;
    private String status;
    private List<Sensor> sensorSet;

    public NodeNested(Node node, List<Sensor> sensorSet) {
        this.id = node.getId();
        this.cluster_id = node.getCluster_id();
        this.room_id = node.getRoom_id();
        this.name = node.getName();
        this.type = node.getType();
        this.series_number = node.getSeries_number();
        this.install_time = node.getInstall_time();
        this.status = node.getStatus();
        this.sensorSet = sensorSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(long cluster_id) {
        this.cluster_id = cluster_id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
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

    public List<Sensor> getSensorSet() {
        return sensorSet;
    }

    public void setSensorSet(List<Sensor> sensorSet) {
        this.sensorSet = sensorSet;
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