#encoding :utf-8

module Irrgarten
  require_relative 'Dice'
  require_relative 'Directions'
  require_relative 'GameCharacter'
  require_relative 'Orientation'
  require_relative 'Shield'
  require_relative 'Weapon'

  class TestP1

    puts "---------------------------------------"
    puts "           TESTS PRÁCTICA 1            "
    puts "---------------------------------------"
    puts ""
    
    
    
    puts "------ Tests Valores Enum -------"
    puts "Direccion: #{Irrgarten::Directions::UP}"
    puts "Oritentacion: #{Irrgarten::Orientation::HORIZONTAL}"
    puts "Personaje: #{Irrgarten::GameCharacter::PLAYER}"
    puts ""
    



    puts "------ Tests Clase Weapon -------"

    weapon = Irrgarten::Weapon.new(Dice.weapon_power(), Dice::MAX_USES)
    puts "Weapon (Estado inicial): #{weapon.to_s}"
    for i in 1..(Irrgarten::Dice::MAX_USES + 1) do
      puts "Test de ataque del arma (#{i}º intento):"
      puts "- Potencia del arma: #{weapon.attack}"
      puts "- Estado del arma tras el ataque: #{weapon.to_s}"
      puts "- ¿Se descarta el arma? #{weapon.discard}"
    end
    puts ""




    puts "------ Tests Clase Shield -------"

    shield = Irrgarten::Shield.new(Dice.shield_power(), Dice::MAX_USES)
    puts "Shield (Estado inicial): #{shield.to_s}"
    for i in 1..(Irrgarten::Dice::MAX_USES + 1) do
      puts "Test de ataque del escudo (#{i}º intento):"
      puts "- Potencia del escudo: #{shield.protect}"
      puts "- Estado del escudo tras el ataque: #{shield.to_s}"
      puts "- ¿Se descarta el escudo? #{shield.discard}"
    end
    puts ""




    puts "------ Tests Clase Dice -------"

    test = 100


    puts "Comprobacion generacion de atributos y recompensas:"
    puts "Random intelligence: #{Dice.random_intelligence()}"
    puts "Random strength: #{Dice.random_strength()}"
    puts "Weapons reward: #{Dice.weapons_reward()}"
    puts "Shields reward: #{Dice.shields_reward()}"
    puts "Health reward: #{Dice.health_reward()}"
    puts ""


    puts "Comprobacion de resurrect_player():"
    resurrecciones = 0
    
    test.times do
      if Dice.resurrect_player()
        resurrecciones += 1
      end 
    end
    puts "Resurrecciones en #{test} intentos: #{resurrecciones * 100 / test}%
    Esperado: #{Dice::RESURRECT_PROB * 100}%"
    puts ""

    puts "Comprobacion de random_pos():"
    max = 10 #suponemos que la posicion maxima es 10 
    posiciones = Array.new(max, 0) #creamos un array de tamaño max y elementos a 0
    
    test.times do
      posiciones[Dice.random_pos(max)] += 1
    end
    
    puts "Posiciones generadas en #{test} intentos:"
    max.times do |i| #añadimos el atributo i dentro del bloque
      puts "- Posicion #{i}: #{posiciones[i]} veces"
    end
    puts "Esperado: #{test / max} para cada posicion"
    puts""


    puts "Comprobacion discard_element():"
    discards_no_usos = 0
    discards_max_usos = 0
    discards_x_usos = 0
    x_usos = 1 #suponemos que el elemento tiene 1 uso

    test.times do
      if Irrgarten::Dice.discard_element(0)
        discards_no_usos += 1
      end
      if Irrgarten::Dice.discard_element(x_usos)
        discards_x_usos += 1
      end
      if Irrgarten::Dice.discard_element(Dice::MAX_USES)
        discards_max_usos += 1
      end
    end

    esperado_x = (1.0 - (x_usos.to_f / Dice::MAX_USES)) * 100

    puts "- Descartado con 0 usos: #{discards_no_usos} de #{test} veces. Esperado: 100%"
    puts "- Descartado con #{x_usos} uso(s): #{discards_x_usos} de #{test} veces. Esperado: #{esperado_x}%"
    puts "- Descartado con max usos: #{discards_max_usos} de #{test} veces. Esperado: 0%"
    puts ""


    puts "Comprobacion de who_starts():"
    nplayers = 5 #suponemos 5 jugadores
    starts = Array.new(nplayers, 0) #creamos un array de tamaño nplayers y elementos a 0

    test.times do
      starts[Irrgarten::Dice.who_starts(nplayers)] += 1
    end

    puts "Total: #{nplayers} jugadores."
    puts "Jugador que empieza en #{test} intentos:"
    nplayers.times do |i|
      puts "- Jugador #{i}: #{starts[i]} veces"
    end
    puts ""


    puts "Comprobacion de intensity():"
    competence_main = 6.5 #suponemos que la competencia del jugador principal es 6.5
    intensity = Dice.intensity(competence_main)
    puts "Intensidad para competencia, con #{competence_main} maxima: #{intensity}"
    puts ""
  end #class TestP1

end

Irrgarten:TestP1.main