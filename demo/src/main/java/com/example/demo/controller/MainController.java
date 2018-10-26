package com.example.demo.controller;


import com.example.demo.model.Cluster;
import com.example.demo.model.Node;
import com.example.demo.repository.ClusterRepository;
import com.example.demo.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private ClusterRepository clusterRepository;
    @Autowired
    private NodeRepository nodeRepository;

    @GetMapping("addcluster")
    public String addClusterForm (Model model) {
        model.addAttribute("cluster", new Cluster());
        return "addCluster";
    }

    @GetMapping("addnode")
    public String addNodeForm (Model model) {
        model.addAttribute("node", new Node());
        model.addAttribute("clusters", clusterRepository.findAll());
        return "addNode";
    }

    @PostMapping("addcluster")
    public String addclusterSubmit(@ModelAttribute Cluster cluster) {
        clusterRepository.save(cluster);
        return "resultCluster";
    }

    @PostMapping("addnode")
    public String addnodeSubmit(@ModelAttribute Node node) {
        nodeRepository.save(node);
        return "resultNode";
    }

    @GetMapping(path = "/clusters")
    public @ResponseBody Iterable<Cluster> getAllClusters() {
        return clusterRepository.findAll();
    }
}
