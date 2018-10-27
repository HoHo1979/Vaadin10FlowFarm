package com.iotarch.farm.service;

import com.iotarch.farm.entity.SensorRecord;

import java.util.List;

public interface SensorRecordService {

    List<SensorRecord> findAllRecordsBySensorId(String sensorId);

    List<SensorRecord> findAllRecords();

}
