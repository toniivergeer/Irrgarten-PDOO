/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Usuario
 */
public class Weapon {
    
    //Para declarar variables privadas, usamos private delante de la declaración
    private float power;
    private int uses;
    
    /*
    Constructor público. Se llama igual que la clase. Se reserva memoria
    (se encarga el lenguaje y se inicializa el objeto
    Dentro del ámbito de un método, si hay coincidencia de nombres, se tiene
    preferencia al atributo más local: en este caso el atributo del método. Podemos
    diferenciarlo usando this.name_attribute --> será el atributo privado.
    */
    
    public Weapon(float aPower, int someUses){
        power = aPower;
        uses = someUses;
    }
    
    public float attack() {
        float salida = power;
        if(uses > 0){
            uses--;
        } else{
            salida = 0f; //Añadimos 0 para decirle a Java que interprete el 0 como...
        }
        
        return salida;
    }
    
    public boolean discard(){
        return Dice.discardElement(uses);
    }
    
    /*Vamos a convertir la información en una cadena, para que cuando queramos mostrar 
    el objeto, puedan estar los atributos organizados de manera entendible. Este método 
    lo solemos añadir en todas las clases.
    
    @Override --> Todas las clases se heredan de una clase de Java llamada Object, con
    unas implementaciones predefinidas, sin embargo son poco entendibles.
    Lo que estamos haciendo con @Override es avisar a Java que vamos a sobreescribir 
    el método que hemos heredado.
    */
    
    @Override
    public String toString() {
        return "W[p:" + power + ", u:" + uses + "]";
    }
}