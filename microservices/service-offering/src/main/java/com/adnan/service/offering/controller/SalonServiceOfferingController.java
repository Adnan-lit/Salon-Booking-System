package com.adnan.service.offering.controller;

import com.adnan.service.offering.dto.CategoryDTO;
import com.adnan.service.offering.dto.SalonDTO;
import com.adnan.service.offering.dto.ServiceDTO;
import com.adnan.service.offering.modal.ServiceOffering;
import com.adnan.service.offering.service.impl.ServiceOfferingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    @Autowired
    private ServiceOfferingServiceImpl serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createServiceOffering(@RequestBody ServiceDTO  serviceDTO) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L); // Replace with actual salon ID from authenticated user context

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering serviceOffering = serviceOfferingService.createService(salonDTO, serviceDTO, categoryDTO);
        return ResponseEntity.ok(serviceOffering);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateServiceOffering(@PathVariable Long id, @RequestBody ServiceOffering serviceOffering) throws Exception {

        ServiceOffering serviceOfferings = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }
}
