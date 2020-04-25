package com.company;
import java.util.Scanner;

class TicTacToe {
    private int turn = 1;
    private int size = 3;
    private int player = -1;
    private int opponent = 1;
    int [][] arr = new int[size][size];
    TicTacToe(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                arr[i][j] = 0;
            }
        }
    }
    boolean Move(){
        Scanner sc =  new Scanner(System.in);
        System.out.printf("Enter x: ");
        int x = sc.nextInt();
        System.out.printf("Enter y: ");
        int y = sc.nextInt();
        if(y > size || x > size || arr[x-1][y-1] != 0){
            System.out.println("Invalid Coordinates");
            return false;
        } else {
            if(turn%2==1){
                arr[x-1][y-1] = 1;
            } else {
                arr[x-1][y-1] = -1;
            }
            turn++;
            return true;
        }
    }
    void Move(MoveStruct move){
        arr[move.x][move.y] = player;
        turn++;
    }
    int Eval() {
        for(int i=0; i < size; i++){
            if(arr[0][i] != 0 && CheckSame(arr[0][i], 0, i, 1,0)){
                if(arr[0][i]==-1) return 5;
                else return -5;
            }
        }

        for(int i=0; i < size; i++){
            if(arr[i][0] != 0 && CheckSame(arr[i][0], i, 0, 0,1)){
                if(arr[i][0]==-1) return 5;
                else return -5;
            }
        }

        if(arr[0][0] != 0 && CheckSame(arr[0][0], 0,0,1,1)) {
            if(arr[0][0] == -1) return 5;
            else return -5;
        }
        if(arr[0][size-1] != 0 && CheckSame(arr[0][size-1], 0,size-1,1,-1)) {
            if(arr[0][size-1] == -1) return 5;
            else return -5;
        }
        return 0;
    }
    void Print(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(arr[i][j] == 1) System.out.printf("X ");
                else if(arr[i][j] == -1) System.out.printf("O ");
                else System.out.printf("- ");
            }
            System.out.println();
        }
    }
    boolean CheckSame(int val, int cellX, int cellY, int deltaX, int deltaY){
        if(cellX > size-1 || cellY > size-1) return true;
        if(cellX < 0 || cellY < 0) return true;
        if(arr[cellX][cellY] != val) return false;
        return CheckSame(val, cellX+deltaX, cellY + deltaY, deltaX, deltaY);
    }
    boolean Win(){
        for(int i=0; i < size; i++)
            if(arr[0][i] != 0 && CheckSame(arr[0][i], 0, i, 1,0)) return true;
        for(int i=0; i < size; i++)
            if(arr[i][0] != 0 && CheckSame(arr[i][0], i, 0, 0,1)) return true;
        if(arr[0][0] != 0 && CheckSame(arr[0][0], 0,0,1,1)) return true;
        if(arr[0][size-1] != 0 && CheckSame(arr[0][size-1], 0,size-1,1,-1)) return true;
        return false;
    }
    void Play(){
        Print();
        while(!Win()){
            while(!Move());
            MoveStruct ai = bestMove();
            Move(ai);
            System.out.println("The AI moved: ");
            Print();
        }
    }
    private class MoveStruct{
        public int x;
        public int y;
    }
    boolean MovesLeft(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(arr[i][j] == 0) return true;
            }
        }
        return false;
    }
    int minimax(int depth, boolean maxi){
        int score = Eval();
        if(score == 5 || score == -5) return score;
        if(!MovesLeft()) return 0;
        if(maxi){
            int bestScore = -500;
            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    if(arr[i][j] == 0){
                        arr[i][j] = player;
                        bestScore = Math.max(bestScore, minimax(depth+1, !maxi));
                        arr[i][j] = 0;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 500;
            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    if(arr[i][j] == 0){
                        arr[i][j] = opponent;
                        bestScore = Math.min(bestScore, minimax(depth+1, !maxi));
                        arr[i][j] = 0;
                    }
                }
            }
            return bestScore;
        }
    }
    MoveStruct bestMove(){
        int val = -500;
        MoveStruct move = new MoveStruct();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(arr[i][j] == 0){
                    arr[i][j] = player;
                    int minimaxVal = minimax(7 , false);
                    arr[i][j] = 0;
                    if(minimaxVal>val){
                        val = minimaxVal;
                        move.x = i;
                        move.y = j;
                    }
                }
            }
        }
        return move;
    }
}
public class Main {
    public static void main(String[] args) {
        TicTacToe T = new TicTacToe();
        T.Play();
    }
}
