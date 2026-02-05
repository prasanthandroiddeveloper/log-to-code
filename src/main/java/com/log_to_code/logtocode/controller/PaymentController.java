package com.log_to_code.logtocode.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {



    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    // ← Add this line here
    private static final String COMMIT_SHA = System.getenv("COMMIT_SHA") != null ? System.getenv("COMMIT_SHA") : "unknown";





    @GetMapping("/pay/{orderId}")
    public String makePayment(@PathVariable String orderId) {
        try {
            if (orderId.startsWith("x")) {
                throw new RuntimeException("Payment service failed due to insufficient balance");
            }
            logger.info("Payment successful | {\"level\":\"INFO\",\"commit_sha\":\"{}\",\"order_id\":\"{}\",\"message\":\"Payment successful\"}",
                    COMMIT_SHA, orderId);
        } catch (Exception e) {
            logger.error("Payment failed due to insufficient balance | {\"level\":\"ERROR\",\"commit_sha\":\"{}\",\"order_id\":\"{}\",\"message\":\"{}\"}",
                    COMMIT_SHA, orderId, e.getMessage());
        }
        return "Payment attempted for order: " + orderId;
    }
}
