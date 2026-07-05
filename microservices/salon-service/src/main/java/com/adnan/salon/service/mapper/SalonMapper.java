package com.adnan.salon.service.mapper;

import com.adnan.salon.service.modal.Salon;
import com.adnan.salon.service.payload.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapSalonToDTO(Salon salon) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setName(salon.getName());
        salonDTO.setImages(salon.getImages());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setCity(salon.getCity());
        salonDTO.setOwnerId(salon.getOwnerId());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setCloseTime(salon.getCloseTime());
        return salonDTO;
    }
}
