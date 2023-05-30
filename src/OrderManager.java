import tools.ProductList;
import tools.OrderList;
import tools.CustomerList;
import tools.Menu;
import java.util.ArrayList;

public class OrderManager {      
    public static void main(String[] args) { 
        String filename1 = "src\\data\\Products.txt";    
        String filename2 = "src\\data\\Customers.txt";
        String filename3 = "src\\data\\Orders.txt";
        
        ArrayList<String> options = new ArrayList<>();
        ProductList plist = new ProductList();
        CustomerList clist = new CustomerList();
        OrderList olist = new OrderList();
        
        options.add("List all products");
        options.add("List all customers");
        options.add("Search a customer based on his/her ID");
        options.add("Add a customer");
        options.add("Update a customer");
        options.add("Save customers to the file, named customers.txt");
        options.add("List all orders in ascending order of customer name");
        options.add("List all pending orders");
        options.add("Add an order");
        options.add("Update an order");
        options.add("Save orders to file, named orders.txt");
        options.add("Quit");              
        
        int choice = 0; 
        String choice2 = null;
        boolean quit = false;
        do {
            System.out.println("-----ORDER MANAGERMENT PROGRAM-----");
            choice = Menu.getChoice(options);
            switch(choice) {
                case 1:
                    plist.printAllProducts(); 
                    break;
                case 2:
                    clist.printAllCustomers(); 
                    break;
                case 3:
                    clist.searchCustomer(); 
                    break;
                case 4:
                    clist.addCustomer(); 
                    break;
                case 5:
                    clist.updateCustomer(); 
                    olist.LoadAgainCustomer(clist);
                    break;
                case 6:
                    clist.saveToFile(filename2); 
                    break;
                case 7:
                    olist.printAllOrders(); 
                    break;
                case 8:
                    olist.getPendingOrderList(); 
                    break;
                case 9:
                    olist.addOrder(); 
                    break;
                case 10:
                    ArrayList<String> list = new ArrayList<>();
                    do {                        
                        list.clear();
                        list.add("Update an order based on its ID");
                        list.add("Delete an order based on its ID");
                        choice2 = Menu.getChoice3(list);
                        switch(choice2) {
                            case "10.1": 
                                olist.updateOrder(); 
                                break;    
                            case "10.2": 
                                olist.removeOrder(); 
                                break;
                            default:
                                quit = true;
                        }   
                    } while(!quit); 
                    break;
                case 11:
                    olist.saveToFile(filename3); 
                    break;
                default:
                    System.out.println("Bye!");
            }
        } 
        while(choice > 0 && choice < 12);       
    }  
}