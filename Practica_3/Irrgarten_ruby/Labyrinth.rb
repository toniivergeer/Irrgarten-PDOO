#encoding:utf-8

module Irrgarten
    
    # Representa un laberinto de Irrgarten, donde se produce el juego.
    class Labyrinth

        BLOCK_CHAR = 'X'
        EMPTY_CHAR = '-'
        MONSTER_CHAR = 'M'
        COMBAT_CHAR = 'C'
        EXIT_CHAR = 'E'
        ROW = 0
        COL = 1

        # ----------------- CONSTRUCTOR -----------------

        # Constructor de Labyrinth.
        # @param n_rows [Integer] Número de filas del Labyrinth.
        # @param n_cols [Integer] Número de columnas del Labyrinth.
        # @param exit_row [Integer] Fila de la casilla de salida.
        # @param exit_col [Integer] Columna de la casilla de salida.
        def initialize(n_rows, n_cols, exit_row, exit_col)
            @n_rows = n_rows
            @n_cols = n_cols

            @monsters = Array.new(@n_rows) { Array.new(@n_cols) }
            @players = Array.new(@n_rows) { Array.new(@n_cols) }
            @labyrinth = Array.new(@n_rows) { Array.new(@n_cols, EMPTY_CHAR) }
            # Llenamos directamente la matriz labyrinth de EMPTY_CHAR, sin hacer 
            # dos estructuras for de manera manual. 

            @players = [@n_rows][@n_cols]
            @labyrinth = [@n_rows][@n_cols]

            @exit_row = exit_row
            @exit_col = exit_col

            @labyrinth[@exit_row][@exit_col] = EXIT_CHAR

        end #initialize

        # ----------------- METODOS PUBLIC -----------------

        public 
        
        # Distribuye los jugadores en posiciones aleatorias del Labyrinth.
        # @param players [Array] Array de jugadores a distribuir.
        def spread_players(players)
            players.each do |p|
                pos = random_empty_pos()
                put_player_2D(-1, -1, pos[ROW], pos[COL], p)
            end #each
        end #spread_players

        # Consulta si  hay un jugador en la casilla de salida.
        # @return [Boolean] true si hay un jugador en la casilla de salida, false en caso contrario.
        def have_a_winner
            return @players[@exit_row][@exit_col] != nil
        end #have_a_winner

        # Muestra el estado del laberinto.
        # @return [String] Representación en String del laberinto.
        def to_s
            lab_represent = ""

            @labyrinth.each do |row|
                row.each do |pos|
                    lab_represent += pos
                end #each
                lab_represent += "\n"
            end #to_s

            return lab_represent
        end #to_s
        
        # Añade un monstruo en una pos del laberinto.
        # @param row [Integer] Fila de la casilla donde se quiere añadir el monstruo.
        # @param col [Integer] Columna de la casilla donde se quiere añadir el monstruo.
        # @param monster [Monster] Monstruo que se quiere añadir.
        def add_monster(row, col, monster)
            if (pos_OK(row,col) && @labyrinth[row][col] == EMPTY_CHAR)
                @monsters[row][col] = monster
                @labyrinth[row][col] = MONSTER_CHAR
            end #if
        end #add_monster

        # Coloca un jugador en una dirección concreta del laberinto.
        # @param direction [Directions] Dirección en la que se quiere colocar el jugador.
        # @param player [Player] Jugador que se quiere colocar.
        def put_player(direction, player)
            old_row = player.get_row()
            old_col = player.get_col()

            new_pos = dir2pos(old_row, old_col,direction)

            monster = put_player_2D(old_row, old_col, new_pos[ROW], new_pos[COL], player)

            return monster
        end #put_player

        # Añade un bloque de longitud "length" en una dirección concreta del laberinto.
        # @param orientation [Orientations] Orientación en la que se quiere colocar el bloque.
        # @param start_row [Integer] Fila de la casilla donde se quiere añadir el bloque.
        # @param start_col [Integer] Columna de la casilla donde se quiere añadir el bloque.
        # @param length [Integer] Longitud del bloque que se quiere añadir.
        def add_block(orientation, start_row, start_col, length)
            if(orientation == Orientations::VERTICAL)
                inc_row = 1
                inc_col = 0
            else
                inc_row = 0
                inc_col = 1
            end #if-else
            
            @row = start_row
            @col = start_col

            while( (pos_OK(@row,@col)) && (empty_pos(@row,@col)) && (length > 0))
                @labyrinth[@row][@col] = BLOCK_CHAR
                @row += inc_row
                @col += inc_col
                length -= 1
            end #while
        end #add_block

        # Lista las direcciones válidas desde una pos determinada.
        # @param row [Integer] Fila de la casilla desde la que se quieren listar las direcciones válidas.
        # @param col [Integer] Columna de la casilla desde la que se quieren listar las direcciones válidas.
        # @return [Array] Array de direcciones válidas desde la casilla (row, col).
        def valid_moves(row,col)
            output = []

            if(can_step_on(row+1,col))
                output << Directions::DOWN
            end #if

            if (can_step_on(row-1,col))
                output << Directions::UP
            end #if

            if (can_step_on(row,col+1))
                output << Directions::RIGHT
            end #if

            if (can_step_on(row,col-1))
                output << Directions::LEFT
            end #if

            return output
        end #valid_moves

        # ----------------- METODOS PROTECTED -----------------
    
        protected
 
        # Genera posiciones aeatorias hasta encontrar una con una casilla vacía.
        # @return [Array] Coordenadas de la casilla vacía (si encuentra una).
        def random_empty_pos
            begin
                row = Dice.random_pos(@n_rows)
                col = Dice.random_pos(@n_cols)
            end while(!empty_pos(row,col))

            return [row, col]
        end #random_empty_pos

        # ----------------- METODOS PRIVATE -----------------
    
        private

        # Comprueba si una posición es válida dentro del laberinto.
        # @param row [Integer] Fila de la casilla que se quiere comprobar. 
        # @param col [Integer] Columna de la casilla que se quiere comprobar.
        # @return [Boolean] true si la posición es válida, false en caso contrario.
        def pos_ok(row,col)
            return (row >= 0 && row < @n_rows && col >= 0 && col < @n_cols)
        end #pos_ok

        # Comprueba si una posición es válida y está vacía dentro del laberinto.
        # @param row [Integer] Fila de la casilla que se quiere comprobar.
        # @param col [Integer] Columna de la casilla que se quiere comprobar.
        # @return [Boolean] true si la posición es válida y está vacía, false en caso contrario.
        def empty_pos(row,col)
            return ((pos_ok(row,col)) && (@labyrinth[row][col] == EMPTY_CHAR))
        end #empty_pos

        # Comprueba si una posición es válida y contiene un monstruo dentro del laberinto.
        # @param row [Integer] Fila de la casilla que se quiere comprobar.
        # @param col [Integer] Columna de la casilla que se quiere comprobar.
        # @return [Boolean] true si la posición es válida y contiene un monstruo, false en caso contrario.
        def monster_pos(row,col)
            return ((pos_ok(row,col)) && (@labyrinth[row][col] == MONSTER_CHAR))
        end #monster_pos

        # Comprueba si una posición es válida y contiene la salida dentro del laberinto.
        # @param row [Integer] Fila de la casilla que se quiere comprobar.
        # @param col [Integer] Columna de la casilla que se quiere comprobar.
        # @return [Boolean] true si la posición es válida y contiene la salida, false en caso contrario.
        def exit_pos(row,col)
            return ((pos_ok(row,col)) && (@labyrinth[row][col] == EXIT_CHAR))
        end #exit_pos

        # Comprueba si una posición es válida y contiene un área de combate dentro del laberinto.
        # @param row [Integer] Fila de la casilla que se quiere comprobar.
        # @param col [Integer] Columna de la casilla que se quiere comprobar.
        # @return [Boolean] true si la posición es válida y contiene un área de combate, false en caso contrario.
        def combat_pos(row,col)
            return ((pos_ok(row,col)) && (@labyrinth[row][col] == COMBAT_CHAR))
        end #combat_pos

        # Comprueba si una posición es válida y se puede pisar dentro del laberinto.
        # @param row [Integer] Fila de la casilla que se quiere comprobar.
        # @param col [Integer] Columna de la casilla que se quiere comprobar.
        # @return [Boolean] true si la posición es válida y se puede pisar, false en caso contrario.
        def can_step_on(row,col)
            return ((pos_ok(row,col)) && (empty_pos(row,col) || monster_pos(row,col) || exit_pos(row,col)))
        end #can_step_on

        # Actualiza la posición anterior de un jugador en el laberinto.
        # @param row [Integer] Fila de la casilla que se quiere actualizar.
        # @param col [Integer] Columna de la casilla que se quiere actualizar.
        def update_old_pos(row,col)
            if(pos_ok(row,col))
                if(@labyrinth[row][col] == COMBAT_CHAR)
                    @labyrinth[row][col] = MONSTER_CHAR
                else
                    @labyrinth[row][col] = EMPTY_CHAR
                end #if-else
            end #if
        end #update_old_pos

        # Convierte una dirección en una posición dentro del laberinto.
        # @param row [Integer] Fila de la casilla desde la que se quiere calcular la nueva posición.
        # @param col [Integer] Columna de la casilla desde la que se quiere calcular la nueva posición.
        # @param direction [Directions] Dirección en la que se quiere calcular la nueva posición.
        # @return [Array] Array con la nueva posición calculada.
        def dir2pos(row,col,direction)
            pos = [row, col]

            switch direction
            when Directions::UP
                pos[0] -= 1
            when Directions::DOWN
                pos[0] += 1
            when Directions::LEFT
                pos[1] -= 1
            when Directions::RIGHT
                pos[1] += 1
            end #switch

            return pos
        end #dir2pos

        def put_player_2D(old_row, old_col, row, col, player)
            output = nil

            if (can_step_on(row,col))

                if(pos_ok(old_row,old_col))
                    p = @players[old_row][old_col]

                    if(p == player)
                        update_old_pos(old_row,old_col)
                        @players[old_row][old_col] = nil
                    end #if
                end #if

                pos_monster = monster_pos(row,col)

                if(pos_monster)
                    @labyrinth[row][col] = COMBAT_CHAR
                    output = @monsters[row][col]
                else
                    number = player.get_number()
                    @labyrinth[row][col] = number
                end #if-else

                @players[row][col] = player
                player.set_pos(row,col)
            end #if
            
            return output
        end #put_player_2D

