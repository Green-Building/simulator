package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Building;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cluster")
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cluster_id")
    private long cluster_id;

    @ManyToOne
    @JoinColumn(name = "building_id")
    @JsonBackReference
    private Building building;

    @Column(name = "floor_id")
    private long floor_id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "series_number")
    private String series_number;

    @Column(name = "install_time")
    private Date install_time;

    @Column(name = "status")
    private String status;

    public Cluster() {
    }

    public Cluster(Building building, long floor_id, String name, String type, String series_number, Date install_time, String status) {
        this.building = building;
        this.floor_id = floor_id;
        this.name = name;
        this.type = type;
        this.series_number = series_number;
        this.install_time = install_time;
        this.status = status;
    }

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

    public long getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(long floor_id) {
        this.floor_id = floor_id;
    }
}
