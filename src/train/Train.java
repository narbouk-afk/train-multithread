package train;

/**
 * Représentation of a train. It is determined by two variables:
 * <ol>
 *   <li>
 *     its name
 *   </li>
 *   <li>
 *     its position
 *   </li>
 * </ol>
 *
 * @version 0.3
 */
public class Train extends Thread{
	private final String name;
	private Position pos;
	private int time;
	
	
	public Train(String name, Position p,int time) throws BadPositionForTrainException {
		if (name == null || p == null)
			throw new NullPointerException();

		// A train should be first be in a station
		if (!(p.getPos() instanceof Station))
			throw new BadPositionForTrainException(name);

		this.name = name;
		this.pos = p.clone();
		this.time=time;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Train[");
		result.append(this.name);
		result.append("]");
		result.append(" is on ");
		result.append(this.pos);
		return result.toString();
	}
	
	// M�thode permettant d'acc�der � la prochaine position du train
	public void moveNext() {
		
		try {
			Element newPos= nextPos();
			pos.setPos(newPos);}
		catch (Exception e) {}
		
		waitfor();
		System.out.println(this);
	}
	
	// M�thode determinant quelle sera la prochaine position du train
	public Element nextPos() throws BadPositionForTrainException {
		Element oldPos = pos.getPos();
		Direction oldDir = pos.getDir();
		return oldPos.getRailway().getNextElement(oldPos,oldDir);
		
	}
	
	// M�thode permettant au train de changer de direction si le train se trouve dans une station
	public void changeDir() throws BadPositionForTrainException {
		if (this.pos.getPos() instanceof Station ) {
			pos.changeDir();
		}
		else {
			throw new BadPositionForTrainException("Cannot turn");
		}
	}
	
	@Override
	public void run() {
		Element current = pos.getPos();
		while(true) {
			if (current instanceof Station) {
				try{
					changeDir();
				} catch(Exception e) {}
			} else if (current instanceof Section) {
				
			}
			
			
			try{
				Element next = nextPos();
				next.askAccess(current,pos.getDir());
				moveNext();
				current = next;
			} catch(Exception e) {}
			
			
			
		}
		
	}
	
	public void waitfor() {
		try {
			sleep(time);
			
		} catch(Exception e) {
			
		}
	}
	
}
