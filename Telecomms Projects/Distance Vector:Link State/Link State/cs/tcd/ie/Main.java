package cs.tcd.ie;
/*
 *Patrick Geoghegan - 1332 0590
  Conor O’Flanagan - 1332 3109
  Sofwat Islam - 1331 8593

 **/
import tcdIO.*;

public class Main {
	
	public static void main(String[] args) {
			
		
		Thread laptopR1 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR1 (5001)");	
				try {			
					(new Laptop(terminal, "localhost", 5001, new int []{5002,5003,5005,5006}, 2500)).start();		
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread laptopR2 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR2 (5002)");	
				try {			
					(new Laptop(terminal, "localhost", 5002, new int []{5001,5004}, 2000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		   
		Thread laptopR3 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR3 (5003)");	
				try {			
					(new Laptop(terminal, "localhost", 5003, new int []{5001,5007},1500)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread laptopR4 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR4 (5004)");	
				try {			
					(new Laptop(terminal, "localhost", 5004, new int []{5002,5008},1000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread phoneE1 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE1 (5005)");	
				try {			
					(new Phone(terminal, "localhost", 5005, new int []{5001}, 3000)).start();		
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread phoneE2 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE2 (5006)");	
				try {			
					(new Phone(terminal, "localhost", 5006, new int []{5001}, 3000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		   
		Thread phoneE3 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE3 (5007)");	
				try {			
					(new Phone(terminal, "localhost", 5007, new int []{5003},3000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread phoneE4 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE4 (5008)");	
				try {			
					(new Phone(terminal, "localhost", 5008, new int []{5004},3000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};


		// Start the downloads.
		laptopR1.start();
		laptopR2.start();
		laptopR3.start();
		laptopR4.start();
		phoneE1.start();
		phoneE2.start();
		phoneE3.start();
		phoneE4.start();

		// Wait for them all to finish
		try {
			laptopR1.join();
			laptopR2.join();
			laptopR3.join();
			laptopR4.join();
			phoneE1.start();
			phoneE2.start();
			phoneE3.start();
			phoneE4.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}


