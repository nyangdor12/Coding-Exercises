//this was assignment for machine learning class in okanagan college. went up to silver.
//I made it so whatever move the opponent makes in the small board, next move I made is the same position of the opponent in the large board

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

 
class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int[] board = new int[81];
        int[] bigBoard = new int[9];
        ArrayList<Integer> track = new ArrayList<>();
        track.add(0); track.add(3); track.add(6); track.add(27); track.add(30); track.add(33); track.add(54); track.add(57); track.add(60);
        //boolean playerIsX = false;
        //int[] track = {0, 3, 6, 27, 30, 33, 54, 57, 60};
        // 1 == '_'
        // 2 == 'O' (opponent)
        // 3 == 'X' (me)

        // game loop
        while (true) {
            int opponentRow = in.nextInt();
            int opponentCol = in.nextInt();

            int validActionCount = in.nextInt();
            System.err.println("valid action count is " + validActionCount);
            for (int i = 0; i < validActionCount; i++) {
                int row = in.nextInt();
                int col = in.nextInt();
                //System.err.println(row + " " + col);
                board[(row * 9) + col] = 1;
            }
            if (opponentRow != -1 || opponentCol != -1) {
                board[(opponentRow * 9) + opponentCol] = 2;
            } 

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            int iniRow = opponentRow; int iniCol = opponentCol;
            //by the way the whole time i had 2 for maxPlayer and 3 for minPlayer, it should be opposite, or doesn't that matter
            if (opponentCol % 3 == 2){
                iniCol = 6;
            } else if (opponentCol % 3 == 0){
                iniCol = 0;
            } else if (opponentCol % 3 == 1) {
                iniCol = 3;
            }
            if (opponentRow % 3 == 2){
                iniRow = 6;
            } else if (opponentRow % 3 == 0){
                iniRow = 0;
            } else if (opponentRow % 3 == 1) {
                iniRow = 3;
            }
            
            //what i need to do is make an outer loop that goes i+=3 when i % 9 is not 8, and i+=19 when 
            //it is. then in the smaller loop, i'm gonna copy and paste what i wrote for minimax loop,
            //then I'm gonna keep track of counts of occupied positions and return the highest count,
            //set initialPos to the first element of that square
            //BUT IT COUNTS THE BOARDS THAT ARE ALREADY FULL!

            //WHAT IF I SCRAPE ALL THIS, JUST CALL CHECKWINNER AT EACH FIRST POSITIONS
            //AND THEN IF THE CHECKWINNER RETURNS A 0, THEN THE WINNER IS NOT DECIDED
            //ON THAT BOARD YET. NOTE THAT WHEN USED WITH MINIMAX IT POINTS TO A SPOT WHERE
            //ROW, COL OR DIAG CAN BE MADE IN WHATEVER MOVES, USED ALONE IT SHOULD BE ABLE TO 
            //TELL IF THAT BOARD HAD A WINNER.
            
            int initialPos = (iniRow * 9) + iniCol;
            System.err.println("initialPos " + initialPos);
            
            //WHAT IF WHEN I NEED TO CHOOSE A NEW BOARD BECAUSE THE TOTAL OF 
            //ALL BOARDS ARE LESS THAN 9 BUT I STILL HAVE TO FIND A NEW BOARD?
            if (validActionCount > 9){
                int i = 0;
            
                while (i < board.length){
                    if (/*checkWinner(board, i) == 0 && */bigWinner(board) != 0) {
                            System.err.println("the position is " + bigWinner(bigBoard));
                            initialPos = bigWinner(bigBoard);
                    }
                    if (checkWinner(board, i) == 0) initialPos = i;
                    //System.err.println("i is " + i);
                

                    if(i % 9 == 6) i += 21;
                    else i+=3;
                    
                }
            }
                
            System.err.println("the initialPos now is " + initialPos);
            System.err.println(Arrays.toString(bigBoard));
            //System.err.println("the best count is " + bestCount);

            
            //System.err.println(Arrays.toString(board));

            

            
            if (opponentRow == -1 || opponentCol == -1) {
                System.out.println("4 4");
                board[40] = 3;
            }
            else bestMove(board, opponentRow, opponentCol, validActionCount, initialPos, track);
            //else System.out.println((opponentRow + 3) + " " + (opponentCol + 3));
            //System.out.println("0 0");
            System.err.println("winner is " + checkWinner(board, initialPos));
            if (checkWinner(board, initialPos) == -1) {
                bigBoard[track.indexOf(initialPos)] = 200 + initialPos;
            } else if (checkWinner(board, initialPos) == 1){
                bigBoard[track.indexOf(initialPos)] = 300 + initialPos;
            } else{
                bigBoard[track.indexOf(initialPos)] = 100 + initialPos;
            }

        }
    }

    public static void bestMove(int[] board, int opponentRow, int opponentCol, int validActionCount, int initialPos, ArrayList<Integer> t){
        int bestScore = -10;
        int bestInd = -10;
        
        
        //System.err.println("initial row and col " + iniRow + " " + iniCol + " the initial pos is " + ((iniRow * 9) + iniCol));
        int row = initialPos; 
        while(row < initialPos+21){
            //System.err.println("the row is " + row);
            if(board[row] == 1){
                board[row] = 3;
                int score = 0;
                score = minimax(board, 0, false, validActionCount, initialPos, opponentRow, opponentCol);
                //System.err.println("row: " + row + " score: " + score);
                board[row] = 1;
                if (score > bestScore){
                    bestScore = score;
                    bestInd = row;
                }
            }
            if (row % 3 == 2) row = row + 7;
            else row++;
        }
        //System.err.println("best index is " + bestInd);
        
        
        int newCol = bestInd % 9;
        int newRow = bestInd / 9;
        //ALSO CONSIDER WHEN VALIDCOUNT IS 9 NOT BECAUSE BOARD IS EMPTY, BUT
        //BECAUSE TOTAL SPACE IN 9 X 9 HAS SHRUNK DOWN
        if (validActionCount == 9){

            int pos = initialPos; 
            while(pos < initialPos + 21){
                if ((pos / 9) % 3 == opponentRow % 3 & (pos % 9) % 3 == opponentCol % 3){
                    newCol = pos % 9;
                    newRow = pos / 9;
                }
                if (pos % 3 == 2) pos = pos + 7;
                else pos++;
            }
            bestInd = (newRow * 9) + newCol;
        }
        board[bestInd] = 3;
        //System.err.println("newRow is " + newRow + " and newCol is " + newCol);
        
        System.out.println(newRow + " " + newCol);
    }


    //BASICALLY HOW THIS WORKS IS, AT ANY GIVEN POINT IN A BOARD, IF ANYTHING CAN MAKE ANY
    //SHAPE (ROW, DIAGONAL)IN TWO MOVES THEN IT WILL EITHER RETURN 1 OR -1
    public static int minimax(int[] board, int depth, boolean isMaxPlayer, int validActionCount, int initialPos, int opponentRow, int opponentCol){
        int score = checkWinner(board, initialPos);

        //What if i set my coord to not point to one that's already winner is decided.
        //make another 9 x 9 board that's called bigBoard[] and put that through checkwinner

        //depth = at which point do I want to start calculating definite wins (when there are 2 marks on the board, 4, etc)
        if(score != 0 || depth == 7){
            //System.err.println(score);
            return score;
        }
        //if no more space on that board than it should go to some other board
        if (validActionCount <= 0){
            return 0;
        }


        if(isMaxPlayer){
            int bestScore = -10;
            int row = initialPos; 
            while(row < initialPos + 21){
                if (board[row] == 1){
                    board[row] = 3;
                    score = minimax(board, depth+1, false, validActionCount, initialPos, opponentRow, opponentCol) - depth;

                    board[row] = 1;
                    bestScore = Math.max(score, bestScore);
                }
                if (row % 3 == 2) row = row + 7;
                else row++;
            }
            return bestScore;
        }else{
            int bestScore = 10;
            int row = initialPos;
            while(row < initialPos + 21){
                if(board[row] == 1){
                    board[row] = 2;
                    score = minimax(board, depth+1, true, validActionCount, initialPos, opponentRow, opponentCol) + depth;
                    
                    board[row] = 1;
                    bestScore = Math.min(score, bestScore);
                }
                if (row % 3 == 2) row = row + 7;
                else row++;
            }
            return bestScore;
        }
    }
    //NOW WOULD BE WHERE I COME UP WITH A BRILLIANT PLAN TO DIFFERENTIATE SCORES
    //FOR DIFFERENT MOVES. WHAT WOULD THAT BE.
    public static int bigWinner(int[] board){
        //1 if x wins
        //-1 of O wins
        // 0 otherwise

        
        for (int row = 0; row < 9; row+=3){
            //System.err.println("horiz line is " + row + " " + (row+1) + " " + (row + 2));
            if (board[row]/100 == board[row+1]/100){
                if (board[row]/100 == 3) return board[row+2]%100;
            }
            else if (board[row+1]/100 == board[row+2]/100) {
                if (board[row+1]/100 == 3) return board[row]%100;
            }
        }

        for (int col = 0; col < 3; col++){
            //System.err.println("vert line is " + col + " " + (col+9) + " " + (col+18));
            if (board[col]/100 == board[col+3]/100){
                if (board[col]/100 == 3) return board[col+6]%100;
            }
            else if (board[col+3]/100 == board[col+6]/100){
                if (board[col+3]/100 == 3) return board[col]%100;
            }
        }

        //check main diagonal
        //System.err.println("main digonal is " + initialPos + " " + (initialPos + 10) + " " + (initialPos+20));
        if(board[0]/100 == board[4]/100){
            if(board[0]/100 == 3) return board[8]%100;
        }
         if(board[4]/100 == board[8]/100){
            if(board[0]/100 == 3) return board[0]%100;
        }       
        
        

        //check anti diagonal
        //System.err.println("anti digonal is " + (initialPos+2) + " " + (initialPos + 10) + " " + (initialPos+18));
        if(board[2]/100 == board[4]/100){
            if(board[2]/100 == 3) return board[6]%100;
        }
        if(board[4]/100 == board[6]/100){
            if(board[2]/100 == 3) return board[2]%100;
        }
        return 0;
    }

    public static int closeWin(int[] board, int initialPos){
    //1 if x wins
    //-1 of O wins
    // 0 otherwise
        int count = 0;
        int row = initialPos;
        while(row < initialPos + 21){
            if(board[row] == 3 || board[row] == 2){
                count++;
            }
            if (row % 3 == 2) row = row + 7;
            else row++;
        }
        return count;   
    }

        public static int checkWinner(int[] board, int initialPos){
        //1 if x wins
        //-1 of O wins
        // 0 otherwise

        //now that i have the initial position, i am going to use this initial position
        //to find rows, etc. although can this whole thing be avoided if i use a bfs with
        //but then i need linkedlist. 
        
        for (int row = initialPos; row < initialPos + 21; row+=9){
            //System.err.println("horiz line is " + row + " " + (row+1) + " " + (row + 2));
            if (board[row] == board[row+1] && board[row+1] == board[row+2]){
                if (board[row] == 2) return -1;
                if (board[row] == 3) return 1;
            }
        }

        for (int col = initialPos; col < initialPos + 3; col++){
            //System.err.println("vert line is " + col + " " + (col+9) + " " + (col+18));
            if (board[col] == board[col+9] && board[col+9] == board[col+18]){
                if (board[col] == 2) return -1;
                if (board[col] == 3) return 1;
            }
        }

        
        if(board[initialPos] == board[initialPos + 10] && board[initialPos + 10] == board[initialPos + 20]){
            if(board[initialPos] == 2) return -1;
            if(board[initialPos] == 3) return 1;
        }
        

        //check anti diagonal
        //System.err.println("anti digonal is " + (initialPos+2) + " " + (initialPos + 10) + " " + (initialPos+18));
        if(board[initialPos+2] == board[initialPos+10] && board[initialPos+10] == board[initialPos+18]){
            if(board[initialPos+2] == 2) return -1;
            if(board[initialPos+2] == 3) return 1;
        }
        return 0;
    }
}