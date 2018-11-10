package com.example.demo.controller;

import com.example.demo.Infrastructure.*;
import com.example.demo.repository.*;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Controller
public class InfrastructureController {

    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private RoomRepository roomRepository;

    public ClusterRepository getClusterRepository() {
        return clusterRepository;
    }

    public NodeRepository getNodeRepository() {
        return nodeRepository;
    }

    public SensorRepository getSensorRepository() {
        return sensorRepository;
    }

    public SensorDataRepository getSensorDataRepository() {
        return sensorDataRepository;
    }

    public BuildingRepository getBuildingRepository() {
        return buildingRepository;
    }

    public FloorRepository getFloorRepository() {
        return floorRepository;
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/buildings")
    public @ResponseBody
    BuildingView addBuilding(@RequestBody BuildingView buildingView) {
        buildingView.saveBuildingToDB(buildingRepository);
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        //String url = "http://localhost:3010/buildings";
        //String result = restTemplate.postForObject(url, building, String.class);
        return buildingView;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody
    IBuilding getbuidlingById(
            @PathVariable("building_id") final String building_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent
    ) {
        Long buildingId = Long.valueOf(building_id).longValue();
        if ( nestedContent != null ) {
            switch(nestedContent) {
                case "floor,cluster":
                    return buildingRepository.findById(buildingId).get();
                case "floor":
                    return null;
                case "cluster":
                    return null;
            }
        } else {
            BuildingView buildingView = new BuildingView(buildingRepository.findById(buildingId).get());
            return buildingView;
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/geocode")
    public @ResponseBody
    Iterable<Building> searchBuidlingByLatLng(@RequestParam final String longitude, @RequestParam final String latitude, @RequestParam String radius)
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

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/location")
    public @ResponseBody Iterable<Building> searchBuidlingByLocation(@RequestParam(required = false) final String city,
                                                                     @RequestParam(required = false) final String state,
                                                                     @RequestParam(required = false) final String zipcode)
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

    @CrossOrigin(origins = "*")
    @PostMapping("/floors")
    public @ResponseBody
    Floor addFloor(@RequestBody String json)  {
        long buildingid = 0;
        int floornumber = 0;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            buildingid = jsonObject.getLong("building_id");
            floornumber = jsonObject.getInt("floor_number");
            System.out.println("building_id:" + buildingid + "floor_number:" + floornumber );
            Building building = buildingRepository.findById(buildingid).get();
            Floor floor = new Floor(buildingid, floornumber, building);
            building.getFloors().add(floor);
            buildingRepository.save(building);
            Set<Floor> floors = buildingRepository.findById(buildingid).get().getFloors();
            long maxID = 0;
            for (Floor floor1: floors) {
                long currentId = floor1.getFloor_id();
                if( currentId > maxID ) {
                    maxID = currentId;
                }
            }
            return floorRepository.findById(maxID).get();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/rooms")
    public @ResponseBody
    Room addRoom(@RequestBody Room room)
    {
        roomRepository.save(room);
        return room;
    }
}

