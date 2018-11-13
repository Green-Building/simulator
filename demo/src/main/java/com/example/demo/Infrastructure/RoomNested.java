package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;

public class RoomNested {
    private long id;
    private Long floor_id;
    private Long building_id;
    private Integer room_number;
    private Node node;
    private List<Sensor> sensorList = new LinkedList<>();

    public RoomNested(Room room, Node node, List<Sensor> sensorList) {
        this.id = room.getId();
        this.floor_id = room.getFloor_id();
        this.building_id = room.getBuilding_id();
        this.room_number = room.getRoom_number();
        this.node = node;
        this.sensorList = sensorList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(Long floor_id) {
        this.floor_id = floor_id;
    }

    public Long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Long building_id) {
        this.building_id = building_id;
    }

    public Integer getRoom_number() {
        return room_number;
    }

    public void setRoom_number(Integer room_number) {
        this.room_number = room_number;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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
