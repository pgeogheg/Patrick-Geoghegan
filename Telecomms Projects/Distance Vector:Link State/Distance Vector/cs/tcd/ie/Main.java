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
		    	Terminal terminal= new Terminal("LaptopR1");	
				try {			
					(new Laptop(terminal, "localhost", 1, new int []{2,3,5,6}, 2500)).start();		
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread laptopR2 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR2");	
				try {			
					(new Laptop(terminal, "localhost", 2, new int []{1,4}, 2000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		   
		Thread laptopR3 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR3");	
				try {			
					(new Laptop(terminal, "localhost", 3, new int []{1,7},1500)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread laptopR4 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("LaptopR4");	
				try {			
					(new Laptop(terminal, "localhost", 4, new int []{2,8},1000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread phoneE1 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE1");	
				try {			
					(new Phone(terminal, "localhost", 5, new int []{1}, 3000)).start();		
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread phoneE2 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE2");	
				try {			
					(new Phone(terminal, "localhost", 6, new int []{1}, 3000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		   
		Thread phoneE3 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE3");	
				try {			
					(new Phone(terminal, "localhost", 7, new int []{3},3000)).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		};
		
		Thread phoneE4 = new Thread() {
		    public void run() {
		    	Terminal terminal= new Terminal("phoneE4");	
				try {			
					(new Phone(terminal, "localhost", 8, new int []{4},3000)).start();
					
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


