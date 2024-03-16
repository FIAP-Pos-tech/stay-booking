package br.com.stayway.booking.model.entries;

public record ReceiptItem(String description, String type, int quantity, double totalValue) {

}
