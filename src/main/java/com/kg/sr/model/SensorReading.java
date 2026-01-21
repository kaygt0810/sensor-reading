package com.kg.sr.model;

import java.time.Instant;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name="T_SENSOR_READING")
//@JsonIgnoreProperties({"readingId"})
public class SensorReading {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@JsonProperty(access=JsonProperty.Access.READ_ONLY)
	private Integer readingId;
	
	
	@JsonProperty("device_id")
	@Column(name="DEVICE_ID")
	@NotBlank(message="The Device id must not be blank")
	private String deviceId;
	
	@Column(name="READING_TIMESTAMP")
	@NotNull(message = "Timestamp is required")

	private Instant timestamp;
	
	
	@JsonProperty("value")
	@Column(name="SENSOR_VALUE")
	@NotNull(message="Value must not be null")
	@Positive(message = "Sensor value must be bigger than 0")
	private Double sensorValue;
	
	@JsonIgnore
	public Integer getReading_id() {
		return readingId;
	}	
	public void setReading_id(Integer readingId) {
		this.readingId = readingId;
	}
	public String getDevice_id() {
		return deviceId;
	}
	public void setDevice_id(String deviceId) {
		this.deviceId = deviceId;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public Double getSensorValue() {
		return sensorValue;
	}
	public void setSensorValue(Double sensorValue) {
		this.sensorValue = sensorValue;
	}
}
