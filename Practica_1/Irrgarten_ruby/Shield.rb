#encoding: utf-8

module Irrgarten

  class Shield

    def initialize(protection,uses)
      @protection = protection
      @uses = uses
    end 

    #Como estos métodos no son de clase, no se puede usar self. para invocarlos, 
    #sino que hay que invocarlos directamente
    def protect
      salida = @protection
      
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
      "S[p: #{@protection},#{@uses}]"
    end                                     
  end #class Shield

end #module Irrgarten