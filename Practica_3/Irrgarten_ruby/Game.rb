#encoding:utf-8

module Irrgarten

    # Representa un juego en Irrgarten
    class Game
        
        MAX_ROUNDS = 10
        ROWS = 10
        COLS = 10
        EXIT_ROW = 9
        EXIT_COL = 9
        N_MONSTRUOS = 4

        # ----------------- CONSTRUCTOR -----------------

        def initialize(nplayers)
            @players = []
            @monsters = []
            @log = ""

            for i in 0...nplayers do
                player = Player.new((48+i).chr, Dice.random_intelligence, Dice.random_strength)
                @players << player
            end #for

            @current_player_index = Dice.who_starts(nplayers)
            @current_player = @players[@current_player_index]

            @labyrinth = Labyrinth.new(ROWS,COLS,EXIT_ROW,EXIT_COL)
            configure_labyrinth()
            @labyrinth.spread_players(@players)
        end #initialize

        # ----------------- METODOS PUBLIC -----------------

        public 

        # Consulta si el juego tiene ganador
        # @return [Boolean] true si hay un ganador, false en caso contrario.
        def finished
            return (@labyrinth.have_a_winner())
        end #finished

        # Ejecuta un paso del juego para el jugador actual (movimiento o resurrección).
        # @param preferred_direction [Directions] dirección preferida por el jugador actual.
        # @return [Boolean] true si el juego ha terminado, false en caso contrario.
        def next_step (preferred_direction)
            log = ""
            dead = @current_player.dead()

            if(!dead)
                direction = actual_direction(preferred_direction)

                if(direction != preferred_direction)
                    log_player_no_orders()
                end #if

                monster = @labyrinth.put_player(direction, @current_player)

                if(monster == nil)
                    log_no_monster()
                else
                    winner = combat(monster)
                    manage_reward(winner)
                end #if-else
            
            else
                manage_resurrection()
            end #if-else

            end_game = finished()

            if(!end_game)
                next_player()
            end #if
            
            return end_game
        end #next_step

        # Devuelve el estado del juego.
        # @return [GameState] estado del juego.
        def get_game_state
            lab_info = @labyrinth.to_s

            player_info = ""

            @players.each do |p|
                player_info += p.to_s + "\n"
            end

            monster_info = ""

            @monsters.each do |m|
                monster_info += m.to_s + "\n"
            end

            game_state = GameState.new(lab_info, player_info, monster_info, @current_player_index, finished(),@log)

            return game_state
        end #get_game_state

        # ----------------- METODOS PRIVATE -----------------
    
        private