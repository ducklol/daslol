//Luqman Hakim
//server servant
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;


public class auctionImpl implements auctionator {
	
	public auctionImpl() throws java.rmi.RemoteException{
		super();
	}
	public void run(){
		
	}
	public void registerForCallback(clientServant callbackobj) throws java.rmi.RemoteException{

	}
	/*
public static void create() throws IOException{
	String input ="";
	auctionItem item = new auctionItem();
    ArrayList<String> itemList = new ArrayList<String>();
    ArrayList<String> newitemList = new ArrayList<String>();
    String[] lastoccur = new String[6];
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter a name: ");
    item.setOwnerName(reader.nextLine());
    System.out.println("Enter the item you wish to auction: ");
    item.setItem(reader.nextLine());
    System.out.println("Enter the value of the item: ");
    item.setValue(reader.nextLine());
    
    File file = new File("name.txt");
    file.createNewFile();					//create a file if it doesnt exist
			FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
    	StringBuffer stringBuffer = new StringBuffer();
    	String line;
    	
		while ((line = bufferedReader.readLine()) != null) {
		    stringBuffer.append(line);
		    stringBuffer.append("\n");
		    itemList.add(line);
		//itemList.get(0); //test the array
		}
		if(itemList.size()==0)
        {
            System.out.println(itemList.size());
              newitemList.add(1+","+item.getOwnerName()+","+item.getItemName()+","+item.getValue());
        }
		 else
         {
             lastoccur = itemList.get(itemList.size()-2).split(",");			//-2 because of blank spacing should be -1
             System.out.println("HOOOOOOOOOO " + itemList.get(itemList.size()-2));
             System.out.println(lastoccur[1]);			//to get counter
             int lastocc = Integer.parseInt(lastoccur[0]);
             lastocc++;
             newitemList.add(lastocc+ "," + item.getOwnerName()+","+item.getItemName()+","+item.getValue());
         }
			fileReader.close();
			System.out.println("aaaa" + newitemList.size());
	    	createClient(newitemList.get(0));
    	}

			
    	public static void createClient(String bid) throws RemoteException, FileNotFoundException, IOException {
    		  // TODO Auto-generated method stub
    		  
    		  PrintWriter out = null;
    		  try {
    		      out = new PrintWriter(new BufferedWriter(new FileWriter("name.txt", true)));
    		      out.println(bid + System.getProperty("line.separator"));
    		  }catch (IOException e) {
    		      System.err.println(e);
    		  }finally{
    		      if(out != null){
    		          out.close();
    		      }
    		 } 
    		 System.out.println("Bid created");

    		 }
		*/
	
@SuppressWarnings("resource")
public ArrayList<String> readList() throws IOException {
	ArrayList<String> itemList1 = new ArrayList<String>();
	File file = new File("auctionlist.txt");
    file.createNewFile();					//create a file if it doesnt exist
			FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
    	StringBuffer stringBuffer = new StringBuffer();
    	String line;
    	
		while ((line = bufferedReader.readLine()) != null) {
		    stringBuffer.append(line);
		    stringBuffer.append("\n");
		    itemList1.add(line);
		//itemList.get(0); //test the array
		}
		itemList1.removeAll(Collections.singleton(""));
		return itemList1;  
}

public ArrayList<String> readList2() throws IOException {
	ArrayList<String> itemList1 = new ArrayList<String>();
	File file = new File("auctionlist2.txt");
    file.createNewFile();					//create a file if it doesnt exist
			FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
    	StringBuffer stringBuffer = new StringBuffer();
    	String line;
    	
		while ((line = bufferedReader.readLine()) != null) {
		    stringBuffer.append(line);
		    stringBuffer.append("\n");
		    itemList1.add(line);
		//itemList.get(0); //test the array
		}
		itemList1.removeAll(Collections.singleton(""));
		return itemList1;  
}
	
public void createAuction(String auctItem) throws IOException {
		  // TODO Auto-generated method stub
	
    		  PrintWriter out = null;
    		  try {
    		      out = new PrintWriter(new BufferedWriter(new FileWriter("auctionlist.txt", true)));
    		      out.println(auctItem + System.getProperty("line.separator"));
    		  }catch (IOException e) {
    		      System.err.println(e);
    		  }finally{
    		      if(out != null){
    		          out.close();
    		      }
    		 } 
    		 System.out.println("Auction created");

    		 }

public void synchronizeAuction(String auctItem) throws IOException {
	  // TODO Auto-generated method stub

		  PrintWriter out = null;
		  try {
		      out = new PrintWriter(new BufferedWriter(new FileWriter("auctionlist2.txt", true)));
		      out.println(auctItem + System.getProperty("line.separator"));
		  }catch (IOException e) {
		      System.err.println(e);
		  }finally{
		      if(out != null){
		          out.close();
		      }
		 } 
		 System.out.println("Auctions synchronized");

		 }

public void newBidding(ArrayList<String> auctionList) throws RemoteException, IOException {
	String nbid = "";
	for(int i = 0; i < auctionList.size(); i++){
		if(nbid.equals("")){
			nbid = auctionList.get(i) + System.getProperty("line.separator");
		}
		else {
			nbid = nbid + System.getProperty("line.separator") + auctionList.get(i) + System.getProperty("line.separator");
		}
	}
	 PrintWriter out = null;
	  try {
	      out = new PrintWriter(new BufferedWriter(new FileWriter("auctionlist.txt", false)));
	      out.println(nbid);
	  }catch (IOException e) {
	      System.err.println(e);
	  }finally{
	      if(out != null){
	          out.close();
	      }
	 } 
	 System.out.println("New bid success!");

	
}
}

