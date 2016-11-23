//Luqman Hakim

import java.util.*;
import java.io.*;
public class auctionItem {
	
	String id;
	String itemName;
	String ownerName;
	String bidderName;
	int initialValue;
	int bid;
	long starttime;
	long endtime;
	
	public auctionItem(){
		
	}
	public auctionItem(String id, String itemName, String ownerName, int bid, long endtime){
		this.id = id;
		this.itemName = itemName;
		this.ownerName = ownerName;
		this.bid = bid;
		this.endtime = endtime;
	}
	public void setID(String id){
		this.id = id;
	}
	public String getID() {
		return id;
	}
	
	public void setOwnerName(String newOwnerName){
		this.ownerName = newOwnerName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setItem(String item){
		this.itemName = item;
	}
	public String getItemName(){
		return itemName;
	}
	public void setBidderName(String newBidderName){
		this.bidderName = newBidderName;
	}
	public String getBidderName() {
		return bidderName;
	}
	public void setValue(String newValue){
		try{
			initialValue = Integer.parseInt(newValue);
			bid = initialValue;
		}
		catch(Exception e){
			System.out.println("Invalid Value");
		}

	}
	public int getValue(){
		return initialValue;
	}
	public void setbidValue(String newValue){
		try{
			initialValue = Integer.parseInt(newValue);
		}
		catch(Exception e){
			System.out.println("Invalid Value");
		}
	}
	public void setStartTime(long newstime){
		starttime = newstime;
	}
	public void setDate(long newTime){
		endtime = newTime + starttime;

	}
	public void setClosetime(long closeTime){
		endtime = closeTime;
	}
}