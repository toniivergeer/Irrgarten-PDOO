/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author Toni Verger Vallespir
 */
public class Monster {
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private static final int VOID = -1;
    private static final int INITIAL_HEALTH = 5;
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    // ----------------- CONSTRUCTOR -----------------
    
    /**
    * @brief Constructor de monstruo con el nombre, la inteligencia y la fuerza 
    * indicados. Inicializa la salud con el valor inicial y establece su posición 
    * como no asignada.
    *
    * @param aName Nombre del monstruo.
    * @param aIntelligence Nivel de inteligencia del monstruo.
    * @param aStrength Nivel de fuerza del monstruo.
    */
    public Monster(String aName, float aIntelligence,
            float aStrength){
        health = INITIAL_HEALTH;
        name = aName;
        intelligence = aIntelligence;
        strength = aStrength;
        row = VOID;
        col = VOID;
    }
    
    // ----------------- METODOS PUBLIC -----------------
    
    /**
     * @brief Comprueba si el monstruo está muerto. El monstruo estará muerto si
     * su salud es menor o igual a 0.
     * @return True si el monstruo está muerto. False otherwise.
     */
    public boolean dead(){
    return (health <= 0); 
    }
    
    /**
     * @brief Decide aleatoriamente la potencia de ataque del monstruo.
     * @return Potencia de ataque.
     */
    public float attack(){
        return (Dice.intensity(strength));
    }
    
    public boolean defend(float receivedAttack){ //PRACTICA3
        throw new UnsupportedOperationException();
    }
    
    /**
     * @brief Establece la posición del monstruo.
     * @param row Fila de la casilla establecida.
     * @param col Columna de la casilla establecida.
     */
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;   
    }
    
    /**
     * @brief Redacta las estadísticas del monstruo.
     * @return Cadena de texto con el nombre y los atributos del monstruo, así
     * como su posición.
     */
    @Override
    public String toString(){
        return "Monster: " + name + "\n" +
                "- Health: " + health + "\n" +
                "- Strength: " + strength + "\n" +
                "- Intelligence: " + intelligence + "\n" +
                "- (x,y): (" + row + "," + col + ")"; 
       } 
    
    // ----------------- METODOS PRIVATE -----------------
    
    /**
     * @brief Disminuye en una unidad la vida del monstruo.
     */
    private void gotWounded(){
        health--;
    }
}

