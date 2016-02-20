package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import tcdIO.*;

public class Phone extends Device {

	Phone(Terminal terminal, String dstHost, int srcPort, int [] ports, int wait) {

		super(terminal,dstHost, srcPort,ports,wait);		
	}

	public void onReceipt(DatagramPacket packet) {
		try {
			StringContent content= new StringContent(packet);
			String contentString = content.toString().trim();
			String [] data = contentString.split("\\.");

			String head = data[0];
			if(head.equals("I"))
			{
				String src = data[1];
				int step = Integer.parseInt(data[2]);
				String stamps = data[3];
				String [] stampsData = stamps.split(",");

				byte [] phoneRoute = ("R."+
						src+"."+
						(step-1)+"."+
						stamps+sourcePort+",").getBytes();

				dstAddress= new InetSocketAddress("localhost",Integer.parseInt(stampsData[step]));
				socket.send(new DatagramPacket(phoneRoute,phoneRoute.length, dstAddress));
			}
			//if its not a router message its just a normal message.
			else{
					terminal.println("\nMessage : "+data[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void start() throws Exception {		
		terminal.println("Associated laptop: R"+super.portsInRange.get(0));
		byte[] data= null;
		DatagramPacket packet= null;
		while(true){
			int destinationDevice = 0;
			destinationDevice = (terminal.readInt("Who to send: "));
			if (destinationDevice == -1){
				terminal.println(Dijkstra.constructTable(this));
			} else {
				data= ("P"+"."+destinationDevice+"."+terminal.readString("String to send: ")).getBytes();
				//Send only to laptop in range
				dstAddress= new InetSocketAddress("localhost", super.portsInRange.get(0));
				terminal.println("Sending packet...");
				packet= new DatagramPacket(data, data.length, dstAddress);
				socket.send(packet);
				terminal.println("Packet sent");
			}
		}
	}
}
