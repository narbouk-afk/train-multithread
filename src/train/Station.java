package train;

/**
 * Représentation of a train station.
 * A train station is determined by its name and its number of platoforms (so the number of trains it can host)
 *
 */
public class Station extends Element {
	private final int size;
	

	public Station(String name, int size) {
		super(name);
		if(name == null || size <=0)
			throw new NullPointerException();
		this.size = size;
	}
	
	public synchronized void askAccess(Element prev, Direction dir) {
		
		while (!invariant(nbTrain+1)) {
			try{
				wait();
			} catch (Exception ex) {}
		}
		prev.Departure(this,dir);
		
		nbTrain++;
		
		if (getRailway().getNbStation() ==2) {
			int nLR = railway.getNbTrain(0);
			int nRL = railway.getNbTrain(1);
			if (dir.equals(Direction.LR)) {
				nLR--;
				railway.setNbTrain(nLR,0);
			} else {
				nRL--;
				railway.setNbTrain(nRL,1);
			}
		}
		
		else {
			int nLRbeforeMiddleStation = railway.getNbTrain(0);
			int nRLbeforeMiddleStation = railway.getNbTrain(1);
			int nLRAfterMiddleStation = railway.getNbTrain(2);
			int nRLAfterMiddleStation = railway.getNbTrain(3);
			
			if (dir.equals(Direction.LR) && railway.getElements().indexOf(this) == railway.getElements().size() - 1 ) {
				nLRAfterMiddleStation --;
				railway.setNbTrain(nRLbeforeMiddleStation,2);
			}
			
			else if (dir.equals(Direction.LR) && railway.getElements().indexOf(this) != 0 && railway.getElements().indexOf(this) != railway.getElements().size() - 1) {
				nLRbeforeMiddleStation --;
				railway.setNbTrain(nLRAfterMiddleStation,0);
			}
			
			else if (dir.equals(Direction.RL) && railway.getElements().indexOf(this) == 0 ) {
				nRLbeforeMiddleStation --;
				railway.setNbTrain(nRLbeforeMiddleStation,1);
			}
			
			else if (dir.equals(Direction.RL) && railway.getElements().indexOf(this) != railway.getElements().size() - 1 && railway.getElements().indexOf(this) != 0) {
				nRLAfterMiddleStation --;
				railway.setNbTrain(nRLbeforeMiddleStation,3);
			}
			
		}
		notifyAll();
	}
	
	@Override
	public synchronized void Departure(Element nextPos,Direction dir) {
		while (!(nextPos.getRailway().invariant(dir,this) && invariant(nbTrain + 1))){
			try {
				wait();
			}catch (Exception ex) {}
			}
		nbTrain--;
		notifyAll();
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean invariant(int n) {
			return (n<=size);
	}


	
}
