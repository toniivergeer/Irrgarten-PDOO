/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author Toni Verger Vallespir
 */
public class Player {
    
    // ----------------- DECLARACION VARIABLES -----------------
    
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private static final int VOID = -1;
    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits = 0; //no añadimos final porque va a cambiar.
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    // ----------------- CONSTRUCTOR -----------------
    
    /**
     * @brief Constructor de jugador.
     * @param number Número del jugador.
     * @param intelligence Valor de inteligencia del jugador.
     * @param strength Valor de fuerza del jugador.
     */
    public Player(char number, float intelligence, float strength){
        
        this.name = "Player #" + number; //p.e: Player #2
        this.number = number; //Conservamos el numero de jugador por si acaso.
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.row = VOID; //inicializamos al jugador FUERA del tablero.
        this.col = VOID;
        this.weapons = new ArrayList<>(); //declaramos como listas vacías.
        this.shields = new ArrayList<>();
    }
    
    // ----------------- METODOS PUBLIC -----------------
    
    /**
     * @brief Revive al jugador, reestableciendo su salud y vaciando su arsenal
     * de armas y escudos. Reestablece los golpes consecutivos.
     */
    public void resurrect(){
        this.health = INITIAL_HEALTH;
        this.weapons.clear(); //vaciar listas en Java
        this.shields.clear(); //vaciar listas en Java
        this.resetHits(); 
    }
    
    /**
     * @brief Consultor.
     * @return Fila del jugador.
     */
    public int getRow(){
        return row;
    }
    
    /**
     * @brief Consultor.
     * @return Columna del jugador.
     */
    public int getCol(){
        return col;
    }
    
    /**
     * @brief Consultor.
     * @return Número del jugador.
     */
    public char getNumber(){
        return number;
    }
    
    /**
     * @brief Consultor.
     * @return Nombre del jugador.
     */
    public String getName(){
        return name;
    }
    
    /**
     * @brief Establece la posición de un jugador.
     * @param row Fila de la cassilla establecida.
     * @param col Columna de la casilla establecida.
     * @post El objeto tiene establecida una posición marcada por (row,col).
     */
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * @brief Consulta si el jugador está muerto. Un jugador estará muerto si su
     * salud es menor o igual a 0.
     * @return True si el jugadr está muerto. False otherwise.
     */
    public boolean dead(){
        return(health <= 0);
    }
    
    /**
     * @brief Mueve al jugador en una dirección, consultando si está entre las válidas.
     * @param direction Dirección en la que se quieren mover.
     * @param validMoves Array de direcciones válidas en las que se puede mover.
     * @return Dirección en la que se mueve.
     */
    public Directions move(Directions direction,
            ArrayList<Directions> validMoves){
        
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        if((size > 0) && (!contained)){
               return validMoves.get(0);
        }
        
        return direction;
    }
    
    /**
     * @brief Calcula la potencia de ataque total del jugador, obtenido de su
     * fuerza junto con la de todas sus armas.
     * @return Potencia de ataque total.
     */
    public float attack(){
        return strength + sumWeapons();
    }
    
    /**
     * @brief Defensa del jugador. 
     * @param receivedAttack valor del ataque que recibe el jugador.
     * @return True si el jugador pierde, false en caso contrario.
     */
    public boolean defend(float receivedAttack){ 
        return manageHit(receivedAttack);
    }
    
    /**
     * @brief Entrega las recompensas de armas, escudos y salud al jugador.
     */
    public void receiveReward(){ 
        int wReward, sReward;
        
        wReward = Dice.weaponsReward();
        sReward = Dice.shieldsReward();
        
        for(int i = 1; i <= wReward; ++i){
            Weapon wnew = newWeapon();
            receiveWeapon(wnew);
        }
        
        for(int i = 1; i <= sReward; ++i){
            Shield snew = newShield();
            receiveShield(snew);
        }
        
        int extraHealth = Dice.healthReward();
        health += extraHealth;
        
    }
    
    /**
     * @brief Redacta las estadísticas del jugador.
     * @return Cadena de texto con el nombre y los atributos del jugador, así
     * como su posición.
     */
    @Override
    public String toString(){
        return name + ":\n" +
                "- Health: " + health + "\n" +
                "- Intelligence: " + intelligence + "\n" +
                "- Strength: " + strength + "\n" +
                "- (x,y): (" + getRow() + "," + getCol() + ")" + "\n" +
                "- ConsecutiveHits: " + consecutiveHits;
    }
    
