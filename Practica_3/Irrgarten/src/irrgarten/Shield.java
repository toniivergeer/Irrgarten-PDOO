/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Toni Verger Vallespir
 */
public class Shield {
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private float protection;
    private int uses;
    
    // ----------------- CONSTRUCTOR -----------------
    
    public Shield(float aProtection, int someUses){
        protection = aProtection;
        uses = someUses;
    }
    
    // ----------------- METODOS PUBLIC -----------------
    
    public float protect() {
        float salida = protection;
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
        return "S[p:" + protection + ", u:" + uses + "]";
    }
}
