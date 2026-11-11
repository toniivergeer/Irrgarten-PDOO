package irrgarten;

import java.util.ArrayList;

/**
 * Pruebas manuales de los métodos implementados en la Práctica 3.
 *
 * Ejecutar después de corregir los errores indicados en Game, Labyrinth y Player.
 * La clase utiliza comprobaciones propias para no depender de que Java tenga
 * activadas las assertions (-ea).
 * 
 * MODELO DE TEST IMPLEMENTADO CON INTELIGENCIA ARTIFICAL,
 * PARA COMPROBAR EL FUNCIONAMIENTO DE LOS MÉTODOS IMPLEMENTADOS.
 */

public class TestP3 {

    private static int pruebas = 0;

    private static void comprobar(boolean condicion, String mensaje) {
        pruebas++;
        if (!condicion) {
            throw new AssertionError("FALLO: " + mensaje);
        }
        System.out.println("OK: " + mensaje);
    }

    public static void main(String[] args) {
        System.out.println("========== TEST P3 ==========");

        probarDice();
        probarWeapon();
        probarShield();
        probarPlayer();
        probarMonster();
        probarLabyrinth();
        probarGame();

        System.out.println("\nPruebas superadas: " + pruebas);
    }

    private static void probarDice() {
        System.out.println("\n--- Dice ---");

        boolean posOK = true;
        boolean intelligenceOK = true;
        boolean strengthOK = true;
        boolean weaponsOK = true;
        boolean shieldsOK = true;
        boolean healthOK = true;

        for (int i = 0; i < 100; i++) {
            posOK &= Dice.randomPos(5) >= 0 && Dice.randomPos(5) < 5;
            intelligenceOK &= Dice.randomIntelligence() >= 0;
            strengthOK &= Dice.randomStrength() >= 0;
            weaponsOK &= Dice.weaponsReward() >= 0;
            shieldsOK &= Dice.shieldsReward() >= 0;
            healthOK &= Dice.healthReward() >= 0;
        }

        comprobar(posOK, "randomPos");
        comprobar(intelligenceOK, "randomIntelligence");
        comprobar(strengthOK, "randomStrength");
        comprobar(weaponsOK, "weaponsReward");
        comprobar(shieldsOK, "shieldsReward");
        comprobar(healthOK, "healthReward");
}

    private static void probarWeapon() {
        System.out.println("\n--- Weapon ---");

        Weapon weapon = new Weapon(4.0f, 2);
        comprobar(weapon.attack() == 4.0f, "el arma ataca mientras tiene usos");
        comprobar(weapon.attack() == 4.0f, "el arma consume el segundo uso");
        comprobar(weapon.attack() == 0.0f, "el arma no ataca cuando se queda sin usos");
    }

    private static void probarShield() {
        System.out.println("\n--- Shield ---");

        Shield shield = new Shield(3.0f, 1);
        comprobar(shield.protect() == 3.0f, "el escudo protege mientras tiene usos");
        comprobar(shield.protect() == 0.0f, "el escudo no protege cuando se queda sin usos");
    }

    private static void probarPlayer() {
        System.out.println("\n--- Player ---");

        Player player = new Player('0', 0.0f, 5.0f);

        comprobar(player.getName().equals("Player #0"), "el constructor asigna el nombre");
        comprobar(player.getNumber() == '0', "el constructor asigna el identificador");
        comprobar(player.getRow() == -1 && player.getCol() == -1,
                "el jugador comienza fuera del laberinto");
        comprobar(!player.dead(), "el jugador comienza vivo");

        ArrayList<Directions> validMoves = new ArrayList<>();
        validMoves.add(Directions.RIGHT);
        validMoves.add(Directions.DOWN);

        comprobar(player.move(Directions.RIGHT, validMoves) == Directions.RIGHT,
                "move conserva una dirección válida");
        comprobar(player.move(Directions.LEFT, validMoves) == Directions.RIGHT,
                "move elige el primer movimiento válido si el solicitado no lo es");

        ArrayList<Directions> noMoves = new ArrayList<>();
        comprobar(player.move(Directions.LEFT, noMoves) == Directions.LEFT,
                "move conserva la dirección si no hay movimientos válidos");

        comprobar(player.attack() == 5.0f, "attack devuelve la fuerza sin armas");

        boolean lose = false;
        lose = player.defend(100.0f);
        comprobar(!lose, "el primer golpe no hace perder al jugador");
        lose = player.defend(100.0f);
        comprobar(!lose, "el segundo golpe no hace perder al jugador");
        lose = player.defend(100.0f);
        comprobar(lose, "el tercer golpe consecutivo hace perder al jugador");

        player.resurrect();
        comprobar(!player.dead(), "resurrect devuelve al jugador a la vida");
    }

    private static void probarMonster() {
        System.out.println("\n--- Monster ---");

        Monster monster = new Monster("Monstruo de prueba", 0.0f, 5.0f);
        comprobar(!monster.dead(), "el monstruo comienza vivo");

        boolean dead = false;
        for (int i = 0; i < 5; i++) {
            dead = monster.defend(100.0f);
        }

        comprobar(dead, "el monstruo muere tras recibir cinco golpes");
        comprobar(monster.dead(), "dead detecta que el monstruo ha muerto");
    }

    private static void probarLabyrinth() {
        System.out.println("\n--- Labyrinth ---");

        Labyrinth labyrinth = new Labyrinth(5, 5, 4, 4);

        // El constructor debe inicializar las casillas vacías y marcar la salida.
        comprobar(!labyrinth.haveAWinner(), "el laberinto comienza sin ganador");

        labyrinth.addBlock(Orientation.HORIZONTAL, 0, 0, 2);
        ArrayList<Directions> moves = labyrinth.validMoves(1, 1);
        comprobar(moves != null, "validMoves devuelve una lista");

        Player player = new Player('1', 0.0f, 5.0f);
        int[] pos = labyrinth.randomEmptyPos();
        // randomEmptyPos es protected: TestP3 está en el mismo paquete.
        comprobar(pos.length == 2, "randomEmptyPos devuelve fila y columna");

        labyrinth.spreadPlayers(new ArrayList<Player>() {{
            add(player);
        }});
        comprobar(player.getRow() >= 0 && player.getCol() >= 0,
                "spreadPlayers asigna una posición al jugador");
    }

    private static void probarGame() {
        System.out.println("\n--- Game ---");

        Game game = new Game(2);
        GameState state = game.getGameState();

        comprobar(state != null, "getGameState devuelve un estado");
        comprobar(state.getLabyrinth() != null && !state.getLabyrinth().isEmpty(),
                "el estado contiene el laberinto");
        comprobar(state.getPlayers() != null && !state.getPlayers().isEmpty(),
                "el estado contiene los jugadores");
        comprobar(state.getMonsters() != null && !state.getMonsters().isEmpty(),
                "el estado contiene los monstruos");
        comprobar(state.getCurrentPlayer() >= 0 && state.getCurrentPlayer() < 2,
                "el índice del jugador actual es válido");

        boolean finished = game.nextStep(Directions.RIGHT);
        comprobar(finished == game.finished(),
                "nextStep devuelve el estado de finalización de la partida");
    }
}
