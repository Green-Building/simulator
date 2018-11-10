package com.example.demo.Infrastructure;

import com.example.demo.Infrastructure.Building;
import com.example.demo.controller.InfrastructureController;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "floor_id")
    private long floor_id;

    @Column(name = "floor_number")
    private Integer floor_number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "building_id", nullable = false, referencedColumnName = "building_id")
    @JsonBackReference
    private Building building;

    public Floor() {

    }

    public Floor(long floor_id, Integer floor_number, Building building) {
        this.floor_id = floor_id;
        this.floor_number = floor_number;
        this.building = building;

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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}

