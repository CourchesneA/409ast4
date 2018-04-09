package q1;

public class Tile{
	private Occupant occupant;
	
	public Occupant getOccupant() {
		return occupant;
	}
	
	public void setOccupant(Occupant occupant) throws Exception {
		if(this.occupant == null) {
			this.occupant = occupant;
		}else {
			throw new Exception("Tile occupied by "+this.occupant);
		}
	}
	
	/**
	 * Remove the current occupant of this tile
	 */
	public void clear() {
		this.occupant = null;
	}
	
	public boolean isOccupied() {
		return (occupant!=null);
	}

}
