package org.mobiquityinc.model;


import java.math.BigDecimal;

public class Thing {

    private final int index;
    private final BigDecimal weight;
    private final BigDecimal price;

    public Thing(int index, BigDecimal price, BigDecimal weight) {
        this.index = index;
        this.price = price;
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public String toString() {
        return "(" + index + "," + weight + "," + price + ")";
    }
}
