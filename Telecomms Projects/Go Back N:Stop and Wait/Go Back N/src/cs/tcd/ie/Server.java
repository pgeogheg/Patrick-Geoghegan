package cs.tcd.ie;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import tcdIO.Terminal;

public class Server extends Node {
	static final int DEFAULT_PORT = 50001;

	Terminal terminal;

	private int ackNumber = 0;
	
	private int prev = 0;
	
	/*
	 * 
	 */
	Server(Terminal terminal, int port) {
		try {
			this.terminal = terminal;
			socket = new DatagramSocket(port);
			listener.go();
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Assume that incoming packets contain a String and print the string.
	 */
	public synchronized void onReceipt(DatagramPacket packet) {
		try {
			wait(100);
			StringContent data = new StringContent(packet);
			String dataString = data.toString().trim();
			String[] array = dataString.split("");
			ackNumber = Integer.parseInt(array[array.length - 1]);
			if (ackNumber != prev + 1) {
				prev = 0;
			} else {
				prev = ackNumber;
			}
			terminal.println(array[0] + ", Ack Number: " + ackNumber);
			DatagramPacket response;
			response = (new StringContent("" + ackNumber))
					.toDatagramPacket();
			response.setSocketAddress(packet.getSocketAddress());
			socket.send(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void start() throws Exception {
		terminal.println("Waiting for contact");
		this.wait();
	}

	/*
	 * 
	 */
	public static void main(String[] args) {
		try {
			Terminal terminal = new Terminal("Server");
			(new Server(terminal, DEFAULT_PORT)).start();
			terminal.println("Program completed");
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}
}