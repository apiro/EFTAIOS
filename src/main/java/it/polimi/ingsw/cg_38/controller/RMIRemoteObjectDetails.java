package it.polimi.ingsw.cg_38.controller;

public class RMIRemoteObjectDetails {
	public String RMI_ID;
	public static int RMI_PORT = 34555;
	

public RMIRemoteObjectDetails(String RMI_ID){
	
	this.setRMI_ID(RMI_ID);
	
	}


public static int getRMI_PORT() {
	return RMI_PORT;
}


public String getRMI_ID() {
	return RMI_ID;
}


public void setRMI_ID(String rMI_ID) {
	RMI_ID = rMI_ID;
}
}
