#encoding:utf-8

module Irrgarten
    
    # Representa un monstruo de Irrgarten
    class Monster

        VOID = -1
        INITIAL_HEALTH = 5

        # ----------------- CONSTRUCTOR -----------------

        # Constructor de monstruo.
        # @param name [String] Nombre del monstruo.
        # @param intelligence [Float] Valor de inteligencia del monstruo.
        # @param strength [Float] Valor de fuerza del monstruo.
        def initialize(name,intelligence,strength)
            @name = name
            @intelligence = intelligence
            @strength = strength
            @health = INITIAL_HEALTH
            @row = VOID
            @col = VOID
        end #initialize

        # ----------------- METODOS PUBLIC -----------------

        public 

        # Comprueba si el monstruo está muerto
        # @return [Boolean] True si health <= 0. False en caso contrario.
        def dead
            return (@health <= 0)
        end #dead

        # Decide aleatoriamente la potencia de ataque del monstruo.
        # @return [Float] Potencia de ataque.
        def attack
            return Dice.intensity(@strength)
        end #attack

        # Defensa del monstruo ante ataque de jugador (y si muere por ello, o no).
        # @param received_attack [FLoat] Valor del ataque que recibe.
        # @return [Boolean] True si el monstruo muere. False en caso contrario.
        def defend(received_attack)
            is_dead = dead()

            if(!is_dead)
                defensive_energy = Dice.intensity(@intelligence)
                
                if(defensive_energy < received_attack)
                    got_wounded()
                    is_dead = dead()
                end #if
            end #if

            return is_dead
        end #defend

        # Establece la posición del monstruo.
        # @param row [Integer] Fila de la casilla establecida.
        # @param col [Integer] Columna de la casilla establecida.
        # @post El objeto tiene establecido una posición marcada por (row,col).
        def set_pos(row,col)
            @row = row
            @col = col
        end #set_pos

        # Redacta las estadísticas del monstruo.
        # @return [String] Cadena con el nombre, posición y atributos del monstruo.
        def to_s
            return "Monster: #{@name}\n \
            - Health: #{@health}\n \
            - Strength: #{@strength}\n \
            - Intelligence: #{@intelligence}\n \
            - (x,y): (#{@row},#{@col})"
        end #to_s

        # ----------------- METODOS PRIVATE -----------------
    
        private

        # Disminuye en una unidad la vida del monstruo.
        def got_wounded
            @health -= 1
        end #got_wounded