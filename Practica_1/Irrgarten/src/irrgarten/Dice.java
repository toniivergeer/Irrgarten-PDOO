/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;

/**
 *
 * @author Usuario
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
    
    //Atributos de clase privados
    private static final int MAX_USES = 5;
    private static final float MAX_INTELLIGENCE = 10.0f;
    private static final float MAX_STRENGTH = 10.0f;
    private static final float RESURRECT_PROB = 0.3f;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;
    
    //CONSULTORES PARA MAIN (evitar uso de números mágicos y facilitar mantenimiento).
    public static int getMaxUses(){
        return MAX_USES;
    }
    public static float getMaxIntelligence(){
        return MAX_INTELLIGENCE;
    }
    public static float getMaxStrength(){
        return MAX_STRENGTH;
    }
    public static float getResurrectProb(){
        return RESURRECT_PROB;
    }
    public static int getWeaponsReward(){
        return WEAPONS_REWARD;
    }
    public static int getShieldsReward(){
        return SHIELDS_REWARD;
    }
    public static int getHealthReward(){
        return HEALTH_REWARD;
    }
    
    /*
    Estamos tratando de usar Random que no tenemos especificado inicialmente. Si
    aparece la bombilla con símbolo de "!" en rojo, le podemos dar click para que
    nos ayude.
    
    Solución: importamos la clase Random de Java.
    */
    private final static Random generator = new Random();
    
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
    
    public static int randomPos(int max){
        return generator.nextInt(max);
    }
    
    public static int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }
    
    public static float randomIntelligence(){
        return generator.nextFloat() * MAX_INTELLIGENCE;
    }
    
    public static float randomStrength(){
        return generator.nextFloat() * MAX_STRENGTH;
    }
    
    public static boolean resurrectPlayer(){
        return(generator.nextFloat() < RESURRECT_PROB);
    }
    
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD);
    }
    
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD);
    }
    
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD);
    }
    
    public static float weaponPower(){
        return generator.nextFloat() * MAX_ATTACK;
    }
    
    public static float shieldPower(){
        return generator.nextFloat() * MAX_SHIELD;
    }
    
    public static int usesLeft(){
        return generator.nextInt(MAX_USES);
    }
    
    public static float intensity(float competence){
        if(competence < 0){
            competence = 0.0f;
        }
        return generator.nextFloat() * competence;
    }
    
    /*
    Si usesLeft == 0, la probabilidad se quedará en 1 al realizar la división. Así,
    devolverá true.
    Si usesLeft >= MAX_USES, no entra en el if y la probabilidad se guarda en 0.0f,
    así que devolverá false.
    En los casos intermedios se calcula la probabilidad y se genera un número aleatorio.
    */
    public static boolean discardElement(int usesLeft){
        float probability = 0.0f;
        if(usesLeft < MAX_USES && usesLeft >= 0){
            probability = 1.0f - ((float) usesLeft / MAX_USES);
        }
        
        return generator.nextFloat() < probability;
    }
}
