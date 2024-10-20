package com.goksucanciftci.deviceservice.service;


import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.model.entity.Device;
import com.goksucanciftci.deviceservice.model.mapper.DeviceMapper;
import com.goksucanciftci.deviceservice.repository.DeviceRepository;
import com.goksucanciftci.deviceservice.service.impl.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeviceServiceImplTest {

	@InjectMocks
	private DeviceServiceImpl deviceService;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private DeviceMapper deviceMapper;

	private Device mockDevice;
	private DeviceDTO mockDeviceDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockDevice = Device.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.creationTime(LocalDateTime.now())
				.version(1)
				.build();

		mockDeviceDTO = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.version(1)
				.build();
	}

	@Test
	public void testSave() {
		when(deviceMapper.asEntity(any(DeviceDTO.class))).thenReturn(mockDevice);
		when(deviceRepository.save(any(Device.class))).thenReturn(mockDevice);
		when(deviceMapper.asDTO(any(Device.class))).thenReturn(mockDeviceDTO);

		DeviceDTO savedDevice = deviceService.save(mockDeviceDTO);

		assertNotNull(savedDevice);
		assertEquals(mockDeviceDTO.getId(), savedDevice.getId());
		verify(deviceRepository, times(1)).save(any(Device.class));
	}

	@Test
	public void testFindById_Success() {
		when(deviceRepository.findById("1")).thenReturn(Optional.of(mockDevice));
		when(deviceMapper.asDTO(any(Device.class))).thenReturn(mockDeviceDTO);

		Optional<DeviceDTO> result = deviceService.findById(1L);

		assertTrue(result.isPresent());
		assertEquals("Device1", result.get().getName());
		verify(deviceRepository, times(1)).findById("1");
	}

	@Test
	public void testFindById_NotFound() {
		when(deviceRepository.findById("1")).thenReturn(Optional.empty());

		Optional<DeviceDTO> result = deviceService.findById(1L);

		assertFalse(result.isPresent());
		verify(deviceRepository, times(1)).findById("1");
	}

	@Test
	public void testFindAll() {
		when(deviceRepository.findAll()).thenReturn(Collections.singletonList(mockDevice));
		when(deviceMapper.asDTOList(anyList())).thenReturn(Collections.singletonList(mockDeviceDTO));

		List<DeviceDTO> result = deviceService.findAll();

		assertEquals(1, result.size());
		assertEquals("Device1", result.get(0).getName());
		verify(deviceRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate_Success() {
		Long deviceId = 1L;
		DeviceDTO deviceDTO = DeviceDTO.builder()
				.name("Updated Device")
				.brand("Updated Brand")
				.build();

		Device existingDevice = Device.builder()
				.name("Old Device")
				.brand("Old Brand")
				.build();

		Device updatedDevice = Device.builder()
				.name(deviceDTO.getName())
				.brand(deviceDTO.getBrand())
				.build();

		when(deviceRepository.findById(String.valueOf(deviceId))).thenReturn(Optional.of(existingDevice));

		when(deviceRepository.save(existingDevice)).thenReturn(updatedDevice);

		DeviceDTO updatedDeviceDTO = DeviceDTO.builder()
				.name(updatedDevice.getName())
				.brand(updatedDevice.getBrand())
				.build();
		when(deviceMapper.asDTO(updatedDevice)).thenReturn(updatedDeviceDTO);

		Optional<DeviceDTO> result = deviceService.update(deviceId, deviceDTO);

		assertTrue(result.isPresent());
		assertEquals(deviceDTO.getName(), result.get().getName());
		assertEquals(deviceDTO.getBrand(), result.get().getBrand());

		verify(deviceRepository, times(1)).save(existingDevice);
	}

	@Test
	public void testUpdate_NotFound() {
		when(deviceRepository.findById("1")).thenReturn(Optional.empty());

		Optional<DeviceDTO> result = deviceService.update(1L, mockDeviceDTO);

		assertFalse(result.isPresent());
		verify(deviceRepository, times(1)).findById("1");
	}

	@Test
	public void testDelete() {
		when(deviceRepository.findById("1")).thenReturn(Optional.of(mockDevice));
		doNothing().when(deviceRepository).delete(any(Device.class));

		deviceService.deleteById(1L);

		verify(deviceRepository, times(1)).delete(any(Device.class));
	}

	@Test
	public void testFindByBrand_Success() {
		when(deviceRepository.findByBrandContainingIgnoreCase("BrandX"))
				.thenReturn(Collections.singletonList(mockDevice));
		when(deviceMapper.asDTOList(anyList())).thenReturn(Collections.singletonList(mockDeviceDTO));

		List<DeviceDTO> result = deviceService.findByBrand("BrandX");

		assertEquals(1, result.size());
		assertEquals("Device1", result.get(0).getName());

		verify(deviceRepository, times(1)).findByBrandContainingIgnoreCase("BrandX");
	}
}