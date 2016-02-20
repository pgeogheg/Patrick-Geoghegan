package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import java.net.DatagramPacket;

public interface PacketContent {
	public String toString();
	public DatagramPacket toDatagramPacket();
}
