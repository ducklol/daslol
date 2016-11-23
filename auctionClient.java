//Luqman Hakim
import java.io.*;
import java.util.*;
import java.rmi.*;
import java.text.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;
import java.rmi.RemoteException;	//Import the RemoteException class so you can catch it
import java.net.MalformedURLException;	//Import the MalformedURLException class so you can catch it
import java.rmi.NotBoundException;	//Import the NotBoundException class so you can catch it
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class auctionClient implements clientServant, Runnable{
//static auctionServant aS = null;
static Scanner scan = new Scanner(System.in);
static clientServant cS = null;
static auctionator a = null;
 public auctionClient() throws RemoteException {
      super();
   }

	public static void main(String args[]) throws IOException{
        String reg_host = "localhost";
       int reg_port = 1099;
       String choice;
       Boolean quit = false;
        
        if(args.length!= 0){
          reg_host = args[0];
              if(args.length < 2){
                reg_port = 1099;
              }
              if(args.length == 2){
                reg_port = Integer.parseInt(args[1]);
              }

         }
        try{
        	auctionator a = (auctionator)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/AuctionService");
        	
        while(!quit){
        	System.out.println("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Exit\nInput choice: ");
        	choice = scan.nextLine();
        	while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"))
            {
        		System.out.println("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Exit\nInput choice: ");
        		choice = scan.nextLine();
            }
        	if (choice.equals("1")){
        		//auctionImpl.create();
        		auctionItem auct = new auctionItem();
        		
        		ArrayList<String> itemList = new ArrayList<String>();
        		ArrayList<String> newitemList = new ArrayList<String>();
        		String[] lastoccur = new String[3];
        		
        		Scanner reader = new Scanner(System.in);
        	    System.out.println("Enter a name: ");
        	    auct.setOwnerName(reader.nextLine());
        	    System.out.println("Enter the item you wish to auction: ");
        	    auct.setItem(reader.nextLine());
        	    System.out.println("Enter the value of the item: ");
        	    auct.setValue(reader.nextLine());
        	    
        	    itemList = a.readList();
        	    
        	    if(itemList.size() != 0)
                {
        	    	lastoccur = itemList.get(itemList.size()-2).split(",");			//-2 because of blank spacing should be -1
                    System.out.println("HOOOOOOOOOO " + itemList.get(itemList.size()-2));
                    System.out.println(lastoccur[1]);			//to get counter
                    int lastocc = Integer.parseInt(lastoccur[0]);
                    lastocc++;
                    newitemList.add(lastocc+ "," + auct.getOwnerName()+","+auct.getItemName()+","+auct.getValue());
                    System.out.println("new item list " + newitemList.get(0));
                }
        		 else
                 {
        			 System.out.println("ITEMLIST " +itemList.size());
                     newitemList.add(1+","+auct.getOwnerName()+","+auct.getItemName()+","+auct.getValue());
                 }
        	    System.out.println("ITEMLIST2 " +itemList.size());
        	    	a.createAuction(newitemList.get(0));
            	}
        	}

        }
        catch (MalformedURLException murle) {
            System.out.println();
            System.out.println("MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
	}
	 public void run(){
		  
		  try{
		    
		  a.registerForCallback(cS);
		  }
		  catch(Exception e){

		  }
		}
}