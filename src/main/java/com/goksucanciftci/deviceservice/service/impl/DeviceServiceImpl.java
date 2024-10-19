package com.goksucanciftci.deviceservice.service.impl;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.model.entity.Device;
import com.goksucanciftci.deviceservice.model.mapper.DeviceMapper;
import com.goksucanciftci.deviceservice.repository.DeviceRepository;
import com.goksucanciftci.deviceservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DeviceMapper deviceMapper;
	@Autowired
	DeviceRepository deviceRepository;

	@Override
	@Transactional
	public DeviceDTO save(DeviceDTO deviceDTO) {
		Device device = deviceMapper.asEntity(deviceDTO);
		device = deviceRepository.save(device);
		return deviceMapper.asDTO(device);
	}

	@Override
	@Cacheable(value = "devices", key = "#id")
	public DeviceDTO findById(Long id) {
		try{
			Device device = deviceRepository.findById(String.valueOf(id)).orElseThrow();;
			return deviceMapper.asDTO(device);
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Device not found with id: " + id);
		}
	}

	@Override
	@Cacheable(value = "devices")
	public List<DeviceDTO> findAll() {
		List<Device> devices = deviceRepository.findAll();
		return deviceMapper.asDTOList(devices);
	}

	@Override
	@Transactional
	@CacheEvict(value = "devices", allEntries = true)
	public DeviceDTO update(Long id, DeviceDTO deviceDTO) {
		try{
			Device device = deviceRepository.findById(String.valueOf(id)).orElseThrow();
			device.setName(deviceDTO.getName());
			device.setBrand(deviceDTO.getBrand());
			device = deviceRepository.save(device);
			return deviceMapper.asDTO(device);

		} catch (Exception e) {
			throw new RuntimeException("Failed to update device: " + e.getMessage(), e);
		}
	}

	@Override
	@CacheEvict(value = "devices", allEntries = true)
	public void deleteById(Long id) {
		deviceRepository.deleteById(String.valueOf(id));
	}

	@Override
	@Cacheable (value = "devices", key = "#brand")
	public List<DeviceDTO> findByBrand(String brand) {
		return deviceRepository.findByBrandContainingIgnoreCase(brand);
	}
}
