package Lab3;
import java.lang.Math;

class Player {
    public String name;
    public double cur_health;
    public double max_health;
    public boolean is_alive ;
    public double x_cord;
    public double y_cord;
    public double damage;
    public double protection;
    public Player(String name){
        this.name = name;
        this.protection = 20;
        this.cur_health = 100;
        this.x_cord = 0;
        this.y_cord = 0;
        this.is_alive = true;
    }
    public void increaseHealth(double addition_points_of_health){
        this.cur_health = Math.max(this.max_health, this.cur_health + addition_points_of_health);
    }
    public void decreaseHealth(double points_to_decrease){
        this.cur_health -= points_to_decrease;
        if (this.cur_health < 0){
            this.is_alive = false;
        }
    }
    public void move(double x_cord, double y_cord){
        this.x_cord = x_cord;
        this.y_cord = y_cord;
    }
}
interface heal{
}
interface attack{
}
interface castSpell{
}

interface Tank {
    public default int additional_defense() {
        return 20;
    }
}
//interface Tank{
//}
class WarriorGuard extends Player implements attack{
    public double attack_radius = 10;
    public double damage = 20;
    public WarriorGuard(String name) {
        super(name);
    }
    public void attack(Player player){
        if (Math.sqrt(Math.pow(this.x_cord - player.x_cord, 2) + Math.pow(this.y_cord - player.y_cord, 2)) <= this.attack_radius){
            player.decreaseHealth(damage);
        }
    }
    @Override
    public String toString(){
        return "Текущее здоровье война WarriorGuard: " + this.name + "составляет: " + this.cur_health;
    }
}

class WarriorTank extends WarriorGuard implements Tank{
    public WarriorTank(String name) {
        super(name);
        this.cur_health += additional_defense();
    }
    @Override
    public String toString(){
        return "Текущее здоровье война WarriorTank - " + this.name + "составляет: " + this.cur_health;
    }
}

class Mage extends Player implements castSpell{
    public Mage(String name) {
        super(name);
    }
    void castSpell(Player player){
        player.decreaseHealth(20);
    }
    @Override
    public String toString(){
        return "Текущее здоровье мага Mage - " + this.name + "составляет: " + this.cur_health;
    }
}

class MageTank extends Mage implements Tank{
    public MageTank(String name) {
        super(name);
        this.cur_health += additional_defense();
    }
    public double additional_defense = 20;
    @Override
    public String toString(){
        return "Текущее здоровье мага MageTank - " + this.name + "составляет: " + this.cur_health;
    }
}

class Priest extends Player implements heal{
    public double healPower = 20;
    public Priest(String name) {
        super(name);
    }
    void heal(Player player) {
        player.increaseHealth(healPower);
    }
    @Override
    public String toString(){
        return "Текущее здоровье священника Priest - " + this.name + "составляет: " + this.cur_health;
    }
}

class PriestTank extends Priest implements Tank{
    public double healPower;
    public PriestTank(String name) {
        super(name);
    }
    public double additional_defense = 20;
    @Override
    public void decreaseHealth(double points_to_decrease){
        this.cur_health = this.cur_health - points_to_decrease + this.additional_defense;
        if (this.cur_health < 0){
            this.is_alive = false;
        }
    }
    @Override
    public String toString(){
        return "Текущее здоровье мага PriestTank - " + this.name + "составляет: " + this.cur_health;
    }
}

class Main {
    public static void main(String[] args) {
        WarriorTank warrior = new WarriorTank("Some Ork");
        MageTank mage = new MageTank("Some Mage");
        Priest priest = new Priest("Some Healer");
        warrior.x_cord = 1;
        warrior.y_cord = 1;
        warrior.attack(mage);
        priest.heal(mage);
        mage.castSpell(warrior);
        System.out.println(warrior);
        System.out.println(priest);
        System.out.println(mage);
    }
}