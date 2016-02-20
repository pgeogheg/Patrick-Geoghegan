/**
 * 
 */
package cs.tcd.ie;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

import tcdIO.*;

/**
 *
 * Client class
 * 
 * An instance accepts user input 
 *
 */
public class Client extends Node {
	static final int DEFAULT_SRC_PORT = 50000;
	static final int DEFAULT_DST_PORT = 50001;
	static final String DEFAULT_DST_NODE = "localhost";	
	
	Terminal terminal;
	InetSocketAddress dstAddress;
	
	private static final long TIMER = 2000;
	private int ackReceived = 1;
	
	private static final int WINDOW_SIZE = 6;
	private int window = 0;
	private int prev = -1;
	private int length;
	private boolean finished = false;
	
	/**
	 * Constructor
	 * 	 
	 * Attempts to create socket at given port and create an InetSocketAddress for the destinations
	 */
	Client(Terminal terminal, String dstHost, int dstPort, int srcPort) {
		try {
			this.terminal= terminal;
			dstAddress= new InetSocketAddress(dstHost, dstPort);
			socket= new DatagramSocket(srcPort);
			listener.go();
		}
		catch(java.lang.Exception e) {e.printStackTrace();}
	}

	
	/**
	 * Assume that incoming packets contain a String and print the string.
	 */
	public synchronized void onReceipt(DatagramPacket packet){
		StringContent content= new StringContent(packet);
		String clientOutput = content.toString().trim();
		String[] array = clientOutput.split("");
		ackReceived = Integer.parseInt(array[array.length - 1]);
		if (ackReceived == prev+1 || (ackReceived == 0 && prev == WINDOW_SIZE-1)){
			window++;
			prev = ackReceived;
		} else {
			window = prev;
		}
		if (window >= length) finished = true;
	}

	
	/**
	 * Sender Method
	 * 
	 */
	public synchronized void start() throws Exception {
		try{
			byte[] data = null;
			DatagramPacket packet = null;
			String dataString = (terminal.readString("String to send: "));
			String[] splitData = dataString.split("");
			length = splitData.length;
			while (!finished) {
				for (int i = 0; i < WINDOW_SIZE; i++){
					data = (splitData[window + i] + i).getBytes();
					terminal.println("Frame: " + i);
					terminal.println("Sending packet...");
					packet = new DatagramPacket(data, data.length, dstAddress);
					socket.send(packet);
				}
				terminal.println("Packet sent");
				socket.setSoTimeout(1000);
				this.wait(TIMER);
			}
		} catch(Exception e){
			
		}
	}


	/**
	 * Test method
	 * 
	 * Sends a packet to a given address
	 */
	public static void main(String[] args) {
		try {					
			Terminal terminal= new Terminal("Client");		
			(new Client(terminal, DEFAULT_DST_NODE, DEFAULT_DST_PORT, DEFAULT_SRC_PORT)).start();
			terminal.println("Program completed");
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}