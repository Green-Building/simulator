package com.example.demo.Infrastructure;

import com.example.demo.controller.InfrastructureController;

public class FloorView {
    private long floor_id;
    private Integer floor_number;
    private long building_id;
    private Floor floor;

    public FloorView(long floor_id, Integer floor_number, long building_id, InfrastructureController infrastructureController) {
        this.floor_id = floor_id;
        this.floor_number = floor_number;
        this.building_id = building_id;
        infrastructureController.getFloorRepository().findById(building_id);
    }

    public long getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(long floor_id) {
        this.floor_id = floor_id;
    }

    public Integer getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(Integer floor_number) {
        this.floor_number = floor_number;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}

