package com.goksucanciftci.deviceservice.controller;

import com.goksucanciftci.deviceservice.model.dto.DeviceDTO;
import com.goksucanciftci.deviceservice.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {

	@Autowired
	DeviceService deviceService;

	@PostMapping
	public ResponseEntity<DeviceDTO> create(@Valid @RequestBody DeviceDTO deviceDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.save(deviceDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DeviceDTO> findById(@PathVariable ("id") Long id) {
		try{
			return ResponseEntity.ok().body(deviceService.findById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<DeviceDTO>> findAll() {
		try{
			return ResponseEntity.ok().body(deviceService.findAll());
		} catch (RuntimeException e) {
			return  ResponseEntity.notFound().build();
		}
	}

	@PutMapping ("/{id}")
	public ResponseEntity<DeviceDTO> update(@PathVariable ("id") Long id,  @Valid @RequestBody DeviceDTO deviceDTO) {
		try{
			return ResponseEntity.status(HttpStatus.OK).body(deviceService.update(id, deviceDTO));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable ("id") Long id) {
		deviceService.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@GetMapping("/brand")
	public ResponseEntity<List<DeviceDTO>> findByBrand(@RequestParam ("brand") String brand) {
		try {
			List<DeviceDTO> devices = deviceService.findByBrand(brand);
			if (devices.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.status(HttpStatus.OK).body(devices);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
