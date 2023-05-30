package tools;

import tools.Inputter;
import model.Customer;
import model.Product;
import model.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class OrderList extends ArrayList<Order> {
    
    ProductList plist;
    CustomerList clist;
    String filename="src\\data\\Orders.txt";
    
    public OrderList() {
        super();
        LoadFromFile(filename);
    }
          
    public void LoadFromFile(String fName) {       
        try {
            File f = new File(fName); 
            if(!f.exists()) return;
            FileReader fr = new FileReader(fName);
            BufferedReader bf = new BufferedReader(fr);
            String details; 
            while((details=bf.readLine())!=null) {
                StringTokenizer stk = new StringTokenizer(details,",");
                String orderID = stk.nextToken().trim().toUpperCase();                              
                String customerID = stk.nextToken().trim().toUpperCase();
                String productID = stk.nextToken().trim().toUpperCase();
                int orderQuantity = Integer.parseInt(stk.nextToken());
                String orderDate = stk.nextToken().trim().toUpperCase();
                boolean status = Boolean.parseBoolean(stk.nextToken());            
            
                clist = new CustomerList();
                plist = new ProductList();
                Customer customer = clist.searchCustomerID(customerID); 
                Product product = plist.searchProductID(productID); 
                Order order = new Order(orderID,customer,product,
                        orderQuantity,orderDate,status);     
                this.add(order);
            }           
            bf.close(); fr.close();
        } catch (Exception e) {
//            System.out.println(e);
        }
    }
    
    public void saveToFile(String fName) {
        if(this.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        try {
            File f = new File(fName);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for(Order or : this) {
                pw.println(or.getOrderID() + "," + 
                        or.getCustomer().getCustomerID() + "," + 
                        or.getProduct().getProductID() + "," + 
                        or.getOrderQuantity() + "," + 
                        or.getOrderDate() + "," + 
                        or.isStatus());
            }
            pw.close(); fw.close();  
            System.out.println("The order list has been saved to the file.");
        }
        catch(Exception e) {
//            System.out.println(e);
        }
    }
    
    public void printAllOrders() {
        if(this.isEmpty()) System.out.println("Empty list!");
        else {
            Collections.sort(this);
            System.out.println("---------- Order List ----------");
            for (Order order : this) System.out.println(order);
            System.out.println("Total: " + this.size() + " order(s).");
        }
    }
    
    public void getPendingOrderList() {
        for (Order order : this) {
            if (order.isStatus()==false) 
                System.out.println(order);
        }  
    }
    
    public Order search(String code) {
        code = code.trim().toUpperCase();
        for (int i=0; i<this.size(); i++) {
            if(this.get(i).getOrderID().equals(code))
                return this.get(i);          
        }
        return null;
    }
    
    public void printByProductName() {
        if(this.isEmpty()) 
            System.out.println("Empty list!");   
        else {
            String bCode = Inputter.inputStr("Input product name: ");
            System.out.println("----------- Order List ----------");
            for (Order order : this)
                if(order.getProduct().getProductName().equals(bCode)) 
                    System.out.println(order);
        }
    }
    
    private boolean isCodeDupplicated(String code) {
        code = code.trim().toUpperCase();
        return search(code)!=null;
    }

    public void addOrder() {
    String newOrderID, newOrderDate;
    int newOrderQuantity;
    boolean newStatus;
    boolean isCodeDuplicated;

    do {
        newOrderID = Inputter.inputStr("Input new order code: ");
        isCodeDuplicated = isCodeDuplicated(newOrderID);
        if (isCodeDuplicated) {
            System.out.println("Order code is duplicated!");
        }
    } while (isCodeDuplicated);

    CustomerList customerList = new CustomerList();
    Customer customer = customerList.getUserChoice();

    ProductList productList = new ProductList();
    Product product = productList.getUserChoice();

    newOrderQuantity = Inputter.inputInt("Quantity: ");

    newOrderDate = Inputter.inputNonBlankStr("Order Date: ").toUpperCase();

    newStatus = false;

    Order order = new Order(newOrderID, customer, product, newOrderQuantity, newOrderDate, newStatus);
    add(order);
    System.out.println("Order " + newOrderID + " has been added.");
    saveToFile(filename);
}
    
    public void updateOrder() {
        if(this.isEmpty()) {
            System.out.println("Empty list. No update can be performed!");
        } else {
            String uCode = Inputter.inputStr("Input code of updated order: ");
            Order order = this.search(uCode);
            if(order==null) {
                System.out.println("Order " + uCode + " doesn't existed!");
            } else {
                String msg = "Old Status: " + order.isStatus() + ", new Status: ";
                boolean newStatus = Inputter.inputBoolean(msg);
                order.setStatus(newStatus);
                System.out.println("Order " + uCode + " has been updated.");
            }
        }
    }
    
    public void removeOrder() {
        if(this.isEmpty()) {
            System.out.println("Empty list. No remove can be performed!");
        } else {
            String rCode = Inputter.inputStr("Input code of removed order: ");
            Order od = this.search(rCode);
            if(od==null)
                System.out.println("Order " + rCode + " doesn't existed!");
            else {
                this.remove(od);
                System.out.println("Order " + rCode + " has been removed.");
            }
        }
        this.saveToFile(filename);
    }
    
    public void LoadAgainCustomer(CustomerList t){
        if(!t.isEmpty()){
        for(int i=0; i<this.size(); i++) {
            this.get(i).setCustomer(t.search(this.get(i).getCustomer().getCustomerID()));
        }
        this.saveToFile(filename);
        }
    }

    private boolean isCodeDuplicated(String newOrderID) {
        for (Order order : this) {
        if (order.getOrderID().equals(newOrderID)) {
            return true; 
        }
    }
    return false; 
    }
    
}
