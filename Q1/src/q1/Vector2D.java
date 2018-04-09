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
}
