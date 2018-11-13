package com.example.demo.repository;

import com.example.demo.Infrastructure.Room;
import com.example.demo.SimulatingStructure.Node;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface NodeRepository extends CrudRepository <Node, Long> {
    @Transactional
    @Modifying
    @Query("delete from Node node where node.cluster_id = :cluster_id")
    void deleteNodeByClusterId(@Param("cluster_id")Long cluster_id);

    @Transactional
    @Modifying
    @Query("UPDATE Node node SET node.status = :status where node.cluster_id = :cluster_id")
    int updateNodeStatusByClusterId(@Param("status") String Status, @Param("cluster_id")Long cluster_id);

    @Transactional
    @Query("select node from Node node where node.cluster_id = :cluster_id")
    List<Node> findNodeByClusterId(@Param("cluster_id")Long cluster_id);

    @Transactional
    @Query("select node from Node node where node.room_id = :room_id")
    Node findNodeByRoomId(@Param("room_id")long room_id);
}
