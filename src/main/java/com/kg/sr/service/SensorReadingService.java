package com.kg.sr.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kg.sr.model.SensorReading;
import com.kg.sr.model.SensorReadingResponse;
import com.kg.sr.repository.SensorReadingRepository;

@Service
public class SensorReadingService {
	@Autowired
	public SensorReadingRepository sensorReadingRepository;
	
	
	public SensorReading submitSensorReading(SensorReading sensorReading) {
		return sensorReadingRepository.save(sensorReading);
	}
	

	public Page<SensorReading> getSensorReadingByPage(String deviceId, Instant from, Instant to, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize); //class provided by spring that implement pageable/real implementation of pageable 
		Page<SensorReading> data = sensorReadingRepository.findReadingByDeviceIdAndTimestampBetween(deviceId, from, to, pageable);

		return data;
	}
	
	public Page<SensorReading> getSensorReadingByDeviceIdPage(String deviceId, Integer pageNumber, Integer pageSize){
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<SensorReading> data = sensorReadingRepository.findReadingByDeviceId(deviceId, pageable);
		return data;
	}
	
	
	public List<SensorReading> getAllSensorReading(){
		return sensorReadingRepository.findAll();
	}
	
	
}
