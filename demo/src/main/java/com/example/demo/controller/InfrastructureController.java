package com.example.demo.controller;

import com.example.demo.Infrastructure.*;
import com.example.demo.SimulatingStructure.Cluster;
import com.example.demo.SimulatingStructure.ClusterService;
import com.example.demo.repository.*;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private ClusterService clusterService;

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
    public String getBuildingByBuidlingId(
            @PathVariable final String building_id,
            Model model)
    {
        BuildingNested buildingNested = buildingService.getBuildingNestedByBuidlingId(building_id,"floor,cluster");
        Map<Integer,Boolean> matchedRes = buildingService.getFloorCluterMatchResult(buildingNested);
        model.addAttribute("matchedRes", matchedRes);
        Cluster cluster = new Cluster();
        model.addAttribute("cluster",cluster);
        if (building_id.equals("2")) {
            return "sanJoseStateUniversityBuilding";
        } else {
            return "sanJoseStateUniversityBuilding";
        }

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/index")
    public String getIndexPage(Model model) {
        return "index";
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
    public @ResponseBody Iterable<Building> searchBuidlingByLocation(
            @RequestParam(required = false) final String city,
            @RequestParam(required = false) final String state,
            @RequestParam(required = false) final String zipcode)
    {
        return buildingService.searchBuidlingByLocation(city,state,zipcode);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/buildings/{building_id}/floors/{floor_number}")
    public String getFloorPlanByBuilidngIdFloorNumber(
            @PathVariable final String building_id,
            @PathVariable final String floor_number,
            Model model)
    {
        if (building_id.equals("2") && floor_number.equals("1")) {
            Map<Integer, Integer> res = floorService.getAlltheRoomNodeSensorsMatchedResult(building_id,floor_number);
            model.addAttribute("res", res);
            return "sjsuFloor1";
        }

        if(building_id.equals("2") && floor_number.equals("2")) {
            Map<Integer, Integer> res = floorService.getAlltheRoomNodeSensorsMatchedResult(building_id,floor_number);
            model.addAttribute("res", res);
            return "sjsuFloor2";
        }
        return "Not Found.";
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/buildings")
    public @ResponseBody String addBuilding(@RequestBody Building building)
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
    public @ResponseBody String addRoom(@RequestBody Room room)
    {
        return roomService.saveNodeToDB(room);
    }
}

