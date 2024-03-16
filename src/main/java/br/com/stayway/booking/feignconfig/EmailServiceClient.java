package br.com.stayway.booking.feignconfig;

import feign.Headers;
import feign.RequestLine;



public interface EmailServiceClient {
    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/email/send")
    void sendEmail(EmailRequest emailRequest);
}
