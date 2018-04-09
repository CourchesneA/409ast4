package q1;


public class Character extends Occupant{
	public int id;
	private int speed;
	private Vector2D position;
	private Vector2D destination;
	
	public Character(Vector2D position) {
		this.speed = Application.rnd.nextInt(4)+1;
		this.position = position;
	}
	
	public void move() {
		Vector2D nextPosition = getNextPosition();
		Tile nextTile = Application.grid[nextPosition.x][nextPosition.y];
		Tile currentTile = Application.grid[position.x][position.y];
		if(!nextTile.isOccupied()) {	//This check make sure that we do not try to lock a tile that is not empty
			//Only lock nextTile if it is free
			
			synchronized(nextTile) {
				//We have the lock, we will move to this tile and set our old tile to empty
				try {
					nextTile.setOccupant(this);
					currentTile.setOccupant(null);	//This should not be a problem since no one will lock/modify a tile that is not empty
				} catch (Exception e) {
					System.out.println("Error, unexpected occupant");
					System.exit(-1);
				}
			}
		}
	}
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	public Vector2D getPosition() {
		return position;
	}
	
	public void getNewDestination() {
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
		return new Vector2D(dx,dy);
	}
	
	public int getSpeed() {
		return speed;
	}

	@Override
	public String toString() {
		return "player-"+id;
	}

}
