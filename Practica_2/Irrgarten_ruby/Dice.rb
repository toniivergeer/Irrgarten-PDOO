#encoding:utf-8

module Irrgarten

    class Dice

        MAX_USES = 5
        MAX_INTELLIGENCE = 10
        MAX_STRENGTH = 10
        RESURRECT_PROB = 0.3
        WEAPONS_REWARD = 2
        SHIELDS_REWARD = 3
        HEALTH_REWARD = 5
        MAX_ATTACK = 3
        MAX_SHIELD = 2

        @@generator = Random.new

        def self.random_pos(max)
            return @@generator.rand(0...max)
        end

        def self.who_starts(nplayers)
            return @@generator.rand(0...nplayers)
        end

        def self.random_intelligence
            return @@generator.rand(0...MAX_INTELLIGENCE)
            #rango: [0, MAX_INTELLIGENCE)
        end

        def self.random_strength
            return @@generator.rand(0...MAX_STRENGTH)
            #rango: [0, MAX_STRENGTH)
        end

        def self.resurrect_player
            (@@generator.rand(0.0..1.0) < RESURRECT_PROB)
        end

        def self.weapons_reward
            return @@generator.rand(0..WEAPONS_REWARD)
        end

        def self.shields_reward
            return @@generator.rand(0..SHIELDS_REWARD)
        end

        def self.health_reward
            return @@generator.rand(0..HEALTH_REWARD)
        end

        def self.weapon_power
            return @@generator.rand(1..MAX_ATTACK)
        end

        def self.shield_power
            return @@generator.rand(1..MAX_SHIELD)
        end

        def self.uses_left
            return @@generator.rand(1..MAX_USES)
        end

        def self.intensity(competence)
            return @@generator.rand(0..competence)
        end

        def self.discard_element(uses_left)
            probability = 0
            if(uses_left < MAX_USES)then
                probability = 1 - (uses_left.to_f / MAX_USES.to_f)
            end
            return (@@generator.rand(0.0..1.0) < probability)
        end

    end #class Dice

end #module Irrgarten