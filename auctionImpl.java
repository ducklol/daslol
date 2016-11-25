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
import java.text.ParseException;
import java.text.SimpleDateFormat;


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
@Override
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

public boolean checkValue(String bid, String prevBid) throws RemoteException, FileNotFoundException, IOException {
    
    int prevValue;
    int newValue;
    String[] prev = new String[6];
    String[] newBid = new String[6];
    prev = prevBid.split(",");
    newBid = bid.split(",");
    prevValue = Integer.parseInt(prev[3]);
    newValue = Integer.parseInt(newBid[3]);
    System.out.println(prevValue + " VS " + newValue);
    if(prevValue >= newValue)
    {
        return false;
    }
    
return true;     



}
@Override
public boolean newBidding(ArrayList<String> auctionList, int choice, ArrayList<String>rpbid) throws RemoteException, IOException {
	String newbid="";
    ArrayList<String> updatebid = new ArrayList<String>();
    String [] nobid;
    updatebid = readList2();
    if(updatebid.size() ==0)
    {
        updatebid = readList();
    }
    else
    {
        rpbid = readList();
        for(int i=0; i<updatebid.size();i++)
        {
            nobid = updatebid.get(i).split(",");
            if(!nobid[5].equals("No current bidder is winning"))
            {
                rpbid.set(i,updatebid.get(i));
            }
            
        }
    }
    ArrayList<String> cc = new ArrayList<String>(rpbid);
    
    //System.out.println(updatebid.size());
    //choice = choice -1;
    System.out.println(cc.get(choice));
    System.out.println(auctionList.get(choice));
    
    boolean chk = checkValue(auctionList.get(choice), cc.get(choice));
    System.out.println("THIS IS THE CHECK RESULT : " + chk);
    if(chk)
    {
        cc.set(choice,auctionList.get(choice));
        for(int i =0; i <cc.size(); i++)
        {
            
            if(newbid.equals(""))
            {
                newbid = cc.get(i) + System.getProperty("line.separator");
            }
            else
            {
                newbid = newbid + System.getProperty("line.separator") + cc.get(i)+System.getProperty("line.separator");
            }
        }
        
        ArrayList<String> morebid = new ArrayList<String>();
        
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
        morebid = readList();
        updatebid = readList2();
        System.out.println("MOREBID SIZE "+ morebid.size());
        System.out.println("UPDATEBID SIZE " + updatebid.size());
        if(morebid.size()!=updatebid.size())
        {
            int diff = morebid.size() - updatebid.size();
            for(int i = diff; i>0 ; i--)
            {
                System.out.println(morebid.get(morebid.size()-i));
                synchronizeAuction(morebid.get(morebid.size()-i));
            } 
	  }
	  
	}
	else
	{
		return false;
	}
	 System.out.println("New bid success!");
	 return true;
	
}
@Override
public String callbak(Date date) throws RemoteException, ParseException{
    
        ArrayList<String> endbid = new ArrayList<String>();
        String dt="";
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        long mili = sdf.parse(sdf.format(date)).getTime();
        
        try {
			endbid = readList2();
            
            String [] endDate;
            
            for(int i = 0 ; i < endbid.size(); i++)
            {
                endDate = endbid.get(i).split(",");
                String dt2 = sdf.format(date);
                //System.out.println(mili2);
                System.out.println(dt2);
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

