//Luqman Hakim
//2228135B
//15AGC045H
//interface
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.*;

public interface auctionator extends java.rmi.Remote {	

	void createAuction(String itemList) throws java.rmi.RemoteException, IOException;		//write into file
	void synchronizeAuction(String auctItem) throws IOException;
	ArrayList<String> readList() throws IOException; //read array list
	boolean newBidding(ArrayList<String> auctionList, int choice, ArrayList<String> rpbid)throws RemoteException, IOException;
	ArrayList<String> readList2() throws RemoteException, IOException;
	String callbak(Date date) throws RemoteException, ParseException;
}