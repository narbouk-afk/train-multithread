package train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.management.BadAttributeValueExpException;

/**
 * Representation of a circuit containing a set of train stations and railway sections
 *
 */
public class Railway {
	private final ArrayList<Element> elements; // list containing all elements of the railway
	private int nbStation = 0; // number of stations (could be 2 or 3)
	private ArrayList<Integer> nbTrain = new ArrayList(Collections.nCopies(2, 0)); // list containing the number of trains in the direction left to right and in the direction right to left

	
	public Railway(ArrayList<Element> elements) {
		if(elements == null)
			throw new NullPointerException();
		
		this.elements = elements;
		
		for (Element e : elements)
			e.setRailway(this);
		
		for (Element e : elements) {
			if (e instanceof Station) 
				nbStation ++;
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Element e : this.elements) {
			if (first)
				first = false;
			else
				result.append("--");
			result.append(e);
		}
		return result.toString();
	}
	
	// M�thode renvoyant le prochain �l�ment en partant de l'�l�ment e, et en allant dans la direction dir
	public Element getNextElement(Element e,Direction dir) throws BadPositionForTrainException {
        int index = 0;
        int length = elements.size();
        while (index<length && !elements.get(index).equals(e)) {
            index++;
        }

        if (index==length) {
            return null;
        } 
        
        if (dir.equals(Direction.LR)) {
            if (index==length-1) {
                throw new BadPositionForTrainException("No more section");
            } else {
                return elements.get(index + 1);
            }
        } else {
            if (index==0) {
                throw new BadPositionForTrainException("No more section");
            } else {
                return elements.get(index - 1);
            }
        }
    }
	
	public int getNbTrain(int index) {
		return nbTrain.get(index);
	}
	

	
	public void setNbTrain(int n,int index) {
		nbTrain.set(index, n);
	}
	
	public int getNbStation() {
		return nbStation;
	}
	
	
	public void addStation(String name, int size, Element first, Element second) throws Exception {
		
		Station s = new Station(name,size);
		int indexFirst = elements.indexOf(first);
		int indexSecond = elements.indexOf(second);
		elements.add(Math.max(indexFirst,indexSecond),s);
		nbStation++;
		nbTrain.add(0);
		nbTrain.add(0);
		
		if (getNbTrain(0) == 0) {
			setNbTrain(0, 0);
			setNbTrain(0, 2);
			for (int i= 0;i < elements.size();i++) {
				if (elements.get(i) instanceof Section)
					if (i < Math.max(indexSecond, indexFirst))
						setNbTrain(getNbTrain(1) + elements.get(i).getNbTrain(), 1);
				
					else
						setNbTrain(getNbTrain(3) + elements.get(i).getNbTrain(), 3);
						
							
			}
		}
		
		else {
			setNbTrain(0, 1);
			setNbTrain(0, 3);
			for (int i= 0;i < elements.size();i++) {
				if (elements.get(i) instanceof Section)
					if (i < Math.max(indexSecond, indexFirst))
						setNbTrain(getNbTrain(0) + elements.get(i).getNbTrain(), 0);
					else
						setNbTrain(getNbTrain(2) + elements.get(i).getNbTrain(), 2);
			}
			
		}
	}
	public ArrayList<Element> getElements(){
		return elements;
	}	
	
	
	
	public boolean invariant(Direction dir, Element prev) {
		if (nbStation <= 2) {
			int nRL = getNbTrain(1);
			int nLR = getNbTrain(0);
			if (dir.equals(Direction.LR)) {
				nLR++;
			}else {
				nRL++;
			}
			return nRL*nLR ==0; // On va v�rifier que les trains vont dans un seul sens. C'est-�-dire que soit nLR == 0 soit nRL == 0
		}
		else {
			Station s = null;
			for (int i = 1; i < getElements().size() - 1; i++) {
				if (getElements().get(i) instanceof Station)
					s = (Station) getElements().get(i);
			}
			
			Element next = null;
			try {
				next = getNextElement(prev, dir);
			} catch (BadPositionForTrainException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int indexNext = elements.indexOf(next);
			int indexMiddleStation = elements.indexOf(s);
			
			int nLRbeforeMiddleStation = getNbTrain(0);
			int nRLbeforeMiddleStation = getNbTrain(1);
			int nLRAfterMiddleStation = getNbTrain(2);
			int nRLAfterMiddleStation = getNbTrain(3);
			
			if (dir.equals(Direction.LR) && indexNext < indexMiddleStation)
				nLRbeforeMiddleStation ++;
			if (dir.equals(Direction.RL) && indexNext < indexMiddleStation)
				nRLbeforeMiddleStation ++;
			if (dir.equals(Direction.LR) && indexNext > indexMiddleStation)
				nLRAfterMiddleStation ++;
			if (dir.equals(Direction.RL) && indexNext > indexMiddleStation)
				nLRAfterMiddleStation ++;

		}
	}
	
}