    // ----------------- METODOS PRIVATE -----------------
    
    /**
     * @brief Recibe un arma y la añade a la colección si hay espacio.
     * @post Se actualiza el arsenal, descartando las que cumplan la condición.
     * @param w Arma a recibir.
     */
    private void receiveWeapon(Weapon w){ 
        for(Weapon wi : weapons){
            boolean discard = wi.discard();
            
            if(discard){
                weapons.remove(wi);
            }
        }
        
        int size = weapons.size();
        
        if(size < MAX_WEAPONS){
            weapons.add(w);
        }
    }
    
    /**
     * @brief Recibe un escudo y lo añade al arsenal si hay espacio.
     * @post Se actualiza el arsenal, descartando los que cumplan la condición.
     * @param s Escudo a recibir.
     */
    private void receiveShield(Shield s){ 
        for(Weapon si : weapons){
            boolean discard = si.discard();
            
            if(discard){
                shields.remove(si);
            }
        }
        
        int size = shields.size();
        
        if(size < MAX_SHIELDS){
            shields.add(s);
        }
    }
    
    /**
     * @brief Crea una nueva arma, inicializando aleatoriamente su poder de
     * ataque y sus usos restantes.
     * @return Arma inicializada.
     */
    private Weapon newWeapon(){
        
        Weapon w = new Weapon(Dice.weaponPower(),
                Dice.usesLeft());
            
        return w;
        /*Este metodo se usa dentro de receiveWeapon(),
        que se encargará de comprobar si weapons < MAX_WEAPONS
        */
    }
    
    /**
     * @brief Crea un nuevo escudo, inicializando aleatoriamente su poder de
     * protección y sus usos restantes.
     * @return Escudo inicializado.
     */
    private Shield newShield(){
        
        Shield s = new Shield(Dice.shieldPower(), 
                Dice.usesLeft());
        
        return s;
        /*Este metodo se usa dentro de receiveShield(),
        que se encargará de comprobar si shields < MAX_SHIELDS
        */
    }
    
    /**
     * @brief Calcula la potencia de ataque de todas sus armas.
     * @return Sumatoria del daño de todas las armas.
     */
    private float sumWeapons(){
        float sumW = 0.0f;
        
        for(Weapon w: weapons){ //recorremos toda la lista
            sumW += w.attack();
        }
        
        return sumW;
    }
    
    /**
     * @brief Calcula la protección de todos los escudos del jugador.
     * @return Sumatoria de los escudos.
     */
    private float sumShields(){
        float sumS = 0.0f;
        
        for(Shield s: shields){
            sumS += s.protect();
        }
        
        return sumS;
    }
    
    /**
     * @brief Calcula la energía defensiva del jugador, basado en su nivel de
     * inteligencia y la suma de sus escudos.
     * @return Energía defensiva del jugador total.
     */
    private float defensiveEnergy(){
        return intelligence + sumShields();
    }
    
    /**
     * @brief Gestiona el golpe recibido al jugador, y si éste pierde o no.
     * @param receivedAttack Valor de ataque recibido al jugador.
     * @return True si el jugador pierde, false en caso contrario.
     */
    private boolean manageHit(float receivedAttack){ 
        boolean lose;
        float defense = defensiveEnergy();
        
        if(defense < receivedAttack){
            gotWounded();
            incConsecutiveHits();
        }
        else{
            resetHits();
        }
        
        if((consecutiveHits == HITS2LOSE) || (dead())){
            resetHits();
            lose = true;
        }
        else{
            lose = false;
        }
        
        return lose;
    }
    
    /**
     * @brief Reestablece los golpes consecutivos a cero.
     */
    private void resetHits(){
        consecutiveHits = 0;
    }
    
    /**
     * @brief Decrementa en una unidad los puntos de salud.
     */
    private void gotWounded(){
        health--;
    }
    
    /**
     * @brief Incrementa en una unidad los golpes consecutivos.
     */
    private void incConsecutiveHits(){
        consecutiveHits++;
    }
}
