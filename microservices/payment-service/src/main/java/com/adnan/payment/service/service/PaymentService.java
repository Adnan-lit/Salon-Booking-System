package com.adnan.payment.service.service;

import com.adnan.payment.service.domain.PaymentMethod;
import com.adnan.payment.service.modal.PaymentOrder;
import com.adnan.payment.service.payload.dto.BookingDTO;
import com.adnan.payment.service.payload.dto.UserDTO;
import com.adnan.payment.service.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws StripeException;
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String paymentId);
    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;
    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws Exception;
}
