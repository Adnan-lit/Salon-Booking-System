package com.adnan.service.offering.service.impl;

import com.adnan.service.offering.dto.CategoryDTO;
import com.adnan.service.offering.dto.SalonDTO;
import com.adnan.service.offering.dto.ServiceDTO;
import com.adnan.service.offering.modal.ServiceOffering;
import com.adnan.service.offering.repository.ServiceOfferingRepository;
import com.adnan.service.offering.service.ServiceOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    @Autowired
    private ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(SalonDTO salonDTO, ServiceDTO serviceDTO, CategoryDTO categoryDTO) {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setImage(serviceDTO.getImage());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception{
        Optional<ServiceOffering> existingService = serviceOfferingRepository.findById(serviceId);
        if (existingService.isEmpty()) {
            throw new Exception("Service not found");
        }
        ServiceOffering serviceToUpdate = existingService.get();
        serviceToUpdate.setName(service.getName());
        serviceToUpdate.setDescription(service.getDescription());
        serviceToUpdate.setPrice(service.getPrice());
        serviceToUpdate.setDuration(service.getDuration());
        serviceToUpdate.setImage(service.getImage());
        return serviceOfferingRepository.save(serviceToUpdate);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if (categoryId != null) {
            services.removeIf(service -> !service.getCategoryId().equals(categoryId));
        }
        return services;

    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }

    @Override
    public ServiceOffering getServiceById(Long id) throws Exception {
        Optional<ServiceOffering> serviceOffering = serviceOfferingRepository.findById(id);
        if (serviceOffering.isEmpty()) {
            throw new Exception("Service not found");
        }
        return serviceOffering.get();
    }


}
