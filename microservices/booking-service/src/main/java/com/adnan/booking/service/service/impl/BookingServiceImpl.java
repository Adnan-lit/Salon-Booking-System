package com.adnan.booking.service.service.impl;

import com.adnan.booking.service.domain.BookingStatus;
import com.adnan.booking.service.dto.BookingRequest;
import com.adnan.booking.service.dto.SalonDTO;
import com.adnan.booking.service.dto.ServiceDTO;
import com.adnan.booking.service.dto.UserDTO;
import com.adnan.booking.service.modal.Booking;
import com.adnan.booking.service.modal.SalonReport;
import com.adnan.booking.service.repository.BookingRepository;
import com.adnan.booking.service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet) throws Exception {
        int totalDuration = serviceDTOSet.stream().mapToInt(ServiceDTO::getDuration).sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salon, bookingStartTime, bookingEndTime);

        int totalPrice = serviceDTOSet.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = serviceDTOSet.stream().map(ServiceDTO::getId).collect(java.util.stream.Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setServiceIds(idList);
        newBooking.setTotalPrice(totalPrice);
        newBooking.setStatus(BookingStatus.PENDING);
        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(
            SalonDTO salonDTO,
            LocalDateTime bookingStartTime,
            LocalDateTime bookingEndTime) throws Exception {

        if (!bookingEndTime.isAfter(bookingStartTime)) {
            throw new Exception("Invalid booking duration.");
        }

        LocalDateTime salonOpenTime =
                salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());

        LocalDateTime salonCloseTime =
                salonDTO.getCloseTime().atDate(bookingStartTime.toLocalDate());

        if (bookingStartTime.isBefore(salonOpenTime)
                || bookingEndTime.isAfter(salonCloseTime)) {

            throw new Exception("Booking time must be within salon operating hours.");
        }

        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        for (Booking booking : existingBookings) {

            if (bookingStartTime.isBefore(booking.getEndTime())
                    && bookingEndTime.isAfter(booking.getStartTime())) {

                throw new Exception("Slot not available, choose a different time.");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new Exception("Booking not found with id: " + id);
        }
        return booking;
    }

    @Override
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<Booking> bookings = bookingRepository.findBySalonId(salonId);
        return bookings.stream()
                .filter(booking -> booking.getStartTime().isAfter(startOfDay) && booking.getStartTime().isBefore(endOfDay))
                .toList();
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = bookingRepository.findBySalonId(salonId);

        int totalEarnings = bookings.stream().mapToInt(Booking::getTotalPrice).sum();

        Integer totalBooking = bookings.size();

        List<Booking> cancelledBookings = bookings.stream().filter(booking -> booking.getStatus() == BookingStatus.CANCELLED).toList();

        Double totalRefund = cancelledBookings.stream().mapToDouble(Booking::getTotalPrice).sum();

        SalonReport report = new SalonReport();
        report.setTotalEarnings(totalEarnings);
        report.setTotalBookings(totalBooking);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalRefund(totalRefund);
        report.setSalonId(salonId);

        return report;
    }
}
