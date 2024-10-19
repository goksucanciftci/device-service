package com.goksucanciftci.deviceservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {

	private long id;
	private String name;
	private String brand;
	private LocalDateTime creationTime;

}
