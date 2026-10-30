/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Toni Verger Vallespir
 */
public class Weapon {
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private float power;
    private int uses;
    
    // ----------------- CONSTRUCTOR -----------------
    
    public Weapon(float aPower, int someUses){
        power = aPower;
        uses = someUses;
    }
    
    // ----------------- METODOS PUBLIC -----------------
    
    public float attack() {
        float salida = power;
        if(uses > 0){
            uses--;
        } else{
            salida = 0.0f;
        }
        
        return salida;
    }
    
    public boolean discard(){
        return Dice.discardElement(uses);
    }
    
    @Override
    public String toString() {
        return "W[p:" + power + ", u:" + uses + "]";
    }
}