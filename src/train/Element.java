package train;

/**
 * This abstract class is the representation of a basic element of the system
 * It factorises the common ffeatures of the two subclasses
 * The two subclasses are :
 * <ol>
 *   <li>A railway station
 *   <li>A section of a railway track
 * </ol>s
 *
 */
public abstract class Element {
	private final String name;
	protected Railway railway;
	protected int nbTrain = 0;

	protected Element(String name) {
		if(name == null)
			throw new NullPointerException();
		
		this.name = name;
	}

	public void setRailway(Railway r) {
		if(r == null)
			throw new NullPointerException();
		
		this.railway = r;
	}
	
	

	@Override
	public String toString() {
		return this.name;
	}
	
	public Railway getRailway() {
		return this.railway;
	}
	
	public void setNbTrain(int n) {
		nbTrain = n;
	}
	
	public int getNbTrain() {
		return nbTrain;
	}

	public abstract void askAccess(Element prev, Direction dir);

	public abstract void Departure(Element nextPos,Direction dir);
	
	public abstract boolean invariant(int n);
	
	
}
