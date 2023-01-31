package train;

/**
 * Représentation of the train's position in the circuit.
 *The position is determined by two variables
 * <ol>
 *   <li>
 *     The element where the train is located: a train station
 *     or a railway section
 *   </li>
 *   <li>
 *     Its direction: left to right or right to left
 *   </li>
 * </ol>
 *         
 * @version 0.3
 */
public class Position implements Cloneable {
	private Direction direction;
	private Element pos;

	public Position(Element elt, Direction d) {
		if (elt == null || d == null)
			throw new NullPointerException();

		this.pos = elt;
		this.direction = d;
	}

	@Override
	public Position clone() {
		try {
			return (Position) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Element getPos() {
		return pos;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(this.pos.toString());
		result.append(" going ");
		result.append(this.direction);
		return result.toString();
	}
	public void setPos(Element e) {
        pos = e;
    }
    
    public Direction getDir() {
        return direction;
    }
    
    public void changeDir() {
        if (direction.equals(Direction.LR)) {
            direction = Direction.RL;
        } else {
            direction = Direction.LR;
        }
    }

}
