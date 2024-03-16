package br.com.stayway.booking.feignconfig;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class EmailServiceClientImpl implements EmailServiceClient{

    public  EmailServiceClient createClient() {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return Feign.builder()
                .decoder(new JacksonDecoder(objectMapper))
                .encoder(new JacksonEncoder())
                .target(EmailServiceClient.class, "http://localhost:8040");
    }
    @Override
    public void sendEmail(EmailRequest emailRequest) {
        createClient().sendEmail(emailRequest);
    }
}
