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
		Tile nextTile = Application.getTile(nextPosition);
		Tile currentTile = Application.getTile(position);
		
		
		synchronized(getFirst(nextPosition, position)) {	//Lock both current position and next position according to an evaluation function (Same for everyone) -> solution to the dining philosophers
			synchronized(getSecond(nextPosition, position)) {
				//We have the lock, we will try to move to this tile and set our old tile to empty
				try {
					nextTile.setOccupant(this);
					position = nextPosition;
					currentTile.clear();	//This should not be a problem since no one will lock/modify a tile that is not empty
					moveCount++;
				} catch (Exception e) {
					//We were not able to move, unlock tile and cancel current destination
					//e.printStackTrace();
					destination = null;
				}
				//System.out.println(Thread.currentThread().getName());
				//Application.printGrid();

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
				int maxx = Math.min(Application.size-1, position.x+8);
				int miny = Math.max(0, position.y-8);
				int maxy = Math.min(Application.size-1, position.y+8);
				ans = new Vector2D(Application.rnd.nextInt(maxx-minx)+minx,Application.rnd.nextInt(maxy-miny)+miny);
				assert(ans.x < 30 || ans.y < 30);
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
	
	//Order the locks
	
	
	private Tile getFirst(Vector2D p1, Vector2D p2) {
		if(p1.x < p2.x ) return Application.grid[p1.x][p1.y];
		if(p1.x == p2.x) {
			if(p1.y < p2.y) return Application.grid[p1.x][p1.y];
		}
		return Application.grid[p2.x][p2.y];
	}
	private Tile getSecond(Vector2D p1, Vector2D p2) {
		if(p1.x < p2.x ) return Application.grid[p2.x][p2.y];
		if(p1.x == p2.x) {
			if(p1.y < p2.y) return Application.grid[p2.x][p2.y];
		}
		return Application.grid[p1.x][p1.y];
	}

}
