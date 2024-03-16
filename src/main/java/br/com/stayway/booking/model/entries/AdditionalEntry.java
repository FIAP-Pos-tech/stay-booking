package br.com.stayway.booking.model.entries;

public class AdditionalEntry {
    private String id;
    private String quantity;

    public AdditionalEntry(String id, String quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    
}
