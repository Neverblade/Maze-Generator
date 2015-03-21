import java.util.*;
import java.io.*;
import java.awt.*;

public class GenerateMaze
{
	/*
	This program generates and prints a maze to an output file.
	"#" will be used to represent walls. "*" will represent paths
	
	Ideal size for a piece of paper (font: courier new; size: 6)
	Height: 35
	Width: 48
	*/

	public static void main(String[] args) throws Exception
	{
		//take user input for the height and width
		Scanner sc = new Scanner(System.in);
		System.out.print("Height: ");
		int height = sc.nextInt();
		System.out.print("Width: ");
		int width = sc.nextInt();
		
		//ask for if they want the solution
		System.out.print("Solution? (Y/N): ");
		boolean sC = sc.next().equals("Y");

		//generate the maze of that size
		//DepthFirst maze = new DepthFirst(height, width);
		GrowingTree maze = new GrowingTree(height, width, sC);

		//print
		printMaze(maze, 3);
	}
	
	//new and improved maze printing
	public static void printMaze(GrowingTree maze, int size)
	{
		DrawingPanel panel = new DrawingPanel((maze.grid[0].length*5 + 10)*size, (maze.grid.length*5 + 10)*size);
		Graphics g = panel.getGraphics();
		g.setColor(Color.BLACK);
		for (int i = 0; i < maze.grid.length; i++)
		{
			for (int j = 0; j < maze.grid[0].length; j++)
			{
				int x = (5 + j*5)*size;
				int y = (5 + i*5)*size;
				if (maze.grid[i][j].getWall(0)) g.drawLine(x-2*size, y-2*size, x-2*size, y+3*size);
				if (maze.grid[i][j].getWall(1)) g.drawLine(x-2*size, y+3*size, x+3*size, y+3*size);
				if (maze.grid[i][j].getWall(2)) g.drawLine(x+3*size, y-2*size, x+3*size, y+3*size);
				if (maze.grid[i][j].getWall(3)) g.drawLine(x-2*size, y-2*size, x+3*size, y-2*size);
				if (maze.grid[i][j].getSolution())
				{
					g.setColor(Color.RED);
					g.fillRect(x-1*size, y-1*size, 3*size, 3*size);
					g.setColor(Color.BLACK);
				}
			}
		}
	}
	
	/*public static void printMaze(GrowingTree maze, int height, int width) throws Exception
	{
		//create output file to print on
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("maze.txt")));
		
		//print top line
		out.println("start");
		out.print("+   +");
		for (int j = 1; j < width; j++)
		{
			out.print("---+");
		}
		out.println();
		out.print("|");
		for (int j = 0; j < width - 1; j++)
		{
			if (maze.grid[0][j].getWall(2))
			{
				if (maze.grid[0][j].getSolution())
				{
					out.print(" * |");
				}
				else
				{
					out.print("   |");
				}
			}
			else
			{
				if (maze.grid[0][j].getSolution())
				{
					out.print(" *  ");
				}
				else
				{
					out.print("    ");
				}
			}
		}
		if (maze.grid[0][width - 1].getSolution())
		{
			out.println(" * |");
		}
		else
		{
			out.println("   |");
		}
		
		//print rows
		for (int i = 1; i < height; i++)
		{
			out.print("+");
			for (int j = 0; j < width; j++)
			{
				if (maze.grid[i][j].getWall(3))
				{
					out.print("---+");
				}
				else
				{
					out.print("   +");
				}
			}
			out.println();
			out.print("|");
			for (int j = 0; j < width - 1; j++)
			{
				if (maze.grid[i][j].getWall(2))
				{
					if (maze.grid[i][j].getSolution())
					{
						out.print(" * |");
					}
					else
					{
						out.print("   |");
					}
				}
				else
				{
					if (maze.grid[i][j].getSolution())
					{
						out.print(" *  ");
					}
					else
					{
						out.print("    ");
					}
				}
			}
			if (maze.grid[i][width - 1].getSolution())
			{
				out.println(" * |");
			}
			else
			{
				out.println("   |");
			}
		}
		
		//print bottom line
		out.print("+");
		for (int j = 0; j < width - 1; j++)
		{
			out.print("---+");
		}
		out.println("   +");
		for (int j = 0; j < width - 1; j++)
		{
			out.print("    ");
		}
		out.print(" end ");
		out.close();
		System.exit(0);		
	}*/
}