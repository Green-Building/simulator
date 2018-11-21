package com.example.demo.controller;

import com.example.demo.Infrastructure.BuildingService;
import com.example.demo.Infrastructure.FloorService;
import com.example.demo.SensorData.SensorDataService;
import com.example.demo.SimulatingStructure.*;
import com.example.demo.repository.*;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class SimulatorController {
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorDataService sensorDataService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private ClusterService clusterService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private BuildingService buildingService;

    @CrossOrigin(origins = "*")
    @GetMapping("/clusters/add/{building_id}/{floor_number}")
    public String getAddClusterForm(
            @PathVariable("building_id") final long building_id,
            @PathVariable("floor_number") final Integer floor_number,
            Model model)
    {
        Cluster cluster = new Cluster();
        model.addAttribute("cluster",cluster);
        model.addAttribute("building_id",building_id);
        model.addAttribute("floor_number",floor_number);
        return "addCluster";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/nodes/add/{building_id}/{floor_number}/{room_number}")
    public String getAddNodeForm(
            @PathVariable("building_id") final long building_id,
            @PathVariable("floor_number") final Integer floor_number,
            @PathVariable("room_number") final Integer room_number,
            Model model)
    {
        model.addAttribute(new Node());
        model.addAttribute("building_id", building_id);
        model.addAttribute("floor_number",floor_number);
        model.addAttribute("room_number", room_number);
        return "addNode";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/sensors/add/{building_id}/{floor_number}/{room_number}")
    public String getSensorSubmitPage(
            @PathVariable("building_id") final long building_id,
            @PathVariable("floor_number") final Integer floor_number,
            @PathVariable("room_number") final Integer room_number,
            Model model)
    {
        Sensor sensor = new Sensor();
        model.addAttribute("sensor", sensor);
        model.addAttribute("building_id", building_id);
        model.addAttribute("floor_number",floor_number);
        model.addAttribute("room_number", room_number);
        return "addSensor";
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value="/clusters/add/{building_id}/{floor_number}")
    public String addClusterSubmit(
            @ModelAttribute Cluster cluster,
            @PathVariable("building_id") final String building_id,
            @PathVariable("floor_number") final String floor_number,
            Model model)
    {
        Cluster newCluster = clusterService.saveClusterToDB(cluster,building_id,floor_number);
        ClientHttpRequestFactory requestFactory = new
             HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/clusters";
        String result = restTemplate.postForObject(url, newCluster, String.class);
        model.addAttribute("buildingId", newCluster.getBuilding_id());
        return "resultAfterAddDeleteNewCluster";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/sensors/add/{building_id}/{floor_number}/{room_number}")
    public String addsensorSubmit(
            @ModelAttribute Sensor sensor,
            @PathVariable("building_id") final String building_id,
            @PathVariable("floor_number") final String floor_number,
            @PathVariable("room_number") final String room_number,
            Model model)
    {
        Sensor sensorNew = sensorService.saveSensorToDB(sensor,building_id,floor_number,room_number);
        ClientHttpRequestFactory requestFactory = new
            HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/sensors";

        String result = restTemplate.postForObject(url, sensorNew, String.class);


        sensorDataService.createSensorData(sensorNew);


        model.addAttribute("building_id",building_id);
        model.addAttribute("floor_number", floor_number);
        return "resultAfterAddDeleteNewSensor";
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/nodes/add/{building_id}/{floor_number}/{room_number}")
    public String addnodeSubmit(
            @ModelAttribute Node node,
            @PathVariable("building_id") final String building_id,
            @PathVariable("floor_number") final String floor_number,
            @PathVariable("room_number") final String room_number,
            Model model)
    {
        Node nodeNew = nodeService.saveNodeToDB(node, building_id,floor_number,room_number);
        model.addAttribute("building_id",building_id);
        model.addAttribute("floor_number", floor_number);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String url = "http://localhost:3006/nodes";
        String result = restTemplate.postForObject(url, nodeNew, String.class);

        return "resultAfterAddDeleteNewSensor";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/clusters/delete/{building_id}/{floor_number}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public String deleteClusterByClusterId (
            @PathVariable("building_id" ) final String building_id,
            @PathVariable("floor_number") final String floor_number,
            Model model)
    {
        long cluster_id = clusterService.deleteClusterByBuidlingIdFloorNumber(building_id,floor_number);

        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/clusters/" + cluster_id+"?from=1";
        restTemplate.delete(url);
        long buildingId = Long.valueOf(building_id).longValue();
        model.addAttribute("buildingId", buildingId);
        return "resultAfterAddDeleteNewCluster";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/nodes/delete/{building_id}/{floor_number}/{room_number}")
    public String deleteNodeByNodeId(
            @PathVariable final String building_id,
            @PathVariable final String floor_number,
            @PathVariable final String room_number,
            Model model)
    {
        long node_id = nodeService.deleteNode(building_id,floor_number,room_number);
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/nodes/"+node_id+"?from=1";
        restTemplate.delete(url);
        model.addAttribute("building_id", building_id);
        model.addAttribute("floor_number",floor_number);
        model.addAttribute("room_number", room_number);
        return "resultAfterAddDeleteNewSensor";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/sensors/delete/{building_id}/{floor_number}/{room_number}")
    public String deleteSensorBySensorId(
            @PathVariable("building_id") final String building_id,
            @PathVariable("floor_number") final String floor_number,
            @PathVariable("room_number") final String room_number,
            Model model)
    {
        long sensor_id = sensorService.deleteASensorFromARoom(building_id, floor_number,room_number);
        model.addAttribute("building_id", building_id);
        model.addAttribute("floor_number",floor_number);
        model.addAttribute("room_number", room_number);
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String url = "http://localhost:3006/sensors/" + sensor_id+"?from=1";
        restTemplate.delete(url);
        return "resultAfterAddDeleteNewSensor";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/clusters/{cluster_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody String getClusterNestedByClusterID(
            @PathVariable("cluster_id") final String cluster_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedRequirement)
    {
        if(nestedRequirement == null) {
            return clusterService.getClusterByClusterId(cluster_id);
        } else {
            return clusterService.getClusterNestedByClusterId(cluster_id, nestedRequirement);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/nodes/{node_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody String getNodeNestedByNodeID(
            @PathVariable("node_id") final String node_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedRequirement)
    {
        if(nestedRequirement == null) {
            return nodeService.getNodeByNodeId(node_id);
        } else {
            return nodeService.getNodeNestedByNodeId(node_id, nestedRequirement);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/sensors/{sensor_id}")
    //https://stackoverflow.com/questions/13715811/requestparam-vs-pathvariable
    public @ResponseBody String getSensorNestedBySensorID(
            @PathVariable("sensor_id") final String sensor_id,
            @RequestParam(value = "fetch_nested", required = false) final String nestedRequirement)
    {
        if(nestedRequirement == null) {
            return sensorService.getSensorBySensorId(sensor_id);
        } else {
            return sensorService.getSensorNestedBySensorId(sensor_id, nestedRequirement);
        }
    }
}

