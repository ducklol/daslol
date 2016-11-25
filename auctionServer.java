//Luqman Hakim
//2228135B
//15AGC045H

import java.rmi.Naming;	//Import naming classes to bind to rmiregistry
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;


public class auctionServer {
	static int port = 1099;
   //server constructor
   public auctionServer() {
     
     //Construct a new CalculatorImpl object and bind it to the local rmiregistry
     //N.b. it is possible to host multiple objects on a server by repeating the
     //following method. 

     try {
    	 LocateRegistry.createRegistry(port);
       	//calculator c = new calculatorimpl();
       	auctionImpl aI= new auctionImpl();				//implementation for server
       	auctionator auction = (auctionator) UnicastRemoteObject.exportObject(aI, 0);
       	Naming.rebind("rmi://localhost:" + port + "/AuctionService", auction);
       	System.out.println("Starting up the server");
     } 
     catch (Exception e) {
       System.out.println("Server Error: " + e);
     }
   }

   public static void main(String args[]) {
     	//Create the new Calculator server
	if (args.length == 1)
		port = Integer.parseInt(args[0]);
	
	new auctionServer();
   }
}
