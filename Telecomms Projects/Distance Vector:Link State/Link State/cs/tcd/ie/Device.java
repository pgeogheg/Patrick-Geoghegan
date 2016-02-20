package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import tcdIO.*;

public abstract class Device extends Node{
	
	protected Terminal terminal;
	protected InetSocketAddress dstAddress;
	protected ArrayList <Integer> portsInRange; 
	protected int sourcePort;
	protected int waitTime;
	
	/**
	 * Constructor
	 * 	 
	 * Attempts to create socket at given port and create an InetSocketAddress for the destinations
	 */
	Device(Terminal terminal, String dstHost, int srcPort, int [] ports, int wait) {
		try {
			waitTime = wait;
			sourcePort = srcPort;
			this.terminal= terminal;
			Dijkstra.addToTable(this);
			portsInRange = new ArrayList<>();
			socket= new DatagramSocket(srcPort);
			listener.go();
			for(int i : ports){
				portsInRange.add(i);
			}	
		}
		catch(java.lang.Exception e) {e.printStackTrace();}
	}

	public abstract void onReceipt(DatagramPacket packet);

	public abstract void start() throws Exception;
	
}
