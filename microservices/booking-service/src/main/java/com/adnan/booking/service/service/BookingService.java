package com.adnan.booking.service.service;

import com.adnan.booking.service.domain.BookingStatus;
import com.adnan.booking.service.dto.BookingRequest;
import com.adnan.booking.service.dto.SalonDTO;
import com.adnan.booking.service.dto.ServiceDTO;
import com.adnan.booking.service.dto.UserDTO;
import com.adnan.booking.service.modal.Booking;
import com.adnan.booking.service.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception;
    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBookingStatus(Long bookingId, BookingStatus status) throws  Exception;
    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);
}
