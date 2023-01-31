package train;

/**
 * Représentation of the direction that can be taken by a train: left to right
 * or right to left
 *
 */
public enum Direction {
	LR {
		@Override
		public String toString() {
			return "from left to right";
		}
	},
	RL {
		@Override
		public String toString() {
			return "from right to left";
		}
	};
}
