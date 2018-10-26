package com.example.demo.repository;

import com.example.demo.model.Node;

import org.springframework.data.repository.CrudRepository;

public interface NodeRepository extends CrudRepository <Node, Long> {

}
