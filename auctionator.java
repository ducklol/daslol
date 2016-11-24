//Luqman Hakim
//interface
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public interface auctionator extends java.rmi.Remote {	

	void createAuction(String itemList) throws java.rmi.RemoteException, IOException;		//write into file
	void synchronizeAuction(String auctItem) throws IOException;
	ArrayList<String> readList() throws IOException; //read array list
	public void registerForCallback(clientServant callbackobj) throws java.rmi.RemoteException;
	//public void ping() throws java.rmi.RemoteException;
	boolean newBidding(ArrayList<String> auctionList, int choice, ArrayList<String> rpbid)
			throws RemoteException, IOException;
	ArrayList<String> readList2() throws RemoteException, IOException;
}