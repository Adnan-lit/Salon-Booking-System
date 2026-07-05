package com.adnan.salon.service.service.impl;

import com.adnan.salon.service.modal.Salon;
import com.adnan.salon.service.payload.dto.SalonDTO;
import com.adnan.salon.service.payload.dto.UserDTO;
import com.adnan.salon.service.repository.SalonRepository;
import com.adnan.salon.service.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonServiceImpl implements SalonService {

    @Autowired
    private SalonRepository salonRepository;

    @Override
    public Salon createSalon(SalonDTO salon, UserDTO user) {
        Salon newSalon = new Salon();
        newSalon.setName(salon.getName());
        newSalon.setCity(salon.getCity());
        newSalon.setAddress(salon.getAddress());
        newSalon.setEmail(salon.getEmail());
        newSalon.setImages(salon.getImages());
        newSalon.setOwnerId(user.getId());
        newSalon.setOpenTime(salon.getOpenTime());
        newSalon.setCloseTime(salon.getCloseTime());
        newSalon.setPhoneNumber(salon.getPhoneNumber());
        return salonRepository.save(newSalon);
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception {
        Salon existingSalon = salonRepository.findById(salonId).orElse(null);
        if (!salonId.equals(user.getId())){
            throw new Exception("You are not authorized to update this salon");
        }
        if (existingSalon != null ) {
            if (existingSalon.getName() != null) {
                existingSalon.setName(salon.getName());
            }
            if (existingSalon.getCity() != null) {
                existingSalon.setCity(salon.getCity());
            }
            if (existingSalon.getAddress() != null) {
                existingSalon.setAddress(salon.getAddress());
            }
            if (existingSalon.getEmail() != null) {
                existingSalon.setEmail(salon.getEmail());
            }
            if (existingSalon.getImages() != null) {
                existingSalon.setImages(salon.getImages());
            }
            if (existingSalon.getOpenTime() != null) {
                existingSalon.setOpenTime(salon.getOpenTime());
            }
            if (existingSalon.getCloseTime() != null) {
                existingSalon.setCloseTime(salon.getCloseTime());
            }
            if (existingSalon.getPhoneNumber() != null) {
                existingSalon.setPhoneNumber(salon.getPhoneNumber());
            }
            return salonRepository.save(existingSalon);
        }
        throw new Exception("Salon not exist with id: " + salonId);
    }

    @Override
    public List<Salon> getAllSalons() {
        return salonRepository.findAll();
    }

    @Override
    public Salon getSalonById(Long salonId) throws Exception {
        Salon salon = salonRepository.findById(salonId).orElse(null);
        if (salon == null) {
            throw new Exception("Salon not found with id: " + salonId);
        }
        return salon;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) throws Exception {
        Salon salon = salonRepository.findByOwnerId(ownerId);
        if (salon == null) {
            throw new Exception("Salon not found with owner id: " + ownerId);
        }
        return salon;
    }

    @Override
    public List<Salon> searchSalonByCity(String city) {
        return salonRepository.searchSalons(city);
    }
}
