package q1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
	static int n,p,r,k;
	static final int size = 30;
	static Tile[][] grid = new Tile[30][30];
	static Random rnd = new Random();

	public static void main(String[] args) {
		if (args.length<3) {
			new Exception("Missing arguments, only "+args.length+" were specified!").printStackTrace();
		}
		// arg 0 is n -> nb of characters
		n = Integer.parseInt(args[0]);
		// arg 1 is p -> max nb of thread used
		p = Integer.parseInt(args[1]);
		// arg 2 is r -> number of obstacles
		r = Integer.parseInt(args[2]);
		// arg 3 is k -> move rate in ms
		k = Integer.parseInt(args[3]);
		
		assert(n > 0);
		assert(p > 0);
		assert(r > 0);
		assert(k > 0);
		initGrid();
		
		//ThreadPoolExecutor'
		
	}
	
	public static void initGrid() {
		//Create empty tiles
		for(int i=0; i< size; i++) {
			for(int j=0; j<size; j++) {
				grid[i][j] = new Tile();
			}
		}
		System.out.println("Y: "+r);
		//Add obstacles
		int obstacleCount = 0;
		while(obstacleCount < r) {
			int x = rnd.nextInt(size-2)+1;
			int y = rnd.nextInt(size-2)+1;
			try {
				grid[x][y].setOccupant(new Obstacle());
				obstacleCount++;
			} catch (Exception e) {}
		}
		System.out.println("U");
		//Add players
		int characterCount = 0;
		while(characterCount < n) {
			int x = rnd.nextInt(size-2)+1;
			int y = rnd.nextInt(size-2)+1;
			try {
				grid[x][y].setOccupant(new Character(new Vector2D(x,y)));
				characterCount++;
			} catch (Exception e) {}
		}
	}
	
	
	
	
	public static void printGrid() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print('[');
				if (grid[i][j].isOccupied()) {
					if(grid[i][j].getOccupant() instanceof Obstacle)
						System.out.print('x');
					else
						System.out.print('C');
				} else {
					System.out.print(' ');
				}
				System.out.print(']');
			}
			System.out.println();
		}
	}
}

class Mover implements Runnable{
	private Character c=null;
	@Override
	public void run() {
		
	}
	
	public Mover(Character c) {
		this.c = c;
	}
	
}
