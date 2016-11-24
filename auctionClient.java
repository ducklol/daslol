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

	@SuppressWarnings("resource")
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
        	System.out.println("Choose option\n1) Create Auction Item\n2) Bid Item\n3) Exit\nInput choice: ");
        	choice = scan.nextLine();
        	while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"))
            {
        		System.out.println("Choose option\n1) Create Auction Item\n2) Bid Item\n3) Exit\nInput choice: ");
        		choice = scan.nextLine();
            }
        	if (choice.equals("1")){
        		//auctionImpl.create();
        		auctionItem auct = new auctionItem();
        		
        		ArrayList<String> itemList = new ArrayList<String>();		//arraylist to store item
        		//ArrayList<String> printitemList = new ArrayList<String>();	//list that gets printed out
        		String[] counter;							//counter that acts as id for items
        		String print;												//to print
        		String bidderName;
        		
        		Scanner reader = new Scanner(System.in);		//initialize the scanner
        	    System.out.println("Enter a name: ");
        	    auct.setOwnerName(reader.nextLine());			//set owner name
        	    System.out.println("Enter the item you wish to auction: ");
        	    auct.setItem(reader.nextLine());				//set the item name
        	    System.out.println("Enter the value of the item: ");
        	    auct.setValue(reader.nextLine());				//set the value of the item
        	    System.out.println("Enter the duration the auction will be up for (in seconds): ");
        	    boolean checktime = false;
        	    while(!checktime){
        	    try {
        	         long l = Long.parseLong(reader.nextLine());
        	         auct.setStartTime(System.currentTimeMillis());
        	         auct.setEndtime(l);		//in millis
        	         SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        	         Date resultdate = new Date(auct.getEndTime());
        	         auct.setDate(sdf.format(resultdate));
        	         checktime = true;
        	      } catch (NumberFormatException nfe) {
        	         System.out.println("Invalid input. Please try again. Duration of the auction:" );
        	         checktime = false;
        	      }
        	    }
        	    
        	    bidderName = "No current bidder";
        	    auct.setBidderName(bidderName);
        	    
        	    itemList = a.readList();
        	    
        	    if(itemList.size() != 0)
                {
        	    	counter = itemList.get(itemList.size()-2).split(",");			//-2 because of blank spacing should be -1 //to get counter
                    //System.out.println("HOOOOOOOOOO " + itemList.get(itemList.size()-2));
                    //System.out.println(counter[1]);			
                    int id = Integer.parseInt(counter[0]);	//to get counter
                    id++;									//increment counter by 1
                    print = (id+ "," + auct.getOwnerName()+","+auct.getItemName()+","+ "$"+auct.getValue() + "," +auct.getDate() + "," +auct.getBidderName() + " is winning.");	//add into the array
                    //System.out.println("new item list " + print);
                    System.out.println("*************Auction created!*************");
                }
        		 else
                 {
        			 //System.out.println("ITEMLIST " +itemList.size());
                     print = (1+","+auct.getOwnerName()+","+auct.getItemName()+","+ "$" +auct.getValue() + "," +auct.getDate() + "," +auct.getBidderName() + " is winning.");	//first item id always set at 1
                     System.out.println("*************Auction created!*************");
                 }
        	    //System.out.println("ITEMLIST2 " +itemList.size());
        	    //System.out.println("ITEMLIST3 " +printitemList.size());
        	    	a.createAuction(print);
            	}
        	else if(choice.equals("2")){
        			ArrayList<String> auctionList = new ArrayList<String>();
        			auctionList = a.readList(); 	//get the arraylist containing auctions
        			System.out.println("Here is the list of the current auctions:");
        			for(int i = 0; i < auctionList.size(); i++){
        				System.out.println("-------------------");
        				System.out.println(auctionList.get(i));
        			}
        			
        			int select;
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