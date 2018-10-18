package com.iotarch.farm.service;

import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.repository.SensorRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorServiceImp implements SensorService {

    @Autowired
    SensorRepositoryImp repositoryImp;


    @Override
    public String addSensor(Sensor sensor) {
        return repositoryImp.save(sensor).getSensorId();
    }

    @Override
    public void addSensors(List<Sensor> sensorList) {

    }

    @Override
    public void updateSensor(Sensor sensor) {

    }

    @Override
    public List<Sensor> findAllSensors() {
        return repositoryImp.findAll();
    }

    @Override
    public Sensor findSensorById(String id) {
        return (repositoryImp.findById(id).isPresent())?repositoryImp.findById(id).get():null;
    }

    @Override
    public List<Sensor> findAllSensors(String value) {
        List<Sensor> matchSensors;

        if(value!=null) {
            matchSensors = repositoryImp.findAll().stream().filter(s -> s.getName().contains(value)).collect(Collectors.toList());
        }else{
            matchSensors= repositoryImp.findAll();
        }
        return matchSensors;
    }

    @Override
    public void save(Sensor sensor) {

        repositoryImp.save(sensor);

    }
}
