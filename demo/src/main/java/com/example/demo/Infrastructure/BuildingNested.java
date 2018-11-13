package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class BuildingNested {
    private long id;
    private String image_url;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String num_of_floors;
    private Double longitude;
    private Double latitude;
    private List<Floor> floors;
    private List<Cluster> clusters;

    public BuildingNested(Building building, List<Floor> floors, List<Cluster> clusters)
    {
        this.id = building.getId();
        this.image_url = building.getImage_url();
        this.address = building.getAddress();
        this.city = building.getCity();
        this.state = building.getState();
        this.zipcode = building.getZipcode();
        this.num_of_floors = building.getNum_of_floors();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
        this.floors = floors;
        this.clusters = clusters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
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
