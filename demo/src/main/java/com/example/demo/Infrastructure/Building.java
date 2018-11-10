package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({
        "building_id",
        "image_url",
        "address",
        "city",
        "state",
        "zipcode",
        "num_of_floors",
        "longitude",
        "latitude"
})

@Entity
@Table(name = "building")
public class Building implements IBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "building_id")
    private long building_id;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "num_of_floors")
    private String num_of_floors;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "building", fetch = FetchType.EAGER)
    private Set<Floor> floors = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "building", fetch = FetchType.EAGER)
    private Set<Cluster> clusters = new HashSet<>();

    public Building() {

    }

    public Building(@JsonProperty("image_url") String image_url, @JsonProperty("address") String address,
                    @JsonProperty("city") String city, @JsonProperty("state") String state,
                    @JsonProperty("zipcode") String zipcode, @JsonProperty("num_of_floors") String num_of_floors,
                    @JsonProperty("longitude") Double longitude, @JsonProperty("latitude") Double latitude) {
        this.image_url = image_url;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.num_of_floors = num_of_floors;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getNum_of_floors() {
        return num_of_floors;
    }

    public void setNum_of_floors(String num_of_floors) {
        this.num_of_floors = num_of_floors;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Set<Floor> getFloors() {
        return floors;
    }

    public void setFloors(Set<Floor> floors) {
        for (Floor floor : floors) {
            floor.setBuilding(this);
        }
        this.floors = floors;
    }

    public Set<Cluster> getClusters() {
        for (Cluster cluster: clusters) {
            cluster.setBuilding(this);
        }
        return clusters;
    }

    public void setClusters(Set<Cluster> clusters) {
        this.clusters = clusters;
    }
}
