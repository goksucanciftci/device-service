package com.goksucanciftci.deviceservice.controller;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DeviceControllerTest {

	@InjectMocks
	private DeviceController deviceController;

	@Mock
	private DeviceService deviceService;

	private DeviceDTO mockDeviceDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockDeviceDTO = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.creationTime(LocalDateTime.now())
				.version(1)
				.build();
	}

	@Test
	public void testCreate() {
		when(deviceService.save(any(DeviceDTO.class))).thenReturn(mockDeviceDTO);

		ResponseEntity<DeviceDTO> response = deviceController.create(mockDeviceDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1L, response.getBody().getId());
		assertEquals("BrandX", response.getBody().getBrand());
	}

	@Test
	public void testFindById_Success() {
		when(deviceService.findById(1L)).thenReturn(java.util.Optional.of(mockDeviceDTO));

		ResponseEntity<DeviceDTO> response = deviceController.findById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1L, response.getBody().getId());
		assertEquals("BrandX", response.getBody().getBrand());
	}

	@Test
	public void testFindById_NotFound() {
		when(deviceService.findById(1L)).thenReturn(java.util.Optional.empty());

		ResponseEntity<DeviceDTO> response = deviceController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testFindAll() {
		DeviceDTO deviceDTO2 = DeviceDTO.builder()
				.id(2L)
				.name("Device2")
				.brand("BrandY")
				.build();

		when(deviceService.findAll()).thenReturn(Arrays.asList(mockDeviceDTO, deviceDTO2));

		ResponseEntity<List<DeviceDTO>> response = deviceController.findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	public void testUpdate_Success() {
		when(deviceService.update(eq(1L), any(DeviceDTO.class))).thenReturn(java.util.Optional.of(mockDeviceDTO));

		ResponseEntity<DeviceDTO> response = deviceController.update(1L, mockDeviceDTO);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(1L, response.getBody().getId());
	}

	@Test
	public void testUpdate_NotFound() {
		when(deviceService.update(eq(1L), any(DeviceDTO.class))).thenReturn(java.util.Optional.empty());

		ResponseEntity<DeviceDTO> response = deviceController.update(1L, mockDeviceDTO);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDelete() {
		doNothing().when(deviceService).deleteById(1L);

		ResponseEntity<Void> response = deviceController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(deviceService, times(1)).deleteById(1L);
	}

	@Test
	public void testFindByBrand_Success() {
		when(deviceService.findByBrand("BrandX")).thenReturn(Collections.singletonList(mockDeviceDTO));

		ResponseEntity<List<DeviceDTO>> response = deviceController.findByBrand("BrandX");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	public void testFindByBrand_NotFound() {
		when(deviceService.findByBrand("BrandX")).thenReturn(Collections.emptyList());

		ResponseEntity<List<DeviceDTO>> response = deviceController.findByBrand("BrandX");

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertNotNull(response.getBody());
		assertEquals(0, response.getBody().size());
	}
}
