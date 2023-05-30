package tools;

import tools.Menu;
import tools.Inputter;
import model.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
  import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductList extends ArrayList<Product> {
    String filename = "src\\data\\Products.txt";
    
    public ProductList() {
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
                String productID = stk.nextToken().toUpperCase();
                String productName = stk.nextToken().toUpperCase();
                String unit = stk.nextToken().toUpperCase();
                String origin = stk.nextToken().toUpperCase();
                double price = Double.parseDouble(stk.nextToken());

                Product pr = new Product(productID,productName,unit,origin,price);
                this.add(pr);
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
            for(Product pr : this) {
                pw.println(pr.getProductID() + "," + pr.getProductName() + "," + 
                        pr.getUnit() + "," + pr.getOrigin() + "," + pr.getPrice());
            }
            pw.close(); fw.close();  
            System.out.println("The product list has been saved to the file.");
        }
        catch(Exception e) {
//            System.out.println(e);
        }
    }
    
    public void printAllProducts() {
        if(this.isEmpty()) System.out.println("Empty list!");
        else {
            System.out.println("---------- Product List ----------");
            for (Product pr : this) System.out.println(pr);
            System.out.println("Total: " + this.size() + " product(s).");
        }
    }
    
    public boolean isCodeDupplicated(String code) {
        code = code.trim().toUpperCase();
        return search(code)!=null;
    }
    
    public Product search(String code) {
        code = code.trim().toUpperCase();
        for (int i=0; i<this.size(); i++) {
            if(this.get(i).getProductID().equals(code))
                return this.get(i);
        }
        return null;
    }
    
    public Product searchProductID(String code) {
        code = code.trim().toUpperCase();
        this.LoadFromFile(filename);
        for (Product product : this) {
            if (product.getProductID().trim().equals(code))
                return product;
        }
        return null;
    }
       
    public void addProduct() {
        String newProductID, newProductName, newUnit, newOrigin; 
        double newPrice;
        boolean codeDupplicated = false;
        do {
            newProductID = Inputter.inputStr("Input new product code: ");
            codeDupplicated = isCodeDupplicated(newProductID);
            if(codeDupplicated) System.out.println("Product code is dupplicated!");          
        } while (codeDupplicated==true);
        newProductName = Inputter.inputNonBlankStr("Name of new product: ");
        newProductName = newProductName.toUpperCase();
        newUnit = Inputter.inputNonBlankStr("Unit of new product: ");
        newUnit = newProductName.toUpperCase();
        newOrigin = Inputter.inputNonBlankStr("Origin of new product: ");
        newOrigin = newProductName.toUpperCase();
        newPrice = Inputter.inputDouble("Price: ");
        Product pr = new Product(newProductID,newProductName,newUnit,newOrigin,newPrice);
        this.add(pr);
        System.out.println("Product " + newProductID + " has been added.");
    }
    
    public void searchProduct() {
        if(this.isEmpty()) {
            System.out.println("Empty list. No search can be performed!");
        } else {
            String sCode = Inputter.inputStr("Input brand code for search: ");
            Product pr = this.search(sCode);
            if(pr==null)
                System.out.println("Brand " + sCode + " doesn't existed!");
            else
                System.out.println("Found: " + pr);
        }
    }    
    
    public void updateProduct() {
        if(this.isEmpty()) {
            System.out.println("Empty list. No update can be performed!");
        } else {
            String uCode = Inputter.inputStr("Input code of updated product: ");
            Product pr = this.search(uCode);
            if(pr==null) {
                System.out.println("Product " + uCode + " doesn't existed!");
            } else {
                String oldProductName = pr.getProductName();
                String msg1 = "Old ProductName: " + oldProductName + ", new ProductName: ";
                String newProductName = Inputter.inputNonBlankStr(msg1);
                pr.setProductName(newProductName); 
                String oldUnit = pr.getUnit();
                String msg2 = "Old Unit: " + oldUnit + ", new Unit: ";
                String newUnit = Inputter.inputNonBlankStr(msg2);
                pr.setUnit(newUnit); 
                String oldOrigin = pr.getOrigin();
                String msg3 = "Old Origin: " + oldOrigin + ", new Origin: ";
                String newOrigin = Inputter.inputNonBlankStr(msg3);
                pr.setOrigin(newOrigin);
                double oldPrice = pr.getPrice();
                String msg4 = "Old Price: " + oldPrice + ", new Price: ";
                double newPrice = Inputter.inputDouble(msg4);
                pr.setPrice(newPrice);  
                System.out.println("Product " + uCode + " has been updated.");
            }
        }
    }
    
    public Product getUserChoice() {
        int choice = 0;         
        System.out.println("---------- Please select a product ----------");
        choice = Menu.getChoice(this);
        return this.get(choice-1);
    }
       
}
