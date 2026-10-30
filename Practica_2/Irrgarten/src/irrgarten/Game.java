/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import 

/**
 *
 * @author Toni Verger Vallespir
 */
public class Game {
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private static final int MAX_ROUNDS = 10;
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int EXIT_ROW = 9;
    private static final int EXIT_COL = 9;
    private static final int N_MONSTRUOS = 4;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;
    private int currentPlayerIndex;
    private String log;
    
    
    // ----------------- CONSTRUCTOR -----------------
    
    public Game(int nplayers){
        players = new ArrayList<>();
        monsters = new ArrayList<>();
        log = ""; //registro de eventos

        for (int i = 0; i < nplayers; i++) {
            Player p = new Player(
                (char) ('0' + i), //código 48 + i = 0,1,2,3...
                Dice.randomIntelligence(),
                Dice.randomStrength()
            );

            players.add(p);
        }

        currentPlayerIndex = Dice.whoStarts(nplayers);

        labyrinth = new Labyrinth(ROWS, COLS, EXIT_ROW, EXIT_COL);
        configureLabyrinth();
        labyrinth.spreadPlayers(players);    
    }
        
    // ----------------- METODOS PUBLIC -----------------
    
    public boolean finished(){
        return(labyrinth.haveAWinner());
    }
    
    public boolean nextStep(Directions preferredDirection){
        throw new UnsupportedOperationException();
    }

    public GameState getGameState(){

        String labInfo = labyrinth.toString();

        String playerInfo = "";
        for (Player p : players) {
            playerInfo += p.toString() + "\n";
        }

        String monsterInfo = "";
        for (Monster m : monsters) {
            monsterInfo += m.toString() + "\n";
        }

        return new GameState(
            labInfo,
            playerInfo,
            monsterInfo,
            currentPlayerIndex,
            finished(),
            log
        );
    }
   
    // ----------------- METODOS PRIVATE -----------------
    
    private void configureLabyrinth(){
        throw new UnsupportedOperationException();
    }
    
    private void nextPlayer(){
        currentPlayerIndex++;
        if(currentPlayerIndex >= players.size()){
            currentPlayerIndex = 0;
        }
    }
    
    private Directions actualDirection(Directions preferredDirection){
        throw new UnsupportedOperationException();
    }
    
    private GameCharacter combat(Monster monster){
        throw new UnsupportedOperationException();
    }
    
    private void manageReward(GameCharacter winner){
        throw new UnsupportedOperationException();
    }
    
    private void manageResurrection(){
        throw new UnsupportedOperationException();
    }
    
    private void logPlayerWon(){
        log += players.get(currentPlayerIndex).getName() + 
                " ha ganado el combate \n";
    }
    
    private void logMonsterWon(){
        log += "El monstruo ha ganado el combate \n";
    }
    
    private void logResurrected(){
        log += players.get(currentPlayerIndex).getName() + " ha resucitado \n";
    }
    
    private void logPlayerSkipTurn(){
        log += players.get(currentPlayerIndex).getName() +
                " ha perdido el turno, al estar muerto \n";
    }
    
    private void logPlayerNoOrders(){
        log += players.get(currentPlayerIndex).getName() +
                " no ha podido seguir la instrucción solicitada \n";
    }
    
    private void logNoMonster(){
        log += players.get(currentPlayerIndex).getName() +
                " se ha movido a una casilla vacía o no ha sido posible moverse \n";
    }
    
    private void logRounds(int rounds, int max){
        log += "Se han producido " + rounds + " de " + max + " rondas posibles \n";
    }
    
    
    
}
