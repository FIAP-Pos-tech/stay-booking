package br.com.stayway.booking.model;

import java.util.ArrayList;
import java.util.List;

import br.com.stayway.booking.model.entries.ReceiptItem;

public class ReservationReceipt {
    private double finalValue;
    private List<ReceiptItem> items;

    public ReservationReceipt() {
        this.finalValue = 0;
        this.items = new ArrayList<>();
    }

    public double getFinalValue() {
        return finalValue;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void addItem(ReceiptItem item) {
        this.items.add(item);
        this.finalValue = this.finalValue + item.totalValue();
    }
    
}
