package model;

public class Order implements Comparable<Order> {    
    private String orderID;
    private Customer customer;
    private Product product;
    private int orderQuantity;
    private String orderDate;
    private boolean status;
    
    public Order() {
    }
    
    public Order(String orderID, Customer customer, Product product, 
        int orderQuantity, String orderDate, boolean status) {
            this.orderID = orderID;
            this.customer = customer;
            this.product = product;
            this.orderQuantity = orderQuantity;
            this.orderDate = orderDate;
            this.status = status;
    }
    
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }  
   
    @Override
    public String toString() {
        return orderID + ", " + customer + ", " + product + ", " + 
                orderQuantity + ", " + orderDate + ", " + status; 
    }

    @Override
    public int compareTo(Order o) {
        return this.getCustomer().getCustomerName().toUpperCase().compareTo(o.getCustomer().getCustomerName().toUpperCase());
    }
}
