package com.example.demo.repository;

import com.example.demo.model.Cluster;

import com.example.demo.model.SensorData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClusterRepository extends CrudRepository<Cluster, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Cluster cluster SET cluster.status = :status where cluster.id = :id")
    int updateClusterStatusById(@Param("status") String Status, @Param("id")Long id);

}
