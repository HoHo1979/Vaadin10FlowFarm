package com.iotarch.farm.service;

import com.iotarch.farm.entity.Sensor;
import com.iotarch.farm.repository.SensorRepositoryImp;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@VaadinSessionScope
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


        if(sensor.getSensorId()==""){
            sensor.setSensorId(sensor.getName());
            sensor.setTimeStamp(Instant.now().toEpochMilli());
        }else{
            sensor.setUpdateTimeStamp(Instant.now().toEpochMilli());
        }

        repositoryImp.save(sensor);

    }
}
