package com.example.demo.repository;

import com.example.demo.model.Node;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface NodeRepository extends CrudRepository <Node, Long> {
    @Transactional
    @Modifying
    @Query("delete from Node node where node.clusterId = :clusterId")
    void deleteNodeByClusterId(@Param("clusterId")Long clusterId);

    @Transactional
    @Modifying
    @Query("UPDATE Node node SET node.status = :status where node.clusterId = :clusterId")
    int updateNodeStatusByClusterId(@Param("status") String Status, @Param("clusterId")Long clusterId);
}
