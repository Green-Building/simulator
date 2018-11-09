package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.crypto.Data;

@Entity
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long building_id;
    private Long floor_id;
    private String name;
    private String type;
    private String series_number;
    private Data install_time;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Long building_id) {
        this.building_id = building_id;
    }

    public Long getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(Long floor_id) {
        this.floor_id = floor_id;
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

    public Data getInstall_time() {
        return install_time;
    }

    public void setInstall_time(Data install_time) {
        this.install_time = install_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
