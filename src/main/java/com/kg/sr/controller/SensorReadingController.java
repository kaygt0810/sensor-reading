package com.kg.sr.controller;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kg.sr.dto.SensorReadingDto;
import com.kg.sr.model.SensorReading;
import com.kg.sr.model.SensorReadingResponse;
import com.kg.sr.service.SensorReadingService;
import com.kg.sr.utils.DateUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/sensorreading")
public class SensorReadingController {
	@Autowired
	private SensorReadingService sensorReadingService;
	

	@PostMapping(value="/readings", consumes="application/json")
	public ResponseEntity<?> submitReading(@RequestBody SensorReadingDto sensorReadingDto){
		if(sensorReadingDto!=null) {
			if(StringUtils.isEmpty(sensorReadingDto.getDevice_id())) {
				return ResponseEntity.badRequest().body("Device Id cannot be null");
			}
			
			if(sensorReadingDto.getValue() == null || (sensorReadingDto.getValue() != null && sensorReadingDto.getValue().doubleValue() <= 0)) {
				return ResponseEntity.unprocessableEntity().body("Sensor Value must be positive");
			}
			
			if(!StringUtils.isEmpty(sensorReadingDto.getTimestamp())) {
				boolean isFormatCorrect = DateUtils.checkTimestamp(sensorReadingDto.getTimestamp());
				if(!isFormatCorrect) {
					return ResponseEntity.badRequest().body("Timestamp format is not correct , shoul be ISO 8601 format");
				}
			}
		}
		SensorReading sensorReading = new SensorReading();
		sensorReading.setDevice_id(sensorReadingDto.getDevice_id());
		sensorReading.setSensorValue(sensorReadingDto.getValue());
		sensorReading.setTimestamp(Instant.parse(sensorReadingDto.getTimestamp()));
		SensorReading results = sensorReadingService.submitSensorReading(sensorReading);
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}
	
	@GetMapping(value="/getall")
	public List<SensorReading> getSensorReading() {
		return sensorReadingService.getAllSensorReading();
	}
	
	
	@GetMapping(value="/readings")
	public ResponseEntity<?> getReadingByDeviceIdAndDateRange(
			@RequestParam (required=false)String device_id, 
			@RequestParam (required=false)String from, 
			@RequestParam (required=false)String to, 
			@RequestParam(defaultValue = "0")Integer page, 
			@RequestParam(defaultValue = "20") Integer page_size)
	{
		
		 
			if(StringUtils.isEmpty(device_id)) {
				return ResponseEntity.badRequest().body("Device Id cannot be null");
			}
			
			if((page_size!=null)&&(page_size.intValue()>100)) {
				return ResponseEntity.badRequest().body("Malformed query parameters");
			}
			
			if(!StringUtils.isEmpty(from)) {
				boolean isFormatCorrect = DateUtils.checkTimestamp(from);
				if(!isFormatCorrect) {
					return ResponseEntity.badRequest().body("Malformed query parameters");
				}
			}
			
			if(!StringUtils.isEmpty(to)) {
				boolean isFormatCorrect = DateUtils.checkTimestamp(to);
				if(!isFormatCorrect) {
					return ResponseEntity.badRequest().body("Malformed query parameters");
				}
			}
			
		Page<SensorReading> data = null;
		
			if((from==null)||(to==null)) {
				data=sensorReadingService.getSensorReadingByDeviceIdPage(device_id, page, page_size);
			}else {
				data = sensorReadingService.getSensorReadingByPage(device_id, Instant.parse(from), Instant.parse(to), page,page_size);
			}
		
		//set data for the blank object
		SensorReadingResponse sensorReadingResponse = new SensorReadingResponse();
		sensorReadingResponse.setPage_size(page_size);
		sensorReadingResponse.setPage(page);
		sensorReadingResponse.setData(data.getContent());
		sensorReadingResponse.setTotal(data.getTotalElements());
		return ResponseEntity.ok(sensorReadingResponse);
	}	
	
	
}

