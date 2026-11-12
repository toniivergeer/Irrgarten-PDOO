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

        def spread_players(players [])
            players.each do |p|

                pos = random_empty_pos()
                put_player_2D(-1, -1, pos[ROW], pos[COL],p)

        # ----------------- METODOS PRIVATE -----------------
    
        private