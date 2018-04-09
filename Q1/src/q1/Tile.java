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
			throw new Exception("Tile occupied by "+occupant);
		}
	}
	
	public boolean isOccupied() {
		return (occupant!=null);
	}

}
