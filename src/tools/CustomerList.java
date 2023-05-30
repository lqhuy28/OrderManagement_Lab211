package tools;

import tools.Menu;
import tools.Inputter;
import model.Customer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CustomerList extends ArrayList<Customer> {    
    String filename = "src\\data\\Customers.txt";
    
    public CustomerList() {
        super();
        this.LoadFromFile(filename);
    }
    
    public void LoadFromFile(String fName) {
        try {
            File f = new File(fName); 
            if(!f.exists()) return;
            FileReader fr = new FileReader(fName);
            BufferedReader bf = new BufferedReader(fr);
            String details; 
            while((details=bf.readLine())!=null) {
                StringTokenizer stk = new StringTokenizer(details,",:");
                String customerID = stk.nextToken().toUpperCase();
                String customerName = stk.nextToken();
                String customerAddress = stk.nextToken().toUpperCase();
                String customerPhone = stk.nextToken().toUpperCase();
//                double price = Double.parseDouble(stk.nextToken());
                Customer cu = new Customer(customerID,customerName,customerAddress,customerPhone);
                this.add(cu);
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
            for(Customer cus : this) {
                pw.println(cus.getCustomerID() + "," + cus.getCustomerName() + "," 
                        + cus.getCustomerAddress() + "," + cus.getCustomerPhone());
            }
            pw.close(); fw.close();  
            System.out.println("The customer list has been saved to the file.");
        }
        catch(Exception e) {
//            System.out.println(e);
        }
    }
    
    public void printAllCustomers() {
        if(this.isEmpty()) System.out.println("Empty list!");
        else {
            System.out.println("---------- Customer List ----------");
            for (Customer cu : this) System.out.println(cu);
            System.out.println("Total: " + this.size() + " customer(s).");
        }
    }
    
    public boolean isCodeDupplicated(String code) {
        code = code.trim().toUpperCase();
        return search(code)!=null;
    }
    
    public Customer search(String code) {
        code = code.trim().toUpperCase();
        for (int i=0; i<this.size(); i++) {
            if(this.get(i).getCustomerID().equals(code))
                return this.get(i);
        }
        return null;
    }
    
    public Customer searchCustomerID(String code) {
        code = code.trim().toUpperCase();
        this.LoadFromFile(filename);
        for (Customer customer : this) {
            if (customer.getCustomerID().trim().equals(code))
                return customer;
        }
        return null;
    }
       
    public void addCustomer() {
        String customerID, customerName, customerAddress, customerPhone;
        boolean codeDupplicated = false;
        do {
            customerID = Inputter.inputStr("Input new customer code: ");
            codeDupplicated = isCodeDupplicated(customerID);
            if(codeDupplicated) System.out.println("Customer code is dupplicated!");          
        } while (codeDupplicated==true);
        customerName = Inputter.inputNonBlankStr("Name of new customer: ");
        customerName = customerName.toUpperCase();
        customerAddress = Inputter.inputNonBlankStr("Address: ");
        customerAddress = customerAddress.toUpperCase();
        //customerPhone = Inputter.inputNonBlankStr("Phone: ");
        customerPhone = Inputter.readPattern("Phone 10/11 digits", "\\d{10}|\\d{11}");
        customerPhone = customerPhone.toUpperCase();
        Customer cus = new Customer(customerID,customerName,customerAddress,customerPhone);
        this.add(cus);
        System.out.println("Customer " + customerID + " has been added.");
        this.saveToFile(filename);
    }
    
    public void searchCustomer() {
        if(this.isEmpty()) {
            System.out.println("Empty list. No search can be performed!");
        } else {
            String sCode = Inputter.inputStr("Input customer code for search: ");
            Customer cus = this.search(sCode);
            if(cus==null)
                System.out.println("Customer " + sCode + " doesn't existed!");
            else
                System.out.println("Found: " + cus);
        }
    }    
    
    public void updateCustomer() {
        if(this.isEmpty()) {
            System.out.println("Empty list. No update can be performed!");
        } else {
            String uCode = Inputter.inputStr("Input code of updated customer: ");
            Customer cus = this.search(uCode);
            if(cus==null) {
                System.out.println("Customer " + uCode + " doesn't existed!");
            } else {
                String oldCustomerName = cus.getCustomerName();
                String msg1 = "Old CustomerName: " + 
                        oldCustomerName + ", new CustomerName: ";
                String newCustomerName = Inputter.inputBlankStr(msg1);
                if(!newCustomerName.isEmpty())cus.setCustomerName(newCustomerName);    
                String oldCustomerAddress = cus.getCustomerAddress();
                String msg2 = "Old CustomerAddress: " + oldCustomerAddress 
                        + ", new CustomerAddress: ";
                String newCustomerAddress = Inputter.inputBlankStr(msg2);
                if(!newCustomerAddress.isEmpty()) cus.setCustomerAddress(newCustomerAddress);              
                String customerPhone = cus.getCustomerPhone();
                String msg3 = "Old CustomerPhone: " + customerPhone 
                        + ", new CustomerPhone: ";
                customerPhone = Inputter.inputNonBlankStr(msg3);
                
                cus.setCustomerPhone(customerPhone);  
                System.out.println("Customer " + uCode + " has been updated.");
            }
        }
        this.saveToFile(filename);
    }
    
    public Customer getUserChoice() {
        int choice = 0;         
        System.out.println("---------- Please select a customer ----------");
        choice = Menu.getChoice(this);
        return this.get(choice-1);
    }   
}