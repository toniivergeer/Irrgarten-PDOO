#encoding:utf-8

module Irrgarten

  class Weapon

    def initialize(power,uses)
      @power = power
      @uses = uses
    end 

    def attack
      salida = @power
      
      if(@uses > 0)then
        @uses -= 1 #equivalente a @uses = @uses - 1
      else
        salida = 0
      end

      return salida
    end

    def discard
      return Dice.discard_element(@uses)
    end

    def to_s #clase toString()
      "W[p: #{@power},#{@uses}]"
    end                                     
  end #class Weapon

end #module Irrgarten