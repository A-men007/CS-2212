package ca.uwo.proxies;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import ca.uwo.client.Buyer;
import ca.uwo.client.Supplier;
import ca.uwo.frontend.Facade;

/**
 * @author kkontog, ktsiouni, mgrigori
 * This is one concrete implementation of {@link ca.uwo.proxies.Proxy} base class, it is the first proxy
 * the {@link ca.uwo.client.Client} will encounter. If the request of client is not issued by this class, 
 * it is forwarded to the {@link ca.uwo.proxies.SupplierProxy}, then {@link ca.uwo.proxies.LowQuantityProxy}, 
 * lastly {@link ca.uwo.proxies.HighQuantityProxy}. The link between those proxies implements Chain of Responsibility 
 * design pattern.
 */
public class WelcomeProxy extends Proxy {
	/**
	 * constructor for WelcomeProxy class.
	 */
	private String username;
	private String password;
	public WelcomeProxy() {
		super();
		boolean authenticted = false;
			Scanner input = new Scanner(System.in);
			System.out.println("Please enter your username: \n");
			username = input.nextLine();
			System.out.println("Please enter your password: \n");			
			password = input.nextLine();
			Map<String, String> authenticateList = new HashMap<>();
			//Read all the authenticateList from the file and save them. Each line consists of the ID, name and password of the buyer.
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File("buyer_file")));
				String line;
				while ((line = br.readLine()) != null) {
					String[] lineTokens = line.split("\t");
					authenticateList.put(lineTokens[1], lineTokens[2]);
				}
				
				Set entrySet = authenticateList.entrySet();
				Iterator buyerIterator = entrySet.iterator();
				while(buyerIterator.hasNext()){
				       Map.Entry aBuyer = (Map.Entry)buyerIterator.next();
				       if(aBuyer.getKey().equals(username) && aBuyer.getValue().equals(password)){
				    	   System.out.println("You've been successfully authenticated \n");
				    	   
				    	   break;
				   }
				}
				br.close();
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
	

	}

	/* (non-Javadoc)
	 * @see ca.uwo.frontend.interfaces.FacadeCommands#placeOrder(java.util.Map, ca.uwo.client.Buyer)
	 */
	@Override
	public void placeOrder(Map<String, Integer> orderDetails, Buyer buyer) {
		Facade facade = new Facade();
		facade.placeOrder(orderDetails, buyer);
	}

	/* (non-Javadoc)
	 * @see ca.uwo.frontend.interfaces.FacadeCommands#restock(java.util.Map, ca.uwo.client.Supplier)
	 */
	@Override
	public void restock(Map<String, Integer> restockDetails, Supplier supplier) {
		Facade facade = new Facade();
		facade.restock(restockDetails, supplier);
	}

}
