import java.util.*;
public class aiTicTacToe {

	public int player; //1 for player 1 and 2 for player 2
	private List<List<positionTicTacToe>>  winningLines = new ArrayList<>(); 
	public aiTicTacToe(){
		winningLines = initializeWinningLines();
	}

	private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> board)
	{
		//a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
		int index = position.x*16+position.y*4+position.z;
		return board.get(index).state;
	}
	
	//Algorithm that picks random moves
	public positionTicTacToe myAIAlgorithm2(List<positionTicTacToe> board, int player)
	{
		positionTicTacToe myNextMove2 = new positionTicTacToe(0,0,0);
		
		do
			{
				Random rand = new Random();
				int x = rand.nextInt(4);
				int y = rand.nextInt(4);
				int z = rand.nextInt(4);
				myNextMove2 = new positionTicTacToe(x,y,z);
				//myNextMove = findBestMove(board, player);
			}while(getStateOfPositionFromBoard(myNextMove2,board)!=0);
		return myNextMove2;
			
		
	}
	//AI algorithm implementing minimax, heuristic rule, and alpha-beta pruning
	public positionTicTacToe myAIAlgorithm(List<positionTicTacToe> board, int player)
	{
		//TODO: this is where you are going to implement your AI algorithm to win the game. The default is an AI randomly choose any available move.
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);
		
		do
			{
				myNextMove = findBestMove(board, player);
			}while(getStateOfPositionFromBoard(myNextMove,board)!=0);
		return myNextMove;
			
		
	}
	//evaluates and assigns a heurisitc value to each possible board state for the next possible moves
	private int evaluate(List<positionTicTacToe> board, int player)
	{
		int score = 0;
		for (int i=0;i<winningLines.size();i++)
		{
			for (int j=0;j<winningLines.get(i).size();j++)
			{
				positionTicTacToe p0 = winningLines.get(i).get(0);
				positionTicTacToe p1 = winningLines.get(i).get(1);
				positionTicTacToe p2 = winningLines.get(i).get(2);
				positionTicTacToe p3 = winningLines.get(i).get(3);
				
				int state0 = getStateOfPositionFromBoard(p0,board);
				int state1 = getStateOfPositionFromBoard(p1,board);
				int state2 = getStateOfPositionFromBoard(p2,board);
				int state3 = getStateOfPositionFromBoard(p3,board);
				
				//if they have the same state (marked by same player) and they are not all marked.
				if(state0 == state1 && state1 == state2 && state2 == state3 && state0!=0)
				{
					if (state0 == player) {
						return score+10;
					}
					else if (state0 == player+1 || state0 == player-1) {
						return score-10;
					}
				}
			}
		}
		return 0;
	}
	//implements minimax to minimizie potential loss and maximize potential gain
	private 	int minimax(List<positionTicTacToe> board, int depth, int player, boolean isMax, int alpha, int beta) {
		int score = evaluate(board,player);
		int opponent = 0;
		int result = 0;
		
		if (player ==1) {
			opponent = 2;
		}
		if (player ==2) {
			opponent = 1;
		}
		if (score == 10) {
			return score;
		}
		
		if (score == -10) {
			return score;
		}
		
		if ((result = isEnded(board))== -1) {
			return 0;
		}	
		
		if (depth == 3) {
			return score;
		}
		
		if (isMax==true)
		{
			int best = -1000;
			for(int i=0;i<board.size();i++)
			{
				if(board.get(i).state==0)
				{
					board.get(i).state= player;
					best = Math.max(best, minimax(board, depth+1, player, !isMax, alpha, beta)-depth);
					board.get(i).state = 0;
					alpha = Math.max(alpha, best);
					
					//alpha-beta pruning
					if (beta <= alpha) {
						break;
					}
				}
			}
			return best;
		}
		
		else
		{
			int best = 1000;
			for(int i=0;i<board.size();i++)
			{
				if(board.get(i).state==0)
				{
					board.get(i).state = opponent;
					best = Math.min(best, minimax(board, depth+1, opponent, !isMax, alpha, beta)+depth);
					board.get(i).state = 0;
					beta = Math.min(beta, best);
					
					//alpha-beta
					if (beta <= alpha) {
						break;
					}
				}
			}
			return best;
		}
		
		
	}
	// implements the minimax values given to find the next best move for the current player
	private positionTicTacToe findBestMove(List<positionTicTacToe> board, int player) {
		int bestVal = -1000;
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);

		for(int i=0;i<board.size();i++)
		{
			if(board.get(i).state==0)
			{
				board.get(i).state= player;
				int moveVal = minimax(board, 0, player, false, -1000, 1000); 
				board.get(i).state= 0;
				
				if (moveVal>bestVal)
				{
					myNextMove.x = board.get(i).x;
					myNextMove.y = board.get(i).y;
					myNextMove.z = board.get(i).z;

				}

			}
		}
		
		return myNextMove;
	}
	
	
	//test whether the current game is ended
	private int isEnded(List<positionTicTacToe> board)
	{
		
		//brute-force
		for(int i=0;i<winningLines.size();i++)
		{
			
			positionTicTacToe p0 = winningLines.get(i).get(0);
			positionTicTacToe p1 = winningLines.get(i).get(1);
			positionTicTacToe p2 = winningLines.get(i).get(2);
			positionTicTacToe p3 = winningLines.get(i).get(3);
			
			int state0 = getStateOfPositionFromBoard(p0,board);
			int state1 = getStateOfPositionFromBoard(p1,board);
			int state2 = getStateOfPositionFromBoard(p2,board);
			int state3 = getStateOfPositionFromBoard(p3,board);
			
			//if they have the same state (marked by same player) and they are not all marked.
			if(state0 == state1 && state1 == state2 && state2 == state3 && state0!=0)
			{
				//someone wins
				p0.state = state0;
				p1.state = state1;
				p2.state = state2;
				p3.state = state3;
				
				return state0;
			}
		}
		for(int i=0;i<board.size();i++)
		{
			if(board.get(i).state==0)
			{
				//game is not ended, continue
				return 0;
			}
		}
		return -1; //call it a draw
	}
	
	private List<List<positionTicTacToe>> initializeWinningLines()
	{
		//create a list of winning line so that the game will "brute-force" check if a player satisfied any 	winning condition(s).
		List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();
		
		//48 straight winning lines
		//z axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,j,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//y axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,j,-1));
				winningLines.add(oneWinCondtion);
			}
		//x axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,j,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 main diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,0,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,0,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,3,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 anti diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,3,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,3,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,3,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,0,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//4 additional diagonal winning lines
		List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,3,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,0,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(3,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(0,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,3,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,0,3,-1));
		winningLines.add(oneWinCondtion);	
		
		return winningLines;
		
	}
	public aiTicTacToe(int setPlayer)
	{
		player = setPlayer;
		
	}
}
