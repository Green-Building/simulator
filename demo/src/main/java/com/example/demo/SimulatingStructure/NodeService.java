package com.example.demo.SimulatingStructure;

import com.example.demo.Infrastructure.Room;
import com.example.demo.repository.NodeRepository;
import com.example.demo.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private SensorRepository sensorRepository;

    public void saveNodeToDB(Node node) {
        nodeRepository.save(node);
    }

    public String getNodeByNodeId (String node_id) {
        long nodeid = Long.valueOf(node_id).longValue();
        Node node = nodeRepository.findById(nodeid).get();
        return node.toString();
    }

    public String getNodeNestedByNodeId(String node_id, String nestedRequirement) {
        Long nodeid = Long.valueOf(node_id).longValue();
        Node node = nodeRepository.findById(nodeid).get();
        List<Sensor> sensors = sensorRepository.findSensorByNodeId(nodeid);
        NodeNested nodeNested;
        switch(nestedRequirement) {
            case "sensor":
                nodeNested = new NodeNested(node,sensors);
                return nodeNested.toString();
            default:
                return nodeRepository.findById(nodeid).toString();
        }
    }
}
