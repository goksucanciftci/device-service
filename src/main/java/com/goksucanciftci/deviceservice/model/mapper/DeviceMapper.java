package com.goksucanciftci.deviceservice.model.mapper;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.model.entity.Device;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceMapper {

	public DeviceDTO asDTO(Device device) {
		return DeviceDTO.builder()
				.id(device.getId())
				.name(device.getName())
				.brand(device.getBrand())
				.creationTime(device.getCreationTime())
				.build();
	}

	public Device asEntity(DeviceDTO deviceDTO) {
		return Device.builder()
				.id(deviceDTO.getId())
				.name(deviceDTO.getName())
				.brand(deviceDTO.getBrand())
				.creationTime(deviceDTO.getCreationTime())
				.build();
	}

	public List<Device> asEntityList(List<DeviceDTO> deviceDTOs) {
		return deviceDTOs.stream()
				.map(this::asEntity)
				.collect(Collectors.toList());
	}

	public List<DeviceDTO> asDTOList(List<Device> devices) {
		return devices.stream()
				.map(this::asDTO)
				.collect(Collectors.toList());
	}
}
