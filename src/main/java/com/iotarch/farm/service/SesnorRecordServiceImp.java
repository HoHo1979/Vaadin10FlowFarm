package com.iotarch.farm.service;

import com.iotarch.farm.entity.SensorRecord;
import com.iotarch.farm.repository.SensorRecordRepositoryImp;
import com.iotarch.farm.repository.SensorRepositoryImp;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@VaadinSessionScope
public class SesnorRecordServiceImp implements SensorRecordService {


    @Autowired
    SensorRecordRepositoryImp sensorRecordRepositoryImp;

    @Override
    public List<SensorRecord> findAllRecordsBySensorId(String sensorId) {
        return sensorRecordRepositoryImp.findAllRecordsBySensorId(sensorId);
    }

    @Override
    public List<SensorRecord> findAllRecords() {
        return sensorRecordRepositoryImp.findAllRecords();
    }
}
