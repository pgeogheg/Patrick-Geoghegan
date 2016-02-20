/*****************************************************************************
 * CountiesMap
 *
 * @version 1.0
 * @author TODO
 *
 * The constructor of this class loads a MapImage object.
 * The constructor then computes the regions with the same colour on the map.
 * We call these regions <b>counties</b>.
 * 
 * Once the counties have been computed, their size can be obtained by calling
 * the method <code>getCountySize</code>.
 *
 * This implementation is based on the data-structure/algorithm: 
 * 
 * For this assignment I used a version of the Weighted Quick Union Find algorithm. I used some methods from the weighted union find 
 * implementation given to us in class. The constructor creates an instance of the Weighted Union class. It then runs through the entire array 
 * of pixels and, if they are equal in color, unions each pixel to the pixel to its right and below it...(x+1,y and x,y+1). The constuctor (F(N)) has 
 * an amortised running time of NlogN. (N pixels * logN for union). The getCountySize function (G(N)) has 
 * an amortised running time of logN. ( logN for .find() and 1 for .getIndex() ). N is the number of pixel (getWidth * getHeight) and M is the amount of
 * getCountySize calls ( number of counties which is 32). Therefore the amortised run time, O[(f(N)+M*g(N))/M], is O[( NlogN + M * logN) / M].
 * 
 * The worst-case asymptotic running time is O[NlogN + logN]. This is because if every pixel in the Image is the same colour, the 
 * constructor will call .union(), which is logN, N times. Then The getCountySize() function calls .find() which has a worst case asymptotic running time 
 * of logN. Constructor (NlogN) + getCountySize (logN) = NlogN + logN.
 * 
 * The memory usage is N. CountyUF stores a weighted union of every pixel... therefore it is size N, where N is the number of pixels. 
 * This requires memory space of N.
 *
 *****************************************************************************/
public class CountiesMap
{
  private final MapImage map; //1
  WeightedUF countyUF;//1
  int color;//1
  
  /**
   * The constructor does all the map calculations.
   * The parameter of the class contains a map of counties of a country.
   * There is no text on the map. It has only single-colour regions.
   * Some of these single-colour regions represent counties (you don't know which ones).
   * There might be other regions on the map such as lakes, oceans, islands etc.
   *
   * @param map this is a MapImage object
   */
  public CountiesMap(MapImage map)  //contructor time is NlogN
  {
    this.map = map; //1
    countyUF=new WeightedUF((map.getHeight() * map.getWidth())); //N
    for(int i=0;i<map.getWidth()-1;i++){ //width
    	 for(int j=0;j<map.getHeight()-1;j++){ //height... width*height==N
    		 color=map.getRGB(i,j); //N
    		 if(color==map.getRGB(i+1,j)){ //N
    			 countyUF.union(getIndex(i,j),getIndex(i+1,j)); //N logN
    		 }
    		 if(color==map.getRGB(i,j+1)){ //=N
    			 countyUF.union(getIndex(i,j),getIndex(i,j+1));//N logN
    		 }
    	 }
    }
    
  }

  /**
   * This method returns the size in pixels of the region which includes the point (x,y).
   *
   * @param x the x-coordinate of the point in the region.
   * @param y the y-coordinate of the point in the region.
   * @return the size of the region in pixels.
   */
  public int getCountySize(int x, int y) //function time is logN
  {

    return countyUF.sz[countyUF.find(getIndex(x,y))]; // .find = logN and .getIndex = 1... total logN
  }

  /**
   * This method can be used to convert the map's (x,y) coordinates to a unique linear index.
   * Suppose we want to store all pixels of the map in a one-dimentional array.
   * Then the array will have to have size (map.getHeight() * map.getWidth()).
   * Pixel (x,y) will be at position getIndex(x,y) in the array.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the index in a 1-dimentional array corresponding to pixel (x,y).
   */
  private int getIndex(int x, int y)
  {
    return y * map.getWidth()  +  x;
  }
  
  public class WeightedUF {
	    private int[] id;   
	    private int[] sz;     


	    public WeightedUF(int N) {
	        id = new int[N];
	        sz = new int[N];
	        for (int i = 0; i < N; i++) { //N
	            id[i] = i; //N
	            sz[i] = 1; //N
	        }
	    }

	    public int find(int p) {
	        while (p != id[p])
	            p = id[p];
	        return p;
	    }


	    public void union(int p, int q) { //logN
	        int rootP = find(p);
	        int rootQ = find(q);
	        if (rootP == rootQ) return;

	        if   (sz[rootP] < sz[rootQ]) { id[rootP] = rootQ; sz[rootQ] += sz[rootP]; }
	        else                         { id[rootQ] = rootP; sz[rootP] += sz[rootQ]; }
	    }
	}

}
