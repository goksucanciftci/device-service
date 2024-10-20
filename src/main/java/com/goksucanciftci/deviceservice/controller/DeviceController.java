package com.goksucanciftci.deviceservice.controller;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
public class DeviceController {


	private final DeviceService deviceService;

	@PostMapping
	public ResponseEntity<DeviceDTO> create(@Valid @RequestBody DeviceDTO deviceDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.save(deviceDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DeviceDTO> findById(@PathVariable("id") Long id) {
		Optional<DeviceDTO> deviceDTO = deviceService.findById(id);
		return deviceDTO.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping
	public ResponseEntity<List<DeviceDTO>> findAll() {
		return ResponseEntity.ok().body(deviceService.findAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<DeviceDTO> update(@PathVariable("id") Long id, @Valid @RequestBody DeviceDTO deviceDTO) {
		Optional<DeviceDTO> updatedDevice = deviceService.update(id, deviceDTO);
		return updatedDevice.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		deviceService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/brand")
	public ResponseEntity<List<DeviceDTO>> findByBrand(@RequestParam ("brand") String brand) {
		List<DeviceDTO> devices = deviceService.findByBrand(brand);
		return ResponseEntity.status(HttpStatus.OK).body(devices);
	}
}
