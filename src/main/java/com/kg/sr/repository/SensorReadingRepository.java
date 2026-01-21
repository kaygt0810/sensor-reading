package com.kg.sr.repository;


import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kg.sr.model.SensorReading;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Integer>{
	public Page<SensorReading> findReadingByDeviceId(String deviceId, Pageable pageable);
	public Page<SensorReading> findReadingByDeviceIdAndTimestampBetween(String deviceId, Instant from, Instant to, Pageable pageable);
}
