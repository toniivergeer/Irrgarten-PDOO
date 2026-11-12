#encoding :utf-8

module Irrgarten

    #Representa a un jugador de Irrgarten
    class Player

        MAX_WEAPONS = 2
        MAX_SHIELDS = 3
        INITIAL_HEALTH = 10
        HITS2LOSE = 3
        VOID = -1

        # ----------------- CONSTRUCTOR -----------------
        
        # Inicializa un jugador con sus atributos.
        # @param number [Integer] número del jugador
        # @param intelligence [Float] inteligencia
        # @param strength [Float] fuerza
        def initialize(number,intelligence,strength)
            @number = number
            @name = "Player ##{@number}"
            @intelligence = intelligence
            @strength = strength
            @health = INITIAL_HEALTH
            @row = VOID
            @col = VOID
            @weapons = []
            @shields = []
            @consecutive_hits = 0
        end #initialize

        # ----------------- METODOS PUBLIC -----------------
        public

        # Revive al jugador, reestableciendo su salud y vaciando ambos arsenales.
        # También reestablece golpes consecutivos.
        def resurrect
            @health = INITIAL_HEALTH
            @weapons = []
            @shields = []
            resetHits()
        end #resurrect

        # Consultor.
        # Devuelve la fila del jugador.
        def get_row
            return @row
        end #get_row

        # Consultor.
        # Devuelve la columna del jugador.
        def get_col
            return @col
        end #get_col

        # Consultor.
        # Devuelve el número del jugador.
        def get_number
            return @number
        end #get_number

        # Consultor.
        # Devuelve el nombre del jugador.
        def get_name
            return @name
        end #get_name

        # Establece la posición del jugador.
        # @param row [Integer] Fila de la posición.
        # @param col [Inteer] Columna de la posición.
        def set_pos(row, col)
            @row = row
            @col = col
        end #set_pos

        # Consulta si el jugador está muerto (health <= 0).
        # @return [Boolean] True si está muerto, Falso en caso contrario.
        def dead
            return (@health <= 0)
        end #dead

        # Mueve al jugador en una dirección, consultando si está entre las válidas.
        # @param direction [Directions] dirección en la que se quiere mover.
        # @param valid_moves [Directions[]] array de direcciones válidas.
        # @return [Direction] Dirección en la que se mueve
        def move (direction, valid_moves)
            size = valid_moves.size
            contained = valid_moves.include?(direction)

            if((size > 0) && (!contained))
                return valid_moves[0]
            end #if

            return direction

        end #move

        # Potencia de ataque total del jugador.
        # @return [Float] Potencia total.
        def attack
            return @strength + sumWeapons()
        end #attack

        # Defensa del jugador.
        # @param received_attack [Float] Valor del ataque que recibe el jugador.
        # @return [Boolean] True si el jugador pierde, false en caso contrario.
        def defend(received_attack)
            return manage_hit(received_attack)
        end #defend

        # Entrega las recompensas de armas, escudos y salud al jugador.
        def receive_reward
            w_reward = Dice.weapons_reward()
            s_reward = Dice.shields_reward()

            for i in 1..w_reward
                wnew = new_weapon()
                receive_weapon(wnew)
            end #for

            for i in 1..s_reward
                snew = new_shield()
                receive_shield(snew)
            end #for

            extra_health = Dice.health_reward()
            @health += extra_health
        end #receive_reward
        
        # Redacta las estadísticas del jugador.
        # @return [String] Cadena con los atributos del jugador.
        def to_s
            return "#{@name}\n \
            - Health: #{@health}\n \
            - Intelligence: #{@intelligence}\n \
            - Strength: #{@strength}\n \
            - (x,y): (#{get_row()} , #{get_col()})\n \
            - Consecutive Hits: #{@consecutive_hits}\n"
        end #to_s

        # ----------------- METODOS PRIVATE -----------------
    
        private

        # Recibe un arma y la añade al arsenal (si hay espacio).
        # @param w [Weapon] Arma a recibir.
        # @post Se actualiza el arsenal (descartando las que cumplan la condición).
        def receive_weapon(w)
            @weapons.each do |wi|
                discard = wi.discard()

                if(discard)
                    @weapons.delete(wi)
                end #if

            end #each

            size = @weapons.size()

            if(size < MAX_WEAPONS)
                @weapons << w
            end #if
        end #receive_weapon

        # Recibe un escudo y la añade al arsenal (si hay espacio).
        # @param s [Shield] Escudo a recibir.
        # @post Se actualiza el arsenal (descartando los que cumplan la condición).
        def receive_shield(s)
            @shields.each do |si|
                discard = si.discard()

                if(discard)
                    @shields.delete(si)
                end #if

            end #each

            size = @shields.size()

            if(size < MAX_SHIELDS)
                @shields << s
            end #if
        end #receive_shield

        # Crea una nueva arma, inicializando sus atributos.
        # @return [Weapon] Arma inicializada.
        def new_weapon
            w = Weapon.new(Dice.weapon_power(), Dice.uses_left())
            return w
        end #new_weapon

        # Crea una nuevo escudo, inicializando sus atributos.
        # @return [Shield] Escudo inicializado.
        def new_shield
            s = Shield.new(Dice.shield_power(), Dice.uses_left())
            return s
        end #new_shield

        # Suma la potencia de todas las armas del arsenal del jugador.
        # return [Float] Sumatoria del daño.
        def sum_weapons
            sum_w = 0.0

            @weapons.each do |w|
                sum_w  += w.attack()
            end #each

            return sum_w
        end #sum_weapons


        # Suma la potencia de todas los escudos del arsenal del jugador.
        # return [Float] Sumatoria de los escudos.
        def sum_shields
            sum_s = 0.0

            @shields.each do |s|
                sum_s  += s.defend()
            end #each

            return sum_s
        end #sum_shields

        # Calcula la energía defensiva del jugador (inteligencia y arsenal de escudos).
        # @return [Float] Energía defensiva del jugador.
        def defensive_energy
            return @intelligence + sum_shields()
        end #defensive_energy

        # Gestiona el golpe que recibe un jugador (y si éste pierde o no).
        # @param received_attack [Float] Valor de ataque recibido.
        # @return [Boolean] True si el jugador pierde, False en caso contrario.
        def manage_hit(received_attack)
            defense = defensive_energy()

            if(defense < received_attack)
                got_wounded()
                inc_consecutive_hits()
            
            else
                reset_hits()
            end #if-else
        
            if((@consecutive_hits == HITS2LOSE) || (dead()))
                reset_hits()
                lose = true 
            else
                lose = false 
            end #if-else

            return lose
        end #manage_hit

        # Reestablece los golpes consecutivos a 0.
        def reset_hits
            @consecutive_hits = 0
        end #reset_hits

        # Decrementa en una unidad los puntos de salud.
        def got_wounded
            @health -= 1
        end #got_wounded
        
        # Incrementa en una unidad los golpes consecutivos.
        def inc_consecutive_hits
            @consecutive_hits += 1
        end #inc_consecutive_hits



