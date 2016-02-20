package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import tcdIO.*;


public class Laptop extends Device {
	
	ArrayList <String> routeTable;
	
	Laptop(Terminal terminal, String dstHost, int srcPort, int [] ports ,int wait) {
		
	  super(terminal,dstHost, srcPort,ports,wait);	
	  routeTable = new ArrayList<>();
	  while(routeTable.size() < 8) routeTable.add("");
	
	}

	public void onReceipt(DatagramPacket packet) {

		try {
			StringContent content= new StringContent(packet);
			String contentString = content.toString().trim();
			String [] data = contentString.split("\\.");

			String head = data[0];
			/*first find if message is from a phone to avoid 
			a parse error(ArrayOutOfBounds) as messages are different formats*/
			if(head.equals("P")){
				phoneRelay(packet);
			}
			/*if not from a phone, its from a laptop which means its for routing table stuff*/
			else{
				String src = data[1];
				int step = Integer.parseInt(data[2]);
				String stamps = data[3];
				String [] stampsData = stamps.split(",");

				byte [] forward = (head+"."+
						src+"."+
						(step+1)+"."+
						stamps+sourcePort+",").getBytes();
				byte [] back = ("R."+
						src+"."+
						(step-1)+"."+
						stamps).getBytes();

				//Decide what kind of message it is
				switch(head){
				case "I":
					//Send forward
					for(int port : portsInRange){
						//If message hasnt been to this port send it to it , else do nothing
						if(!stamps.contains(""+port+"")){

							dstAddress= new InetSocketAddress("localhost", port);
							socket.send(new DatagramPacket(forward,forward.length, dstAddress));
						}

					}
					//Send back to last stamp and this means its terminal so add stamp
					back = ("R."+
							src+"."+
							(step-1)+"."+
							stamps+sourcePort+",").getBytes();
					dstAddress= new InetSocketAddress("localhost",Integer.parseInt(stampsData[step]));
					socket.send(new DatagramPacket(back,back.length, dstAddress));
					break;

				case "R":
					//if message came back as R and src is us then save to our routing table if and only if shorter path to relevant node.
					if(src.equals(String.valueOf(sourcePort))){
						int posInTable = Integer.parseInt(stampsData[stampsData.length-1]) - PORT_OFFSET;
						if(routeTable.get(posInTable-1).length() > stamps.substring(2,stamps.length()-1).length() || 
								routeTable.get(posInTable-1) == ""){
							routeTable.set(posInTable-1, stamps.substring(5,stamps.length()-1).trim());
							//terminal.println(stampsData[stampsData.length-1]+"--"+stamps.substring(4,stamps.length()-1));
						}
					}
					//send back 
					else{
						dstAddress= new InetSocketAddress("localhost", Integer.parseInt(stampsData[step]));
						socket.send(new DatagramPacket(back,back.length, dstAddress));
					}
					break;

				default:

					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
    
    /*For handling phone messages*/
	private void phoneRelay(DatagramPacket packet) {
		try {
			StringContent content= new StringContent(packet);
			String contentString = content.toString().trim();
			String [] data = contentString.split("\\.");
			int dest= Integer.parseInt(data[1]);
			System.out.println(dest);
			if(dest == sourcePort){
				terminal.println("Message : "+data[2]);
			}
			/*find next closest device to destination*/
			else{
				terminal.println("Relay to dest: "+dest);
				String path = routeTable.get(dest-(PORT_OFFSET+1));
				System.out.println(path);
				if(path.length()==0)
				{
					dstAddress= new InetSocketAddress("localhost", dest-(PORT_OFFSET+1));
				}else{
					dstAddress= new InetSocketAddress("localhost", (path.charAt(3)-'0')+PORT_OFFSET);
				}
				socket.send(new DatagramPacket(contentString.getBytes(),contentString.getBytes().length,dstAddress));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*Sends out initial messages to build routing table*/
	/*Sends out initial messages to build routing table*/
	public synchronized void start() throws Exception {
		this.wait(waitTime);
		for(int i = 0 ; i < 1; i ++){
			for(int j : portsInRange){
				dstAddress= new InetSocketAddress("localhost", j);
				//header.src.steps.stamps
				String packetData = "I." + sourcePort + "." +"0"+"."+sourcePort+",";
				byte [] data = packetData.getBytes();
				socket.send(new DatagramPacket(data,data.length, dstAddress));
			}
		}
		terminal.println("Routing Table");
		terminal.println("-------------");
		terminal.println(Dijkstra.constructTable(this));
		this.wait();
	}
}
