package com.goksucanciftci.deviceservice.controller;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeviceControllerTest {

	@InjectMocks
	private DeviceController deviceController;

	@Mock
	private DeviceService deviceService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreate() {
		DeviceDTO device = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.build();

		DeviceDTO savedDevice = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.creationTime(LocalDateTime.now()) // CreationTime'ı servisin set etmesi
				.build();

		when(deviceService.save(any(DeviceDTO.class))).thenReturn(savedDevice);

		ResponseEntity<DeviceDTO> response = deviceController.create(device);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
		assertEquals("BrandX", response.getBody().getBrand());
		assertNotNull(response.getBody().getCreationTime());
	}

	@Test
	public void testFindById_Success() {
		DeviceDTO device = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.creationTime(LocalDateTime.now())
				.build();

		when(deviceService.findById(1L)).thenReturn(device);

		ResponseEntity<DeviceDTO> response = deviceController.findById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, response.getBody().getId());
		assertEquals("BrandX", response.getBody().getBrand());
		assertNotNull(response.getBody().getCreationTime());
	}

	@Test
	public void testFindById_NotFound() {
		when(deviceService.findById(1L)).thenThrow(new RuntimeException());

		ResponseEntity<DeviceDTO> response = deviceController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	@Test
	public void testFindAll() {
		DeviceDTO device1 = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.creationTime(LocalDateTime.now())
				.build();

		DeviceDTO device2 = DeviceDTO.builder()
				.id(2L)
				.name("Device2")
				.brand("BrandY")
				.creationTime(LocalDateTime.now())
				.build();

		when(deviceService.findAll()).thenReturn(Arrays.asList(device1, device2));

		ResponseEntity<List<DeviceDTO>> response = deviceController.findAll();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
	}

	@Test
	public void testUpdate_Success() {
		DeviceDTO updatedDevice = DeviceDTO.builder()
				.id(1L)
				.name("UpdatedDevice")
				.brand("UpdatedBrand")
				.creationTime(LocalDateTime.now())
				.build();

		when(deviceService.update(eq(1L), any(DeviceDTO.class))).thenReturn(updatedDevice);

		DeviceDTO updateRequest = DeviceDTO.builder()
				.name("UpdatedDevice")
				.brand("UpdatedBrand")
				.build();

		ResponseEntity<DeviceDTO> response = deviceController.update(1L, updateRequest);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("UpdatedDevice", response.getBody().getName());
		assertEquals("UpdatedBrand", response.getBody().getBrand());
		assertNotNull(response.getBody().getCreationTime());
	}

	@Test
	public void testUpdate_NotFound() {
		when(deviceService.update(eq(1L), any(DeviceDTO.class))).thenThrow(new RuntimeException());

		DeviceDTO updateRequest = DeviceDTO.builder()
				.name("UpdatedDevice")
				.brand("UpdatedBrand")
				.build();

		ResponseEntity<DeviceDTO> response = deviceController.update(1L, updateRequest);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void testDelete() {
		doNothing().when(deviceService).deleteById(1L);

		ResponseEntity<Void> response = deviceController.delete(1L);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		verify(deviceService, times(1)).deleteById(1L);
	}

	@Test
	public void testFindByBrand_Success() {
		DeviceDTO device = DeviceDTO.builder()
				.id(1L)
				.name("Device1")
				.brand("BrandX")
				.creationTime(LocalDateTime.now())
				.build();

		when(deviceService.findByBrand("BrandX")).thenReturn(Collections.singletonList(device));

		ResponseEntity<List<DeviceDTO>> response = deviceController.findByBrand("BrandX");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
	}

	@Test
	public void testFindByBrand_NotFound() {
		when(deviceService.findByBrand("BrandX")).thenReturn(Collections.emptyList());

		ResponseEntity<List<DeviceDTO>> response = deviceController.findByBrand("BrandX");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
