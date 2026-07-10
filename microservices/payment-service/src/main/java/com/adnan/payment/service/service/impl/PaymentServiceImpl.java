package com.adnan.payment.service.service.impl;

import com.adnan.payment.service.domain.PaymentMethod;
import com.adnan.payment.service.domain.PaymentOrderStatus;
import com.adnan.payment.service.modal.PaymentOrder;
import com.adnan.payment.service.payload.dto.BookingDTO;
import com.adnan.payment.service.payload.dto.UserDTO;
import com.adnan.payment.service.payload.response.PaymentLinkResponse;
import com.adnan.payment.service.repository.PaymentOrderRepository;
import com.adnan.payment.service.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api-key}")
    private String stripeSecreteKey;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws StripeException {
        Long amount = (long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse response = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.STRIPE)) {
            String paymentUrl = createStripePaymentLink(user, savedOrder.getAmount(), savedOrder.getId());

            response.setPayment_link_url(paymentUrl);
        } else if(paymentMethod.equals(PaymentMethod.CASH)) {
            response.setPayment_link_url("Cash payment selected. Please pay at the salon.");
        } else {
            response.setPayment_link_url("Unsupported payment method.");
        }

        return response;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder == null) {
            throw new Exception("Payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey = stripeSecreteKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success?orderId=" + orderId)
                .setCancelUrl("http://localhost:8080/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("bdt")
                                .setUnitAmount(amount)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Salon appointment booking")
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws Exception {
        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.STRIPE)){
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }else if(paymentOrder.getPaymentMethod().equals(PaymentMethod.CASH)){
                paymentOrder.setStatus(PaymentOrderStatus.PENDING);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
        }
        return false;
    }
}
