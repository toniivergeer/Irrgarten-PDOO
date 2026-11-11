
package irrgarten.UI;

import irrgarten.Directions;
import irrgarten.GameState;
import java.util.Scanner;


public class TextUI {
    
    private static Scanner in = new Scanner(System.in);
    
    private char readChar() {
        String s = in.nextLine();     
        return s.charAt(0);
    }
    
    public Directions nextMove() {
        System.out.print("Where? ");
        
        Directions direction = Directions.DOWN;
        boolean gotInput = false;
        
        while (!gotInput) {
            char c = readChar();
            switch(c) {
                case 'w' -> {
                    System.out.print(" UP\n");
                    direction = Directions.UP;
                    gotInput = true;
                }
                case 's' -> {
                    System.out.print(" DOWN\n");
                    direction = Directions.DOWN;
                    gotInput = true;
                }
                case 'd' -> {
                    System.out.print("RIGHT\n");
                    direction = Directions.RIGHT;
                    gotInput = true;
                }
                case 'a' -> {
                    System.out.print(" LEFT\n");
                    direction = Directions.LEFT;
                    gotInput = true;
                }
            }
        }    
        return direction;
    }
    
    public void showGame(GameState gameState) {
        System.out.println("\n========== IRRGARTEN ==========");

        System.out.println("\nLaberinto:");
        System.out.println(gameState.getLabyrinth());

        System.out.println("\nJugadores:");
        System.out.println(gameState.getPlayers());

        System.out.println("\nMonstruos:");
        System.out.println(gameState.getMonsters());

        System.out.println("\nTurno del jugador: "
                + gameState.getCurrentPlayer());

        System.out.println("\nRegistro:");
        System.out.println(gameState.getLog());

        if (gameState.getWinner()) {
            System.out.println("\n¡FIN DE LA PARTIDA! ¡Tenemos ganador!");
        }

        System.out.println("===============================");
}
    
}
