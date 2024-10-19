package com.goksucanciftci.deviceservice.service;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {

	DeviceDTO save(DeviceDTO deviceDTO);

	DeviceDTO findById(Long id);

	List<DeviceDTO> findAll();

	DeviceDTO update(Long id, DeviceDTO deviceDTO);

	void deleteById(Long id);

	List<DeviceDTO> findByBrand(String brand);
}
