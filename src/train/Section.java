package train;

/**
 * Représentation of a railway section
 */
public class Section extends Element {
	public Section(String name) {
		super(name);
	}
	
	
	
	public synchronized void askAccess(Element prev,Direction dir) {
		while(!(invariant(nbTrain+1) && railway.invariant(dir,prev))) {
			try {
				System.out.println("Somebody is waiting...");
				wait();
			} catch(Exception e) {
			}
			
		}
		
		prev.Departure(this,dir);
		
		nbTrain++;
		if (prev instanceof Station) {
			
			if (getRailway().getNbStation() ==2) {
				int nLR = railway.getNbTrain(0);
				int nRL = railway.getNbTrain(1);
				if (dir.equals(Direction.LR)) {
					nLR++;
					railway.setNbTrain(nLR,0);
				} else {
					nRL++;
					railway.setNbTrain(nRL,1);
				}
			}
			
			else {
				int nLRbeforeMiddleStation = railway.getNbTrain(0);
				int nRLbeforeMiddleStation = railway.getNbTrain(1);
				int nLRAfterMiddleStation = railway.getNbTrain(2);
				int nRLAfterMiddleStation = railway.getNbTrain(3);
				
				if (dir.equals(Direction.LR) && railway.getElements().indexOf(prev) == 0 ) {
					nLRbeforeMiddleStation ++;
					railway.setNbTrain(nLRbeforeMiddleStation,0);
				}
				
				else if (dir.equals(Direction.LR) && railway.getElements().indexOf(prev) != 0 ) {
					nLRAfterMiddleStation ++;
					railway.setNbTrain(nLRAfterMiddleStation,2);
				}
				
				else if (dir.equals(Direction.RL) && railway.getElements().indexOf(prev) == railway.getElements().size() - 1 ) {
					nRLAfterMiddleStation ++;
					railway.setNbTrain(nRLAfterMiddleStation,3);
				}
				
				else if (dir.equals(Direction.RL) && railway.getElements().indexOf(prev) != railway.getElements().size() - 1 ) {
					nRLbeforeMiddleStation ++;
					railway.setNbTrain(nRLbeforeMiddleStation,1);
				}
				
			}
		}
		notifyAll();
		 
	}
	
	public boolean invariant(int n) {
		return (n<2);
	}


	@Override
	public synchronized void Departure(Element nextPos, Direction dir) {
		// TODO Auto-generated method stub
		nbTrain--;
		notifyAll();
		
	}
	
	
	
}
