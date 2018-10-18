package com.iotarch.farm.service;

import com.iotarch.farm.entity.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SensorService {

    String addSensor(Sensor sensor);
    void addSensors(List<Sensor> sensorList);
    void updateSensor(Sensor sensor);
    List<Sensor> findAllSensors();
    Sensor findSensorById(String id);
    List<Sensor> findAllSensors(String value);
    void save(Sensor sensor);
}
