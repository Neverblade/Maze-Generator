import java.util.*;
import java.io.*;

public class GrowingTree //the maze map itself!
{
	/*
	W S E N
	0 1 2 3
	*/
	
	public Cell[][] grid; //most important, this is where all the data is
	public int height; //height of your grid (y)
	public int width; //width of your grid (x)
	public static int[] cX = {-1, 0, 1, 0}; //change in x depending on direction selected
	public static int[] cY = {0, 1, 0 ,-1}; //change in y depending on direction selected
	
	public GrowingTree(int height, int width, boolean sC) //create a randomly generated maze
	{
		//set the height and width of the cell grid
		grid = new Cell[height][width];
		
		//create the grid of default cells (all walls up)
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				grid[i][j] = new Cell();
			}
		}
		
		//set borders around the outside of the maze
		for (int i = 0; i < height; i++)
		{
			grid[i][0].raiseBorder(0);
			grid[i][width - 1].raiseBorder(2);
		}
		for (int i = 0; i < width; i++)
		{
			grid[0][i].raiseBorder(3);
			grid[height - 1][i].raiseBorder(1);
		}
		
		//create Rand objects for selecting x, y, and directional coordinates
		Random r = new Random();
		
		//create the set of cells
		ArrayList<Integer> xHist = new ArrayList<Integer>();
		ArrayList<Integer> yHist = new ArrayList<Integer>();
		
		//add random starting cords to the set
		int xC = r.nextInt(width);
		int yC = r.nextInt(height);
		grid[yC][xC].goThrough();
		xHist.add(xC);
		yHist.add(yC);
		
		do
		{
			//choose a random cell from the set
			int xy = r.nextInt(xHist.size());
			xC = xHist.get(xy);
			yC = yHist.get(xy);
			
			//check for xy's neighbors
			ArrayList<Integer> pC = new ArrayList<Integer>(); //directional possibility list
			
			//check which of the 4 possible directions are valid
			for (int i = 0; i < 4; i++)
			{
				//if it doesn't go out of bounds...
				if (xC+cX[i] < width && xC+cX[i] >= 0 && yC+cY[i] < height && yC+cY[i] >= 0)
				{
					//and if the neighboring cell of the direction hasn't been gone through, and there's no border...
					if (!grid[yC+cY[i]][xC+cX[i]].getBeenThrough() && !grid[yC][xC].getBorder(i))
					{
						//add it to the possible directions
						pC.add(i);
					}
				}
			}
			
			//if neighbors exist...
			if (pC.size() != 0)
			{
				//pick a random direction
				int d = pC.get(r.nextInt(pC.size()));
				
				//remove the walls between the two cells
				grid[yC][xC].removeWall(d);
				grid[yC+cY[d]][xC+cX[d]].removeWall((d+2)%4);
				
				//add the new cell to the set
				xHist.add(xC+cX[d]);
				yHist.add(yC+cY[d]);
				grid[yC+cY[d]][xC+cX[d]].goThrough();
			}
			//if none exist...
			else
			{
				//remove the cell from the set
				xHist.remove(xy);
				yHist.remove(xy);
			}
			
		} while (xHist.size() != 0); //continue this until the set size is 0 (no neighbors remain)
		if (sC)
		{
			solve(height, width);
		}
	}
	
	public GrowingTree(Cell[][] grid, int height, int width)
	{
		this.grid = grid;
		this.height = height;
		this.width = width;
	}
	
	
	//use depth-first search to solve the maze
	public void solve(int height, int width)
	{
		//clear the boolean goThroughs
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				grid[i][j].goOut();
			}
		}
		
		//create the arrays for history
		ArrayList<Integer> xHist = new ArrayList<Integer>();
		ArrayList<Integer> yHist = new ArrayList<Integer>();
		
		//set start and end coordinates
		int xC = 0;
		int xS = width - 1;
		int yC = 0;
		int yS = height - 1;
		
		//add the starts to the history list/solution list
		xHist.add(xC);
		yHist.add(yC);
		grid[yC][xC].goThrough();
		grid[yC][xC].isSolution();
		
		//do the magic
		do
		{
			//possible choice list
			ArrayList<Integer> pC = new ArrayList<Integer>();
			
			//check which of the 4 possible directions are valid
			for (int i = 0; i < 4; i++)
			{
				//if it doesn't go out of bounds...
				if (xC+cX[i] < width && xC+cX[i] >= 0 && yC+cY[i] < height && yC+cY[i] >= 0)
				{
					//if the neighboring cell of the direction hasn't been gone through, and there's no border...
					//and there are no walls in the way...
					if (!grid[yC+cY[i]][xC+cX[i]].getBeenThrough() && !grid[yC][xC].getBorder(i) && !grid[yC][xC].getWall(i))
					{
						//add it to the possible directions
						pC.add(i);
					}
				}
			}
			
			//if there are no possible directions to go, backtrack
			if (pC.size() == 0)
			{
				//remove your current position from the history
				xHist.remove(xHist.size()-1);
				yHist.remove(yHist.size()-1);
				
				//remove it's solution status
				grid[yC][xC].notSolution();
				
				//update your position to your previously visited position
				xC = xHist.get(xHist.size()-1);
				yC = yHist.get(yHist.size()-1);
			}
			
			//if there are possible directions, travel to one
			else
			{
				//get a random direction
				Random r = new Random();
				int d = pC.get(r.nextInt(pC.size()));
				
				//go to the new cell
				xC += cX[d];
				yC += cY[d];
				grid[yC][xC].goThrough();
				
				//update the history
				xHist.add(xC);
				yHist.add(yC);
				
				//give it solution status
				grid[yC][xC].isSolution();
			}
		} while (xC != xS || yC != yS);
	}
}
		