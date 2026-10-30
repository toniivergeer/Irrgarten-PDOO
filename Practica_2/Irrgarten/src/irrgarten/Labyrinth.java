/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author Toni Verger Vallespir
 */
public class Labyrinth {
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0; 
    private static final int COL = 1;
    
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    private char[][] labyrinth; //Matriz de Estados de las Casillas.
    private Monster[][] monsters; //Referencias a monsters.
    private Player[][] players; //Referencias a players.

    // ----------------- CONSTRUCTOR -----------------
    
    /**
     * @brief Constructor del Labyrinth.
     * @param nRows Número de filas del laberinto.
     * @param nCols Número de columnas del laberinto.
     * @param exitRow Fila de la casilla de salida.
     * @param exitCol Columna de la casilla de salida.
     * @post Genera tres matrices: una referenciando a jugadores, otra a monstruos,
     * y otra para el estado de las casillas.
     * @post Establece la casilla de salida.
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth = new char[nRows][nCols];
        this.exitRow = exitRow;
        this.exitCol = exitCol;
    }
    
    // ----------------- METODOS PUBLIC -----------------
    
    public void spreadPlayers(ArrayList<Player> players){ //P3
        throw new UnsupportedOperationException();
    }
    
    /**
     * @brief Consulta si hay un jugador en la casilla de salida.
     * @return True si hay un jugador en Exit. False otherwise.
     */
    public boolean haveAWinner(){
        return players[exitRow][exitCol] != null;
    }
    
    /**
     * @brief Muestra el estado del laberinto.
     * @return Cadena de texto que representa el laberinto.
     */
    @Override
    public String toString(){
        String labRepresent = "";
        
        for(char[] fila : labyrinth){
            for(char pos : fila){
                labRepresent += pos;
            }
            labRepresent += "\n";
        }
        
        return labRepresent;
    }
    
    /**
     * @brief Añade un monstruo en una posición del laberinto.
     * @param row Fila de la casilla en la que se añade el monstruo.
     * @param col Columna de la casilla en la que se añade el monstruo.
     * @param monster Monstruo que se añade.
     * @post Actualización de casillas en las matrices labyrinth y monsters.
     */
    public void addMonster(int row, int col, Monster monster){
        if (posOK(row, col) && labyrinth[row][col] == EMPTY_CHAR){
            labyrinth[row][col] = MONSTER_CHAR;
            monsters[row][col] = monster;
        }
    }
    
    public Monster putPlayer(Directions direction, Player player){ //P3
        throw new UnsupportedOperationException();
    }
    
    public void addBlock(Orientation orientation, int startRow,
            int startCol, int length){ //P3
        throw new UnsupportedOperationException();
    }
    
    public ArrayList<Directions> validMoves(int row, int col){ //P3
        throw new UnsupportedOperationException();
    }
    
    // ----------------- METODOS PRIVATE -----------------
    
    /**
    * @brief Indica si la casilla está dentro del laberinto.
    * @param row fila de la posicion solicitada.
    * @param col columna de la posicion solicitada.
    * @return True si la posición solicitada es válida.
    */
    private boolean posOK(int row, int col){
       return row <= nRows && row >= 0 && col <= nCols && col >= 0;
    }
   
    /**
    * @brief Indica si la casilla está vacía.
    * @param row fila de la posicion solicitada.
    * @param col columna de la posicion solicitada.
    * @return True si la posición solicitada está vacía.
    */
    private boolean emptyPos(int row, int col){
       return posOK(row,col) && labyrinth[row][col] == EMPTY_CHAR;
    }
   
    /**
    * @brief Indica si en la casilla hay un monstruo.
    * @param row fila de la posicion solicitada.
    * @param col columna de la posicion solicitada.
    * @return True si la posición solicitada hay un monstruo.
    */
    private boolean monsterPos(int row, int col){
       return posOK(row,col) && labyrinth[row][col] == MONSTER_CHAR;
    }
   
    /**
    * @brief Indica si es la casilla de salida.
    * @param row fila de la posicion solicitada.
    * @param col columna de la posicion solicitada.
    * @return True si la posición solicitada es la salida.
    */
    private boolean exitPos(int row, int col){
       return posOK(row,col) && row == exitRow && col == exitCol;
    }
   
    /**
    * @brief Indica si en la casilla hay un combate.
    * @param row fila de la posicion solicitada.
    * @param col columna de la posicion solicitada.
    * @return True si la posición solicitada es de combate.
    */
    private boolean combatPos(int row, int col){
       return posOK(row,col) && labyrinth[row][col] == COMBAT_CHAR;
    }
   
    /**
    * @brief Indica si la posición está dentro del tablero y se corresponde con
    * una de las opciones: casilla vacía, casilla de monstruo, salida.
    * @param row fila de la posicion solicitada.
    * @param col columna de la posicion solicitada.
    * @return True si el player puede situarse sobre esa casilla; si no False.
    */
    private boolean canStepOn(int row, int col){
       return posOK(row,col) && (emptyPos(row,col) || monsterPos(row,col) ||
               exitPos(row,col));
    } 
   
    /**
    * @brief Actualiza la posición (revisada) una vez el jugador abandona la casilla: 
    * si la posición es de combate, se actualiza a una de monstruo. En otro caso, 
    * se actualiza a una posición vacía.
    * 
    * @param row Fila de la casilla a actualizar.
    * @param col Columna de la casilla a actualizar.
    */
    private void updateOldPos(int row, int col){
        if (posOK(row,col)){
            if(labyrinth[row][col] == COMBAT_CHAR){
               labyrinth[row][col] = MONSTER_CHAR;
            }
            else{
               labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }
    
    /**
     * @brief Calcula y devuelve la nueva posición del laberinto al avanzar una 
     * casilla desde las coordenadas indicadas en la dirección especificada
     * (no comprueba si la posición resultante está dentro de los límites).
     * 
     * @param row Fila de la casilla inicial.
     * @param col Columna de la casilla inicial.
     * @param direction Dirección suministrada {UP,DOWN,LEFT,RIGHT}
     * @return Posición a la que se llega tras avanzar en la dirección suministrada.
     */
    private int[] dir2Pos(int row, int col, Directions direction){
        int[] pos = {row,col};
        switch (direction) {
            case UP -> pos[0]--;
            case DOWN -> pos[0]++;
            case LEFT -> pos[1]--;
            case RIGHT -> pos[1]++;
        }
        
        return pos;
    }
    
    /**
     * @brief Genera posiciones aleatorias hasta encontrar una con una casilla
     * vacía (si no existe, se quedará en un bucle infinito).
     * @return Coordenadas de la casilla vacía, en caso de que encuentre una. 
     */
    protected int[] randomEmptyPos(){
        int row, col;
        
        do{
            row = Dice.randomPos(nRows);
            col = Dice.randomPos(nCols);
        } while(!emptyPos(row,col));
        
        return new int[]{row,col};
    }
   
    private Monster putPlayer2D(int oldRow, int oldCol, int row,
            int col, Player player){ //P3
        throw new UnsupportedOperationException();
       
    } 
}
