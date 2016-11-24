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
	long endTime;
	long startTime;
	String date;
	
	public auctionItem(){
		
	}
	public auctionItem(String id, String itemName, String ownerName, int bid, long endTime){
		this.id = id;
		this.itemName = itemName;
		this.ownerName = ownerName;
		this.bid = bid;
		this.endTime = endTime;
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
	public long getEndTime(){
		return endTime;
	}
	public void setEndtime(long endTime){			//in milliseconds
		this.endTime = endTime + startTime;
	}
	public void setStartTime(long startTime){
		this.startTime = startTime;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getDate(){
		return date;
	}
}