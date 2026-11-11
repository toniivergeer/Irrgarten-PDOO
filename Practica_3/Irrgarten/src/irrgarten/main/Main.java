package irrgarten.main;

import irrgarten.Game;
import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;

public class Main {

    public static void main(String[] args) {
        TextUI view = new TextUI();
        Game game = new Game(2);
        Controller controller = new Controller(game, view);

        controller.play();
    }
}