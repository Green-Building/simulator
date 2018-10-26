package com.example.demo.controller;


import com.example.demo.model.Cluster;
import com.example.demo.repository.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private ClusterRepository clusterRepository;

    @GetMapping("addcluster")
    public String addClusterForm (Model model) {
        model.addAttribute("cluster", new Cluster());
        return "addCluster";
    }

    @PostMapping("addcluster")
    public String addclusterSubmit(@ModelAttribute Cluster cluster) {
        clusterRepository.save(cluster);
        return "result";
    }

    @GetMapping(path = "/clusters")
    public @ResponseBody Iterable<Cluster> getAllClusters() {
        return clusterRepository.findAll();
    }
}
