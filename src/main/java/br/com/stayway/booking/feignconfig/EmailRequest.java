package br.com.stayway.booking.feignconfig;

public record EmailRequest(String to, String subject, String body) {
}
