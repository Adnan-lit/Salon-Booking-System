package com.adnan.payment.service.controller;

import com.adnan.payment.service.domain.PaymentMethod;
import com.adnan.payment.service.modal.PaymentOrder;
import com.adnan.payment.service.payload.dto.BookingDTO;
import com.adnan.payment.service.payload.dto.UserDTO;
import com.adnan.payment.service.payload.response.PaymentLinkResponse;
import com.adnan.payment.service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod) throws Exception {
        UserDTO user = new UserDTO();
        user.setFullName("Adnan");
        user.setEmail("adnan@example.com");
        user.setId(1L);
        // Set other user properties as needed

        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);
        return ResponseEntity.ok(res);
    }


    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(paymentOrder);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<PaymentOrder> proceedPayment(@RequestParam String paymentId, @RequestParam String paymentLinkId) throws Exception {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);
        if (paymentOrder != null) {
            boolean success = paymentService.proceedPayment(paymentOrder, paymentId, paymentLinkId);
            if (success) {
                return ResponseEntity.ok(paymentOrder);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
