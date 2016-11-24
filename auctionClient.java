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

static auctionItem auct = new auctionItem();			//auctionImpl.create();
static ArrayList<String> auctionList = new ArrayList<String>();		//arraylist to store item
//ArrayList<String> printitemList = new ArrayList<String>();	//list that gets printed out
static String[] counter;							//counter that acts as id for items
static String print;												//to print
static String bidderName;

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
        		
        		
        	    System.out.println("Enter a name: ");
        	    auct.setOwnerName(scan.nextLine());			//set owner name
        	    System.out.println("Enter the item you wish to auction: ");
        	    auct.setItem(scan.nextLine());				//set the item name
        	    System.out.println("Enter the value of the item: ");
        	    auct.setValue(scan.nextLine());				//set the value of the item
        	    System.out.println("Enter the duration the auction will be up for (in seconds): ");
        	    boolean checktime = false;
        	    while(!checktime){
        	    try {
        	         long l = Long.parseLong(scan.nextLine());								//convert string into long
        	         auct.setStartTime(System.currentTimeMillis());							//get current time in system
        	         auct.setEndtime(l);		//in millis
        	         SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");	//change format
        	         Date resultdate = new Date(auct.getEndTime());
        	         auct.setDate(sdf.format(resultdate));
        	         checktime = true;
        	      } catch (NumberFormatException nfe) {										//error handling for time
        	         System.out.println("Invalid input. Please try again. Duration of the auction:" );
        	         checktime = false;
        	      }
        	    }
        	    
        	    bidderName = "No current bidder";		//set bidder to no bidder since creating
        	    auct.setBidderName(bidderName);			
        	    
        	    auctionList = a.readList();				//read the array
        	    
        	    if(auctionList.size() != 0)
                {
        	    	counter = auctionList.get(auctionList.size()-1).split(",");			//-2 because of blank spacing should be -1 //to get counter
                    //System.out.println("HOOOOOOOOOO " + itemList.get(itemList.size()-2));
                    //System.out.println(counter[1]);			
                    int id = Integer.parseInt(counter[0]);	//to get counter
                    id++;									//increment counter by 1
                    print = (id+ "," + auct.getOwnerName()+","+auct.getItemName()+"," +auct.getValue() + "," +auct.getDate() + "," +auct.getBidderName() + " is winning.");	//add into the array
                    //System.out.println("new item list " + print);
                    System.out.println("*************Auction created!*************");
                }
        		 else
                 {
        			 //System.out.println("ITEMLIST " +itemList.size());
                     print = (1+","+auct.getOwnerName()+","+auct.getItemName()+"," +auct.getValue() + "," +auct.getDate() + "," +auct.getBidderName() + " is winning.");	//first item id always set at 1
                     System.out.println("*************Auction created!*************");
                 }
        	    //System.out.println("ITEMLIST2 " +itemList.size());
        	    //System.out.println("ITEMLIST3 " +printitemList.size());
        	    	a.createAuction(print);
            	}
        	else if(choice.equals("2")){
        		ArrayList<String> cc = new ArrayList<String>();
                ArrayList<String> bidlist = new ArrayList<String>();
                ArrayList<String> rpbid = new ArrayList<String>();
        			String[] selectedAuction = null;
        			String[] splitter = null;
        			String[] nobid = null;
        			String newBid;
        			int select = 0;
        			int selectedID = 0;
        			int bid = 0;
        			int price;
        			
        			bidlist = a.readList2();
        			
        			if(bidlist.size()==0)
        		    {
        				bidlist= a.readList();
        		        
        		    }
        		    else{
        		    	rpbid = a.readList();
        		    	for(int i=0; i < bidlist.size(); i++){
        		    		nobid = bidlist.get(i).split(",");
        		    		if(!nobid[5].equals("No current bidder is winning")){
        		    			rpbid.set(i, bidlist.get(i));
        		    		}
        		    	}
        		    	bidlist = rpbid;
        		    	cc = new ArrayList<String>(rpbid);
        		    }
        			if(bidlist.size() == 0 ){
        				System.out.println("There are no auctions available");
        			}
        		
        			auctionList = bidlist; 	//get the arraylist containing auctions
        			System.out.println("Here is the list of the current auctions:");
        			for(int i = 0; i < auctionList.size(); i++){		//first loop to print out all the auctions in the array
        				System.out.println("-------------------");
        				System.out.println(auctionList.get(i));
        			}
        			
        			System.out.println("Please enter your name: ");
        			bidderName = scan.nextLine();
        			auct.setBidderName(bidderName);
        			System.out.println("Key in the ID of the item you wish to make a bid on: ");
        			select = Integer.parseInt(scan.nextLine());
        			System.out.println("You have chosen to bid for ID number: " + select);
        			
        			for(int i = 0; i < auctionList.size(); i++){		//second loop to get the selected auction
        				selectedAuction = auctionList.get(i).split(",");	//to remove the ","
        				selectedID = Integer.parseInt(selectedAuction[0]);	//to get the id of the auction in the array
        			if(select == selectedID){							//once the correct auction is selected
        				System.out.println("The current bid for the auction item" + " '" + selectedAuction[2] + "'" + " is : " + selectedAuction[3]);			//display the current bid
        				//splitter = (selectedAuction[3].split("\\$"));	//split from the $ sign to get the actual price
        				//price = Integer.parseInt(splitter[1]);							//set the price with the actual value
        				price = Integer.parseInt(selectedAuction[3]);
        				System.out.println("Please place a bid higher than the current, " + selectedAuction[3]);
        				bid = scan.nextInt();							//scan the new bid
        				
        			/*	boolean checkbid = false;
        				 while(!checkbid){
        		        	    if (price < bid){
        		        	    	System.out.println("Your bid has been accepted! Good luck!");
        		        	    	
        		        	    	newBid = (selectedAuction[0] + "," + selectedAuction[1] + "," + selectedAuction[2] + "," + "$" + bid + "," + selectedAuction[4] + "," + auct.getBidderName() + " is winning.");
        		        	    	auctionList.set(i, newBid);
        		        	    	System.out.println(auctionList.get(i));
        		        	    	checkbid = true;
        		        	    }
        		        	    else{
        		        	    	System.out.println("Please place a bid higher than the current, " + selectedAuction[3]);
        		        	    	bid = scan.nextInt();
        		        	    	checkbid = false;
        		        	    }
        		         }
        				 */
        				
        				while(bid <= price)
                        {
                        System.out.println("Please a value higher than "+ bid +": ");
                        bid = scan.nextInt();
                        }
        				System.out.println("Your bid has been accepted! Good luck!");
        				newBid = (selectedAuction[0] + "," + selectedAuction[1] + "," + selectedAuction[2] + "," + bid + "," + selectedAuction[4] + "," + auct.getBidderName() + " is winning.");
	        	    	auctionList.set(i, newBid);
	        	    	System.out.println(auctionList.get(i));
	        	    	System.out.println("AAAAAAAA" + cc.size());
	        	    	System.out.println("aaaaa" + i );
	        	    	if(a.newBidding(auctionList , i, cc))
	                    {
	                     System.out.println("Bid made, Thank you for bidding");
	                    }
	                    else
	                    {
	                     System.out.println("Bid is either lower or equal to the current bid");
	                    }
        			}
        		}
        			//System.out.println(auctionList.size());
        			//a.newBidding(auctionList);
        	}
        	/*for(int i = 0; i < auctionList.size(); i++){
        		if (System.currentTimeMillis() - auct.getEndTime() == 1){
        			System.out.println("FUCK");
        		}
        	}*/
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