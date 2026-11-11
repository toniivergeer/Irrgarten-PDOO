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
    private Player currentPlayer;
    private String log;
    
    
    // ----------------- CONSTRUCTOR -----------------
    
    /**
     * @brief Constructor de Game.
     * @param nplayers Número de jugadores de la partida.
     */
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
        currentPlayer = players.get(currentPlayerIndex);

        labyrinth = new Labyrinth(ROWS, COLS, EXIT_ROW, EXIT_COL);
        configureLabyrinth();
        labyrinth.spreadPlayers(players);    
    }
        
    // ----------------- METODOS PUBLIC -----------------
    
    /**
     * @brief Consulta si el juego ya tiene un ganador. 
     * @return True si hay ganador. False otherwise.
     */
    public boolean finished(){
        return(labyrinth.haveAWinner());
    }
    
    /**
    * @brief Ejecuta un paso del juego para el jugador actual.
    *
    * @param preferredDirection Dirección de movimiento solicitada por el jugador.
    * @return true si la partida ha terminado, false en caso contrario.
    */
    public boolean nextStep(Directions preferredDirection){
        log = "";
        boolean dead = currentPlayer.dead();
        
        if(!dead){
            Directions direction = actualDirection(preferredDirection);
            if(direction != preferredDirection){
               logPlayerNoOrders();
            }
           
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
           
            if (monster == null){
               logNoMonster();
            }
            else{
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        }
        
        else{
            manageResurrection();
        }
        
        boolean endGame = finished();
        
        if(!endGame){
            nextPlayer();
        }
        
        return endGame;
    }
    
    /**
     * @brief Devuelve toda la información relacionada con el estado del juego.
     * @return Estado del juego.
     */
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
    
    /**
     * @brief Se configura el laberinto.
     * @post Laberinto creado con un total de N_MONSTRUOS repartidos aleatoriamente
     * por el mapa.
     */
    private void configureLabyrinth(){
        labyrinth.addBlock(Orientation.HORIZONTAL, EXIT_ROW, EXIT_COL, ROWS);
        labyrinth.addBlock(Orientation.VERTICAL, EXIT_ROW, EXIT_COL, ROWS);
        
        for(int i = 0; i < N_MONSTRUOS; i++){
            int[] pos = labyrinth.randomEmptyPos();
            
            Monster monster = new Monster("Monster " + i, Dice.randomIntelligence(), 
                    Dice.randomStrength());
            
            labyrinth.addMonster(pos[0], pos[1], monster);
            monsters.add(monster);
        }
    }
    
    /**
     * @brief Se le da el turno al siguiente jugador.
     * @post currentPlayerIndex refernecia ahora al siguiente jugador.
     */
    private void nextPlayer(){
        currentPlayerIndex++;
        if(currentPlayerIndex >= players.size()){
            currentPlayerIndex = 0;
        }
    }
    
    /**
     * @brief Consulta los movimientos válidos desde la posición del jugador y
     * realiza el movimiento correspondiente.
     * @param preferredDirection Dirección preferida por el usuario.
     * @return Movimiento correspondiente según la validez.
     */
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        
        ArrayList<Directions> validMoves = 
                labyrinth.validMoves(currentRow, currentCol);
        
        return currentPlayer.move(preferredDirection, validMoves);
        
    }
    
    /**
     * @brief Ejecuta el combate entre el jugador actual y el monstruo.
     * @param monster Monstruo con el que se combate.
     * @return Ganador del combate.
     */
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        float playerAttack = currentPlayer.attack();
        
        boolean lose = monster.defend(playerAttack);
        
        while((!lose) && rounds < MAX_ROUNDS){
            winner = GameCharacter.MONSTER;
            rounds++;
            
            
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            
            if(!lose){
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
                
            }
        }
        
        logRounds(rounds,MAX_ROUNDS);
        
        return winner;   
    }
    
    /**
     * @brief Otorga una recompensa al jugador si es vencedor.
     * @post Quedan apuntados los resultados en log.
     * @param winner 
     */
    private void manageReward(GameCharacter winner){
        if(winner == GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            logPlayerWon();
        }
        else{
            logMonsterWon();
        }
    }
    
    /**
     * @brief Gestiona el proceso de resurrección del jugador.
     * @post Jugador revivido o no.
     * @post Resultado declarado en log.
     */
    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();
        
        if(resurrect){
            currentPlayer.resurrect();
            logResurrected();
        }
        else{
            logPlayerSkipTurn();
        }
    }
    
    /**
     * @brief Añade en log si el jugador actual ha ganado el combate.
     */
    private void logPlayerWon(){
        log += players.get(currentPlayerIndex).getName() + 
                " ha ganado el combate \n";
    }
    
    /**
     * @brief Añade en log si el monstruo ha ganado el combate.
     */
    private void logMonsterWon(){
        log += "El monstruo ha ganado el combate \n";
    }
    
    /**
     * @brief Añade en log si el jugador actual ha resucitado.
     */
    private void logResurrected(){
        log += players.get(currentPlayerIndex).getName() + " ha resucitado \n";
    }
    
    /**
     * @brief Añade en log si el jugador actual ha perdido el turno.
     */
    private void logPlayerSkipTurn(){
        log += players.get(currentPlayerIndex).getName() +
                " ha perdido el turno, al estar muerto \n";
    }
    
    /**
     * @brief Añade en log mensaje de error si el jugador no ha podido seguir
     * la instrucción del usuario.
     */
    private void logPlayerNoOrders(){
        log += players.get(currentPlayerIndex).getName() +
                " no ha podido seguir la instrucción solicitada \n";
    }
    
    /**
     * @brief Añade en log si el jugador no ha podido moverse, o lo ha hecho en
     * una casilla vacía.
     */
    private void logNoMonster(){
        log += players.get(currentPlayerIndex).getName() +
                " se ha movido a una casilla vacía o no ha sido posible moverse \n";
    }
    
    /**
     * @brief Añade en log el número de rondas de un combate producido.
     * @param rounds Rondas producidas.
     * @param max Máximo de rondas permitidas.
     */
    private void logRounds(int rounds, int max){
        log += "Se han producido " + rounds + " de " + max + " rondas posibles \n";
    }
    
    
    
}
