package q1;


public class Character extends Occupant{
	private int id;
	private int speed; //1 is the fastest, 4 is the slowest
	private Vector2D position;
	private Vector2D destination;
	private int moveCount = 0;
	
	public Character(Vector2D position, int id) {
		this.speed = Application.rnd.nextInt(4)+1;
		this.position = position;
		this.id = id;
	}
	
	/**
	 * if no dest or reached dest, set a new dest, move and wait
	 */
	synchronized public void move() {	//Synchronized so there is not multiple threads trying to move it at the same time
		if(destination == null || destination.equals(position)) {
			setNewDestination();
		}
		Vector2D nextPosition = getNextPosition();
		Tile nextTile = Application.grid[nextPosition.x][nextPosition.y];
		Tile currentTile = Application.grid[position.x][position.y];

		synchronized(nextTile) {
			//We have the lock, we will try to move to this tile and set our old tile to empty
			try {
				nextTile.setOccupant(this);
				currentTile.clear();	//This should not be a problem since no one will lock/modify a tile that is not empty
				moveCount++;
			} catch (Exception e) {
				//We were not able to move, unlock tile and cancel current destination
				//e.printStackTrace();
				destination = null;
			}
		}
		//Here they may or may not have moved but in both case they need to wait the same amount
		try {
			Thread.sleep(speed*Application.k);
		} catch (InterruptedException e) {}
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public void setNewDestination() {
		Vector2D ans = null;
		while(ans == null) {
			try {
				int minx = Math.max(0, position.x-8);
				int maxx = Math.min(Application.size, position.x+8);
				int miny = Math.max(0, position.y-8);
				int maxy = Math.min(Application.size, position.y+8);
				ans = new Vector2D(Application.rnd.nextInt(maxx)+minx,Application.rnd.nextInt(maxy)+miny);
			}catch(Exception e) { }
		}
		destination = ans;
	}
	
	public Vector2D getNextPosition() {
		int dx = 0;
		int dy = 0;
		if(destination.x > position.x) {
			dx=1;
		}else if(destination.x < position.x) {
			dx = -1;
		}
		if(destination.y > position.y) {
			dy = 1;
		}else if(destination.y < position.y) {
			dy = -1;
		}
		return new Vector2D(dx+position.x,dy+position.y);
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void printMoveCount() {
		System.out.println("Character-"+id+" moved "+moveCount+" time(s)");
	}

	@Override
	public String toString() {
		return "player-"+id;
	}

}
