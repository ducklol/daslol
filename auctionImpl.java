//Luqman Hakim
//server servant
import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class auctionImpl implements auctionator {
	
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
		}
		itemList1.removeAll(Collections.singleton(""));
		return itemList1;  
}
@Override
public ArrayList<String> readList2() throws IOException {
	ArrayList<String> itemList1 = new ArrayList<String>();
	File file = new File("auctionlist2.txt");
    file.createNewFile();					//create a file if it doesnt exist
			FileReader fileReader = new FileReader(file);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
    	StringBuffer stringBuffer = new StringBuffer();
    	String line;
    	
		while ((line = bufferedReader.readLine()) != null) {
		    stringBuffer.append(line);
		    stringBuffer.append("\n");
		    itemList1.add(line);
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

public boolean checkValue(String bid, String prevBid) throws RemoteException, FileNotFoundException, IOException {
    
    int prevPrice;
    int newPrice;
    String[] prev = new String[6];
    String[] newBid = new String[6];
    prev = prevBid.split(",");
    newBid = bid.split(",");
    prevPrice = Integer.parseInt(prev[3]);
    newPrice = Integer.parseInt(newBid[3]);
    if(prevPrice >= newPrice)
    {
        return false;
    }
    
return true;     



}
@Override
public boolean newBidding(ArrayList<String> auctionList, int choice, ArrayList<String>arbid) throws RemoteException, IOException {
	String newbid="";
    ArrayList<String> latestbid = new ArrayList<String>();
    String [] nobid;
    latestbid = readList2();
    if(latestbid.size() ==0)
    {
    	latestbid = readList();
    }
    else
    {
    	arbid = readList();
        for(int i=0; i<latestbid.size();i++)
        {
            nobid = latestbid.get(i).split(",");
            if(!nobid[5].equals("No current bidder is winning"))
            {
            	arbid.set(i,latestbid.get(i));
            }
            
        }
    }
    ArrayList<String> check = new ArrayList<String>(arbid);
    
    boolean chk = checkValue(auctionList.get(choice), check.get(choice));
    if(chk)
    {
    	check.set(choice,auctionList.get(choice));
        for(int i =0; i <check.size(); i++)
        {
            
            if(newbid.equals(""))
            {
                newbid = check.get(i) + System.getProperty("line.separator");
            }
            else
            {
                newbid = newbid + System.getProperty("line.separator") + check.get(i)+System.getProperty("line.separator");
            }
        }
        
        ArrayList<String> origbid = new ArrayList<String>();
        
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter("auctionlist2.txt", false)));
            out.println(newbid);
        }catch (IOException e) {
            System.err.println(e);
        }finally{
            if(out != null){
                out.close();
            }
        }
        origbid = readList();
        latestbid = readList2();
        if(origbid.size()!=latestbid.size())
        {
            int diff = origbid.size() - latestbid.size();
            for(int i = diff; i>0 ; i--)
            {
                System.out.println(origbid.get(origbid.size()-i));
                synchronizeAuction(origbid.get(origbid.size()-i));
            } 
	  }
	  
	}
	else
	{
		return false;
	}
	 System.out.println("New bid successful!!!");
	 return true;
	
}
@Override
public String callbak(Date date) throws RemoteException, ParseException{
    
        ArrayList<String> endbid = new ArrayList<String>();
        String dt="";
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        
        try {
			endbid = readList2();
            
            String [] endDate;
            
            for(int i = 0 ; i < endbid.size(); i++)
            {
                endDate = endbid.get(i).split(",");
                String dt2 = sdf.format(date);
                if(dt2.equals(endDate[4]))
                {
                    dt = "********** Congrats! Bid Ended for " + endbid.get(i) + " **********";
                    return dt;
                }
                else
                {
                    
                }
            }
            
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  return dt;
     }
 public String mesg(String mes)
{
	return mes;
}
}

