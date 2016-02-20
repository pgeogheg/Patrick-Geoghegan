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
	private static final long TIMER = 1000;
	private String ackReceived = "1";
	Terminal terminal;
	InetSocketAddress dstAddress;
	private int packetNum = 0;
	private String frame;
	
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
		ackReceived = (array[array.length - 1]);
		if (frame.equals(ackReceived)) {
			terminal.println("Wrong Ack: Resending packet " + ackReceived);
		} else {
			if (frame == "0") frame = "1";
			else frame = "0";
			packetNum++;
		}
	}

	
	/**
	 * Sender Method
	 * 
	 */
	public synchronized void start() throws Exception {
		try{
			byte[] data = null;
			frame = "0";
			DatagramPacket packet = null;
			String dataString = (terminal.readString("String to send: "));
			String[] splitData = dataString.split("");
			for (int i = 0; i < splitData.length; i++) {
				i = packetNum;
				data = (splitData[i] + frame).getBytes();
				terminal.println("Frame: " + frame);
				terminal.println("Sending packet...");
				packet = new DatagramPacket(data, data.length, dstAddress);
				socket.send(packet);
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