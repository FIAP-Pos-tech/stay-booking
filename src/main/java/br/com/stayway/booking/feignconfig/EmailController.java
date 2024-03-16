package br.com.stayway.booking.feignconfig;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailServiceClientImpl emailServiceClient;

    public EmailController() {
        this.emailServiceClient = new EmailServiceClientImpl() ;
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest request) {
        emailServiceClient.sendEmail(request);
    }
}
