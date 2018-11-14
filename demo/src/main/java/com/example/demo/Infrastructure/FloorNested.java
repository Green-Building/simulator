package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class FloorNested {
    private long id;
    private long building_id;
    private Integer floor_number;
    private Cluster cluster;
    private List<Room> roomList;
    private List<Node> nodeList;
    private List<Sensor> sensorList;

    public FloorNested(Floor floor, Cluster cluster, List<Room> roomList, List<Node> nodeList, List<Sensor> sensorList) {
        this.id = floor.getId();
        this.building_id = floor.getBuilding_id();
        this.floor_number = floor.getFloor_number();
        this.cluster = cluster;
        this.roomList = roomList;
        this.nodeList = nodeList;
        this.sensorList = sensorList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public Integer getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(Integer floor_number) {
        this.floor_number = floor_number;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
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

