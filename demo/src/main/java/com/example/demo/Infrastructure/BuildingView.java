package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.repository.BuildingRepository;
import java.util.HashSet;
import java.util.Set;

public class BuildingView implements IBuilding{
    private long building_id;
    private String image_url;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String num_of_floors;
    private Double longitude;
    private Double latitude;

    private Building building = new Building();

    public BuildingView () {

    }

    public BuildingView(Building building) {
        this.building_id = building.getBuilding_id();
        this.image_url = building.getImage_url();
        this.address = building.getAddress();
        this.city = building.getCity();
        this.state = building.getState();
        this.zipcode = building.getZipcode();
        this.num_of_floors = building.getNum_of_floors();
        this.latitude = building.getLatitude();
        this.longitude = building.getLongitude();
        this.building = null;
    }

    public void saveBuildingToDB(BuildingRepository buildingRepository) {
        this.building.setBuilding_id(this.building_id);
        this.building.setImage_url(this.image_url);
        this.building.setAddress(this.address);
        this.building.setCity(this.city);
        this.building.setState(this.state);
        this.building.setZipcode(this.zipcode);
        this.building.setNum_of_floors(this.num_of_floors);
        this.building.setLongitude(this.longitude);
        this.building.setLatitude(this.latitude);
        buildingRepository.save(this.building);
    }

    public Building findBuildingByBuildingId (String building_id, BuildingRepository buildingRepository) {
        Long buidlingId = Long.valueOf(building_id).longValue();
        return buildingRepository.findById(buidlingId).get();
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
