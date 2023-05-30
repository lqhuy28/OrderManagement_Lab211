package model;

public class Product {    
    private String productID;
    private String productName;
    private String unit;
    private String origin;
    private double price;

    public Product() {
    }

    public Product(String productID, String productName, String unit, 
        String origin, double price) {
            this.productID = productID;
            this.productName = productName;
            this.unit = unit;
            this.origin = origin;
            this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        return productID + ", " + productName + ", " + unit + ", " +
                origin + ", " + price;
    }
}
