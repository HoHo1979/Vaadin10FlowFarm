package com.iotarch.farm.repository;

import com.iotarch.farm.entity.SensorRecord;

import java.util.List;


public interface SensorRecordRepository {

    List<SensorRecord> findAllRecordsBySensorId(String sensorId);

    List<SensorRecord> findAllRecords();

}
