package com.adnan.service.offering.service;

import com.adnan.service.offering.dto.CategoryDTO;
import com.adnan.service.offering.dto.SalonDTO;
import com.adnan.service.offering.dto.ServiceDTO;
import com.adnan.service.offering.modal.ServiceOffering;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
