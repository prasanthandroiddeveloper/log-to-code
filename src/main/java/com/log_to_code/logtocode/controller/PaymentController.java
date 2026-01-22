package com.log_to_code.logtocode.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    // Simulate payment for a specific order
    @GetMapping("/pay/{orderId}")
    public String makePayment(@PathVariable String orderId) {
        try {
            // Simulate an error for demonstration
            if (orderId.startsWith("x")) {
                throw new RuntimeException("Payment service failed for order: " + orderId);
            }

            // Normal processing
            logger.info("Payment processed successfully for order: {}", orderId);

        } catch (Exception e) {
            // Log the exception
            logger.error("Exception occurred: {}", e.getMessage());
            // The build or app does not fail because we handle the exception
        }

        return "Payment attempt completed for order: " + orderId;
    }
}
