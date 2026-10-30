/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Usuario
 */
public class TestP1 {
    public static void main(String[] args) {
        
        System.out.println(" *********** TESTS PRACTICA 1 *********** \n");
        
        System.out.println("\n");
        
        System.out.println("--------- Test Valoes Enum ---------\n");
        System.out.println("Direccion: " + Directions.DOWN);
        System.out.println("Orientacion: " + Orientation.VERTICAL);
        System.out.println("Personaje: " + GameCharacter.PLAYER + "\n");
        
        System.out.println();
        
        System.out.println("--------- Test clase Weapon ---------\n");
        
        //Utilizaremos una unidad más de los máximos usos permitidos de Dice.
        Weapon w = new Weapon(Dice.weaponPower(), Dice.getMaxUses());
        System.out.println("Estado inicio: " + w.toString());
        System.out.println();
        
        for(int i = 0; i < Dice.getMaxUses() + 1; i++){
            System.out.println("Test de ataque " + i + ":");
            System.out.println("- Potencia de ataque obtenida: " + w.attack());
            System.out.println("- Estado despues de ataque: " + w.toString());
            System.out.println("- Descarte de arma (s/n): " + w.discard());
            System.out.println();
        }
        
        System.out.println();
        
        System.out.println("--------- Test clase Shield ---------\n");
        
        //Utilizaremos una unidad más de los máximos usos permitidos de Dice.
        Shield s = new Shield(Dice.shieldPower(), Dice.getMaxUses());
        System.out.println("Estado inicio: " + s.toString());
        System.out.println();

        for(int i = 0; i < Dice.getMaxUses() + 1; i++){
            System.out.println("Test de proteccion " + i + ":");
            System.out.println("- Energia de proteccion obtenida: " + s.protect());
            System.out.println("- Estado despues de ataque: " + s.toString());
            System.out.println("- Descarte de arma (s/n): " + s.discard());
            System.out.println();
        }
        
        System.out.println();
        
        System.out.println("--------- Test clase Dice ---------\n");
        int test = 100;
        
        System.out.println("Comprobacion generacion de atributos y recompensas:\n");
        System.out.println("- randomIntelligence(): " + Dice.randomIntelligence() +
                "; Rango: [0.0 a " + Dice.getMaxIntelligence() + "]");
        
        System.out.println("- randomStrength(): " + Dice.randomStrength() +
                " unidades. Rango: [0.0 a " + Dice.getMaxStrength() + "]");
        
        System.out.println("- weaponsReward(): " + Dice.weaponsReward() +
                " unidades. Rango: [0 a " + Dice.getWeaponsReward() + "]");
        
        System.out.println("- shieldsReward(): " + Dice.shieldsReward() + 
                " unidades. Rango: [0 a " + Dice.getShieldsReward() + "]");
        
        System.out.println("- healthReward(): " + Dice.healthReward() +
                " unidades. Rango: [0 a " + Dice.getHealthReward() + "]");
        
        System.out.println();
        
        System.out.println("Comprobacion resurrectPlayer():\n");
        int resurrecciones = 0;
        for(int i = 0; i < test; i++){
            if(Dice.resurrectPlayer()){
                resurrecciones++;
            }
        }
        System.out.println("% de resurrecciones en " + test + " tests: " +
                (float) resurrecciones/test * 100 + "%. Esperado: " +
                Dice.getResurrectProb() * 100 + "%");
        /* A tener en cuenta:
        Al dividir dos números, se va a coger el tipo del numerador. Para que Java
        considere la operación aritmética así como queremos, se lo tenemos que especificar.
        */
        System.out.println();
        
        System.out.println("Comprobacion discardElement():\n");
        int discardsNoUsos = 0;
        int discardsMaxUsos = 0;
        int discardsXUsos = 0;
        int xUsos = 1; //Le asignamos variable para poder probar con otros números.
        
        for(int i = 0; i < test; i++){
            if(Dice.discardElement(0)) discardsNoUsos++;
            if(Dice.discardElement(1)) discardsXUsos++;
            if(Dice.discardElement(5)) discardsMaxUsos++; //MAX_USES = 5
        }
        
        System.out.println("- Descartado con 0 usos: " + discardsNoUsos + " de " +
                test + " veces. Esperado: 100%");
        System.out.println("- Descartado con " + xUsos + " uso(s): " +
                discardsXUsos + " de " + test + " veces. Esperado: " +
                (1.0f - ((float) xUsos / Dice.getMaxUses())) * 100 + "%");
        System.out.println("- Descartado con max usos: " + discardsMaxUsos +
                " de " + test + " veces. Esperado: 0%");

        System.out.println();
        
        System.out.println("Comprobacion de randomPos():\n");
        int max = 10; //Suponemos que la posición máxima es 10.
        int[] posiciones = new int[max];
        for(int i = 0; i < test; i++){
            posiciones[Dice.randomPos(max)]++;
        }
        System.out.println("Resultados en " + test + " intentos con " +
                max + " posiciones:");
        for(int i = 0; i < max; i++){
            System.out.println("- Posicion " + (i + 1) + ": " +
                    posiciones[i] + " veces");
        }

        System.out.println();
        
        System.out.println("Comprobacion de whoStarts():\n");
        int nplayers = 5; //suponemos nplayers = 5
        System.out.println("Numero de jugadores: " + nplayers);

        int jugadores[] = new int[nplayers]; 
        for(int i = 0; i < test; i++){
            int jugadorInicia = Dice.whoStarts(nplayers);
            jugadores[jugadorInicia]++;
        }
        
        for(int i = 0; i < nplayers; i++){
            System.out.println("El jugador " + i + " empieza " + jugadores[i] +
                    " veces. (Esperado aprox: " + 100.0f / nplayers + "%)");
            //100.0f no se refiere a test. Lo usamos para calcular probabilidad.
        }
        
        
        System.out.println();

        System.out.println("Comprobacion de intensity():\n");
        float competenceMain = 6.5f;
        
        System.out.println("Intensidad: " + Dice.intensity(competenceMain) + 
                "; Rango: [0.0 a " + competenceMain + "]");
        
        System.out.println();
    }
}
