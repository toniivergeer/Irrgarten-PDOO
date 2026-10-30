/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author Toni Verger Vallespir
 */
public class Dice {

    /*
    Las variables que declaremos dentro de esta clase las llamaremos
    "Variables Clase", así como los "Métodos Clase": métodos que usan las 
    variables declaradas dentro de la clase. Así, podremos inicializar de la forma
    en que se muestra:
    */
    
    /*
    Usamos final porque una vez se declare, no se va a poder cambiar. Diríamos que
    es un equivalente al "const" de C++.
    Los números en float los especificamos con una f.
    */
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private static final int MAX_USES = 5;
    private static final float MAX_INTELLIGENCE = 10.0f;
    private static final float MAX_STRENGTH = 10.0f;
    private static final float RESURRECT_PROB = 0.3f;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    
    // ----------------- CONSULTORES -----------------
    
    /**
     * @brief Consultor.
     * @return Usos máximos que puede obtener una arma.
     */
    public static int getMaxUses(){
        return MAX_USES;
    }
    
    /**
     * @brief Consultor
     * @return Máxima inteligencia que se puede obtener.
     */
    public static float getMaxIntelligence(){
        return MAX_INTELLIGENCE;
    }
    
    /**
     * @brief Consultor
     * @return Máxima fuerza que se puede obtener.
     */
    public static float getMaxStrength(){
        return MAX_STRENGTH;
    }
    
    /**
     * @brief Consultor
     * @return Probabilidad de revivir.
     */
    public static float getResurrectProb(){
        return RESURRECT_PROB;
    }
    
    /**
     * @brief Consultor
     * @return Máximo número de armas que se pueden obtener.
     */
    public static int getWeaponsReward(){
        return WEAPONS_REWARD;
    }
    
    /**
     * @brief Consultor
     * @return Máximo número de escudos que se pueden obtener.
     */
    public static int getShieldsReward(){
        return SHIELDS_REWARD;
    }
    
    /**
     * @brief Consultor
     * @return Máxima curación que se puede obtener.
     */
    public static int getHealthReward(){
        return HEALTH_REWARD;
    }
    
    //declaración de generador tipo Random para usar en variables aleatorias.
    private final static Random generator = new Random();
    
    // ----------------- METODOS PUBLIC -----------------
    
    /**
     * @brief Genera una posición aleatoria entre 0 y el máximo introducido.
     * @param max elemento máximo a randomizar.
     * @return Número aleatorio entre 0 y max.
     */
    public static int randomPos(int max){
        return generator.nextInt(max);
    }
    
    /**
     * @brief Decide aleatoriamente el jugador que va a empezar.
     * @param nplayers Número de jugadores.
     * @return Número del jugador que empieza.
     */
    public static int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }
    
    /**
     * @brief Calcula un valor para el atributo de inteligencia entre 0 y
     * MAX_INTELLIGENCE
     * @return Número aleatorio entre 0 y MAX_INTELLIGENCE.
     */
    public static float randomIntelligence(){
        return generator.nextFloat() * MAX_INTELLIGENCE;
    }
    
    /**
     * @brief Calcula un valor para el atributo de fuerza entre 0 y
     * MAX_STRENGTH
     * @return Número aleatorio entre 0 y MAX_STRENGTH.
     */
    public static float randomStrength(){
        return generator.nextFloat() * MAX_STRENGTH;
    }
    
    /**
     * @brief Decide aleatoriamente si el jugador va a revivir o no. 
     * @return True si el jugador revive, False otherwise.
     */
    public static boolean resurrectPlayer(){
        return(generator.nextFloat() < RESURRECT_PROB);
    }
    
    /**
     * @brief Decide el número de armas recibidas como recompensa.
     * @return Número de armas obtenidas, entre 0 y WEAPONS_REWARD.
     */
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD);
    }
    
    /**
     * @brief Decide el número de escudos recibidos como recompensa.
     * @return Número de escudos obtenidos, entre 0 y SHIELDS_REWARD.
     */
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD);
    }
    
    /**
     * @brief Decide el número de curación recibida como recompensa.
     * @return Número de curación obtenida, entre 0 y HEALTH_REWARD.
     */
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD);
    }
    
    /**
     * @brief Decide la potencia de ataque del arma.
     * @return Potencia de ataque obtenida (entre 0 y MAX_ATTACK).
     */
    public static float weaponPower(){
        return generator.nextFloat() * MAX_ATTACK;
    }
    
    /**
     * @brief Decide el poder del escudo.
     * @return Poder del escudo obtenido (entre 0 y MAX_SHIELD).
     */
    public static float shieldPower(){
        return generator.nextFloat() * MAX_SHIELD;
    }
    
    /**
     * @brief Determina los usos restantes de un objeto.
     * @return Usos restantes (entre 0 y MAX_USES).
     */
    public static int usesLeft(){
        return generator.nextInt(MAX_USES);
    }
    
    /**
 * @brief Genera una intensidad aleatoria limitada por el valor máximo indicado.
 * @param competence Valor máximo de intensidad.
 * @return Intensidad aleatoria generada.
 */
    public static float intensity(float competence){
        if(competence < 0){
            competence = 0.0f;
        }
        return generator.nextFloat() * competence;
    }

    /**
    * @brief Decide aleatoriamente si se debe descartar un elemento en función de 
    * sus usos restantes. La probabilidad de descarte aumenta a medida que 
    * disminuyen los usos restantes.
    *
    * @param usesLeft Número de usos que le quedan al elemento.
    * @return True si se debe descartar el elemento, False en caso contrario.
    */
    public static boolean discardElement(int usesLeft){
        float probability = 0.0f;
        if(usesLeft < MAX_USES && usesLeft >= 0){
            probability = 1.0f - ((float) usesLeft / MAX_USES);
        }
        
        return generator.nextFloat() < probability;
    }
}


// APUNTES:

/*
Vamos a poder usar los métodos de la clase Random. 
Ejemplos:
    nextFloat() [entre 0.0 y 1.0[
    nextBoolean() [verdadero o falso]
    nextInt() [entre 0 y 9]
    nextDouble() [entre 0 y 4.999...]
*/

/*
IMPORTANTE.
nextInt(limite) recibe el entero máximo, pero nextFloat() 
no admite parámetros y debe multiplicarse por el valor flotante máximo.
*/

    
/*
Estamos tratando de usar Random que no tenemos especificado inicialmente. Si
aparece la bombilla con símbolo de "!" en rojo, le podemos dar click para que
nos ayude.

Solución: importamos la clase Random de Java.
*/