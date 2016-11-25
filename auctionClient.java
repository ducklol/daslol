//Luqman Hakim
//2228135B
//15AGC045H
import java.util.*;
import java.rmi.*;
import java.text.*;
import java.rmi.RemoteException;	//Import the RemoteException class so you can catch it
import java.net.MalformedURLException;	//Import the MalformedURLException class so you can catch it
import java.rmi.NotBoundException;	//Import the NotBoundException class so you can catch it
import java.io.IOException;



public class auctionClient {
static Scanner scan = new Scanner(System.in);
static auctionator a;

static auctionItem auct = new auctionItem();			//auctionImpl.create();
static ArrayList<String> auctionList = new ArrayList<String>();		//arraylist to store item
static String[] counter;							//counter that acts as id for items
static String print;												//to print
static String bidderName;

static String reg_host = "localhost";
static int reg_port = 1099;
static String choice;
static Boolean quit = false;

 public auctionClient() throws RemoteException {
      super();
   }

	public static void main(String args[]) throws IOException{
        
        
        if(args.length!= 0){
          reg_host = args[0];
              if(args.length < 2){
                reg_port = 1099;
              }
              if(args.length == 2){
                reg_port = Integer.parseInt(args[1]);
              }

         }
        Timer t = new Timer();
        TimerTask timer2 = new TimerTask(){
             @Override
                public void run()
                {
                    try{
                      
                       
                     try {
					a = (auctionator)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/AuctionService");
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
                    }catch(RemoteException e)
                    {
                        System.out.println("Server is Offline");
                        System.out.println("Trying to reconnect");
                    }
                }
            
        };t.schedule(timer2, 0 , 5000);
        try{
        	a = (auctionator)Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/AuctionService");
        	Timer timer = new Timer ();
		      TimerTask hourly = new TimerTask () {
		     @Override
		     public void run () {
                  
				try {
                  Date date = new Date();
                  if(!a.callbak(date).equals(""))
                  {
                      
                      System.out.println(a.callbak(date));
                  }
                  else
                  {
                  }
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
               catch(ParseException pe)
               {
                  // pe.printStackTrace();
               }
			
          }
		      };timer.schedule(hourly, 0,  600);
        	
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
        	         auct.setEndtime(l * 1000);		//in millis
        	         SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");	//change format
        	         Date resultdate = new Date(auct.getEndTime());
        	         auct.setDate(sdf.format(resultdate));
        	         System.out.println(auct.getDate());
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
                    int id = Integer.parseInt(counter[0]);	//to get counter
                    id++;									//increment counter by 1
                    print = (id+ "," + auct.getOwnerName()+","+auct.getItemName()+"," +auct.getValue() + "," +auct.getDate() + "," +auct.getBidderName() + " is winning.");	//add into the array

                    System.out.println("*************Auction created!*************");
                }
        		 else
                 {
                     print = (1+","+auct.getOwnerName()+","+auct.getItemName()+"," +auct.getValue() + "," +auct.getDate() + "," +auct.getBidderName() + " is winning.");	//first item id always set at 1
                     System.out.println("*************Auction created!*************");
                 }
        	    	a.createAuction(print);
            	}
        	else if(choice.equals("2")){
        		ArrayList<String> checker = new ArrayList<String>();
                ArrayList<String> bidlist = new ArrayList<String>();
                ArrayList<String> arbid = new ArrayList<String>();
        			String[] selectedAuction = null;
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
        		    	arbid = a.readList();
        		    	for(int i=0; i < bidlist.size(); i++){
        		    		nobid = bidlist.get(i).split(",");
        		    		if(!nobid[5].equals("No current bidder is winning")){
        		    			arbid.set(i, bidlist.get(i));
        		    		}
        		    	}
        		    	bidlist = arbid;
        		    	checker = new ArrayList<String>(arbid);
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
        											
        				price = Integer.parseInt(selectedAuction[3]);	//set the price with the actual value
        				System.out.println("Please place a bid higher than the current, " + selectedAuction[3]);
        				bid = scan.nextInt();							//scan the new bid
        				while(bid <= price)
                        {
                        System.out.println("Please a value higher than "+ bid +": ");
                        bid = scan.nextInt();
                        }
        				System.out.println("Your bid has been accepted! Good luck!");
        				newBid = (selectedAuction[0] + "," + selectedAuction[1] + "," + selectedAuction[2] + "," + bid + "," + selectedAuction[4] + "," + auct.getBidderName() + " is winning.");
	        	    	auctionList.set(i, newBid);
	        	    	System.out.println(auctionList.get(i));
	        	    	if(a.newBidding(auctionList , i, checker))
	                    {
	                     System.out.println("Bid made, Thank you for bidding");
	                    }
	                    else
	                    {
	                     System.out.println("Bid is either lower or equal to the current bid");
	                    }
        			}
        		}
        	}
        	else if(choice.equals("3")) {		//quit
        		timer.cancel();
        		break;
        	}
        	
           }
        }
        catch (MalformedURLException murle) {
            //System.out.println();
            //System.out.println("MalformedURLException");
            //System.out.println(murle);
        }
        catch (RemoteException re) {
            //System.out.println();
            //System.out.println("RemoteException");
            //System.out.println(re);
        }
        catch (NotBoundException nbe) {
            //System.out.println();
            //System.out.println("NotBoundException");
            //System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            //System.out.println();
            //System.out.println("java.lang.ArithmeticException");
            //System.out.println(ae);
        }
	}
}