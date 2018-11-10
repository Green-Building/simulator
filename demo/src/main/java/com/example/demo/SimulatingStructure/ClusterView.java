package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Building;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

public class ClusterView {
    private long cluster_id;
    private Building building;
    private String name;
    private String type;
    private String series_number;
    private Date install_time;
    private String status;

    private Cluster cluster;
    
    public long getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(long cluster_id) {
        this.cluster_id = cluster_id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
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
}

