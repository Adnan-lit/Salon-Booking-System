package com.adnan.salon.service.controller;

import com.adnan.salon.service.mapper.SalonMapper;
import com.adnan.salon.service.modal.Salon;
import com.adnan.salon.service.payload.dto.SalonDTO;
import com.adnan.salon.service.payload.dto.UserDTO;
import com.adnan.salon.service.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salons")
public class SalonController {

    @Autowired
    private SalonService salonService;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapSalonToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalonDTO> updateSalon(
            @RequestBody SalonDTO salonDTO,
            @PathVariable("id") Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon updatedSalon = salonService.updateSalon(salonDTO, userDTO, salonId);
        SalonDTO updatedSalonDTO = SalonMapper.mapSalonToDTO(updatedSalon);
        return ResponseEntity.ok(updatedSalonDTO);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        List<Salon> salons = salonService.getAllSalons();
        List<SalonDTO> salonDTOs = salons.stream().map(SalonMapper::mapSalonToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(salonDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonDTO> getSalon(@PathVariable("id") Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.getSalonById(salonId);
        SalonDTO salonDTO = SalonMapper.mapSalonToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(@RequestParam("city") String city) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        List<Salon> salons = salonService.searchSalonByCity(city);
        List<SalonDTO> salonDTOs = salons.stream().map(SalonMapper::mapSalonToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(salonDTOs);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<SalonDTO> getSalonOwner(@PathVariable("id") Long salonId) throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.getSalonByOwnerId(userDTO.getId());
        SalonDTO salonDTO = SalonMapper.mapSalonToDTO(salon);
        return ResponseEntity.ok(salonDTO);
    }

}
