package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import java.util.Hashtable;

public class Dijkstra {
	
	private static Hashtable<Integer, Device> allDevices = new Hashtable<Integer, Device>();
	private static String[] table;
	private static boolean[] known;
	
	public static void addToTable(Device device){
		allDevices.put(device.sourcePort, device);
	}
	
	public static String constructTable(Device start) {
		known = new boolean[(allDevices.size())];
		table = new String[(allDevices.size())];
		table[start.sourcePort - start.PORT_OFFSET - 1] = "" + start.sourcePort + "|0|-1 ";
		applyDijkstra(start, 1);
		String returnString = "";
		for (int i = 0; i < table.length; i++){
			returnString += table[i] + '\n';
		}
		return returnString;
	}
	
	private static void applyDijkstra(Device device, int cost) {
		int index = device.sourcePort - device.PORT_OFFSET - 1;
		known[index] = true;
		for (int i = 0; i < device.portsInRange.size(); i++){
			int next = device.portsInRange.get(i);
			int costCheck = getCost(table[next - device.PORT_OFFSET - 1]);
			if ((costCheck > cost || costCheck == -1) && costCheck != 0){
				table[next - device.PORT_OFFSET - 1] = "" + next + "|" + cost + "|" + device.sourcePort;
			}
		}
		for (int i = 0; i < device.portsInRange.size(); i++){
			int next = device.portsInRange.get(i);
			Device nextDevice = allDevices.get(next);
			if (!known[next - device.PORT_OFFSET - 1]) {
				applyDijkstra(nextDevice, cost + 1);
			}
		}
	}
	
	private static int getCost(String tableLine) {
		if (tableLine == null) return -1;
		String[] data = tableLine.split("\\|");
		return Integer.parseInt(data[1]);
	}
}
