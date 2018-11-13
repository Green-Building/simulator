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

import java.awt.datatransfer.FlavorListener;
import java.util.*;

@Controller
public class InfrastructureController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;

    @CrossOrigin(origins = "*")
    @GetMapping("/rooms/{room_id}")
    public @ResponseBody
    String getRoomById(
            @PathVariable("room_id") final String room_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent
    ) {
        if (nestedContent == null) {
            return roomService.getRoomByRoomId(room_id).toString();
        } else {
            return roomService.getRoomNestedByRoomId(room_id, nestedContent).toString();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/floors/{floor_id}")
    public @ResponseBody
    String getFloorById(
            @PathVariable("floor_id") final String floor_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent
    ) {
        if (nestedContent == null) {
            return floorService.getFloorByFloorId(floor_id);
        } else {
            return floorService.getFloorNestedByFloorId(floor_id,nestedContent);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody
    String getbuidlingById(
            @PathVariable("building_id") final String building_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedContent
    ) {
        if (nestedContent == null) {
            return buildingService.getBuildingByBuidlingId(building_id);
        } else {
            return buildingService.getBuildingNestedByBuidlingId(building_id,nestedContent);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/geocode")
    public @ResponseBody
    Iterable<Building> searchBuidlingByLatLng(
            @RequestParam final String longitude,
            @RequestParam final String latitude,
            @RequestParam String radius)
    {
        return buildingService.searchBuidlingByLatLng(longitude,latitude,radius);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/search/location")
    public @ResponseBody
    Iterable<Building> searchBuidlingByLocation(@RequestParam(required = false) final String city,
                                                @RequestParam(required = false) final String state,
                                                @RequestParam(required = false) final String zipcode)
    {
        return buildingService.searchBuidlingByLocation(city,state,zipcode);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/buildings")
    public @ResponseBody
    String addBuilding(@RequestBody Building building)
    {
        return buildingService.saveBuildingToDB(building);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/floors")
    public @ResponseBody
    String addFloor(@RequestBody Floor floor)
    {
        return floorService.saveFloorToDB(floor);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/rooms")
    public @ResponseBody
    String addRoom(@RequestBody Room room)
    {
        return roomService.saveNodeToDB(room);
    }
}

