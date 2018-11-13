package com.example.demo.Infrastructure;

import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class BuildingService {
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    FloorRepository floorRepository;
    @Autowired
    ClusterRepository clusterRepository;

    public String saveBuildingToDB (Building building) {
        buildingRepository.save(building);
        return building.toString();
    }

    public String getBuildingByBuidlingId(String building_id) {
        long buildingid = Long.valueOf(building_id).longValue();
        return buildingRepository.findById(buildingid).get().toString();
    }

    public String getBuildingNestedByBuidlingId(String building_id, String requirement) {
        long buildingid = Long.valueOf(building_id).longValue();
        Building building = buildingRepository.findById(buildingid).get();
        List<Cluster> clusterList = clusterRepository.findClusterByBuildingId(buildingid);
        List<Floor> floorList = floorRepository.findFloorByBuildingId(buildingid);
        BuildingNested buildingNested = new BuildingNested(building,floorList,clusterList);
        return buildingNested.toString();
    }

    public Iterable<Building> searchBuidlingByLatLng(String longitude, String latitude, String radius)
    {
        Double lat = Double.valueOf(latitude);
        Double lng = Double.valueOf(longitude);
        Double distanceLimit = Double.valueOf(radius);
        Double distanceRange;

        Iterable<Building> buildings = buildingRepository.findAll();
        List<Building> res = new LinkedList<>();

        for (Building building : buildings) {
            double pow1 = Math.pow(69.1 * (building.getLatitude() - lat), 2);
            double pow2 = Math.pow(69.1 * (lng - building.getLongitude()) * Math.cos(building.getLatitude() / 57.3), 2);
            distanceRange = pow1 + pow2;
            if( distanceRange.compareTo(distanceLimit) < 0 ) {
                res.add(building);
            }
        }
        return res;
    }

    public Iterable<Building> searchBuidlingByLocation(String city, String state, String zipcode)
    {
        Iterable<Building> buildings = buildingRepository.findAll();
        List<Building> res = new LinkedList<>();
        if (zipcode == null && city != null && state != null) {
            String cityName = city.replace("+", " ");
            for(Building building : buildings) {
                if (building.getCity().equalsIgnoreCase(cityName) && building.getState().equalsIgnoreCase(state)) {
                    res.add(building);
                }
            }
        } else if (zipcode != null) {
            for ( Building building : buildings) {
                if ( building.getZipcode().equalsIgnoreCase(zipcode) ) {
                    res.add(building);
                }
            }
        }
        return res;
    }
}
