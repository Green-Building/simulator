package com.example.demo.repository;

import com.example.demo.SimulatingStructure.Node;
import com.example.demo.SimulatingStructure.Sensor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.cluster_id = :cluster_id")
    void deleteSensorByClusterId(@Param("cluster_id")Long cluster_id);

    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.node_id = :node_id")
    void deleteSensorByNodeId(@Param("node_id")Long node_id);

    @Transactional
    @Modifying
    @Query("UPDATE Sensor sensor SET sensor.status = :status where sensor.cluster_id = :cluster_id")
    int updateSensorStatusByClusterId(@Param("status") String Status, @Param("cluster_id")Long cluster_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.cluster_id = :cluster_id")
    List<Sensor> findSensorByClusterId(@Param("cluster_id")Long cluster_id);

    @Transactional
    @Query("select sensor from Sensor sensor where sensor.node_id = :node_id")
    List<Sensor> findSensorByNodeId(@Param("node_id")Long node_id);
}