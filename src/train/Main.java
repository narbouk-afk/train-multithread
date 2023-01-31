package train;

import java.util.ArrayList;

/**
 *
 */
public class Main {
	public static void main(String[] args) {
		Station A = new Station("GareA", 3);
		A.setNbTrain(3);
		Station D = new Station("GareD", 3);
		Section AB = new Section("AB");
		Section BC = new Section("BC");
		Section CD = new Section("CD");
		
		Railway r = new Railway(new ArrayList<Element>() {{add(A);add(AB);add(BC);add(CD);add(D); }});
		
			
		System.out.println("The railway is:");
		System.out.println("\t" + r);
		Position p = new Position(A, Direction.LR);
		try {
			Train t1 = new Train("1", p,1000);
			Train t2 = new Train("2", p,2000);
			Train t3 = new Train("3", p,3000);
			t1.start();
			t2.start();
			t3.start();
			
		} catch (BadPositionForTrainException e) {
			System.out.println("Le train " + e.getMessage());
		}

	}
}
