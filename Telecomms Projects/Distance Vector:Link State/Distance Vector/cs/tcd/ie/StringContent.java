package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import java.net.DatagramPacket;

public class StringContent implements PacketContent {
	String string;
	
	public StringContent(DatagramPacket packet) {
		byte[] data;
		
		data= packet.getData();
		string = new String(data);
	}
	
	public StringContent(String string) {
		this.string = string;
	}
	
	public String toString() {
		return string;
	}

	public DatagramPacket toDatagramPacket() {
		DatagramPacket packet= null;
		try {
			byte[] data= string.getBytes();
			packet= new DatagramPacket(data, data.length);
		}
		catch(Exception e) {e.printStackTrace();}
		return packet;
	}
}
