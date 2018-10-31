package com.example.demo.repository;

import com.example.demo.model.Sensor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SensorRepository extends CrudRepository<Sensor, Long> {
    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.clusterId = :clusterId")
    void deleteSensorByClusterId(@Param("clusterId")Long clusterId);

    @Transactional
    @Modifying
    @Query("delete from Sensor sensor where sensor.nodeId = :nodeId")
    void deleteSensorByNodeId(@Param("nodeId")Long nodeId);

    @Transactional
    @Modifying
    @Query("UPDATE Sensor sensor SET sensor.status = :status where sensor.clusterId = :clusterId")
    int updateSensorStatusByClusterId(@Param("status") String Status, @Param("clusterId")Long clusterId);

}