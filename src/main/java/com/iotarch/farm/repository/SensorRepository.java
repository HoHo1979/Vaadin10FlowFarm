package com.iotarch.farm.repository;


import com.iotarch.farm.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface SensorRepository /*extends JpaRepository*/{

    List<Sensor> findAll();

    Optional<Sensor> findById(String sensorId);

    Sensor save(Sensor sensor);

}
