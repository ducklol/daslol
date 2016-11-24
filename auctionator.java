//Luqman Hakim
//interface
import java.io.IOException;
import java.util.*;

public interface auctionator extends java.rmi.Remote {	

	void createAuction(String itemList) throws java.rmi.RemoteException, IOException;		//write into file
	ArrayList<String> readList() throws IOException; //read array list
	public void registerForCallback(clientServant callbackobj) throws java.rmi.RemoteException;
	//public void ping() throws java.rmi.RemoteException;
	void newBidding(ArrayList<String> auctionList) throws java.rmi.RemoteException, IOException;
}