package q1;

public class Vector2D implements Cloneable{
	public int x;
	public int y;
	
	Vector2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Object clone() {
		return new Vector2D(x,y);
	}
	
	@Override
	public boolean equals(Object other) {
		Vector2D o = (Vector2D) other;
		return (x==o.x && y==o.y);
	}
}
