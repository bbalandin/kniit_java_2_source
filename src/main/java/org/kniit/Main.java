import java.lang.Math;

class Race{
    public double healPower = 20;
    public double damage = 20;
    public Race(String race){
        if (race.equals("dwarf") || race.equals("ork")){
            this.damage += 20;
        }
        else if (race.equals("elf") || race.equals("angel")){
            this.healPower += 20;
        }
    }
}
class Player extends Race{
    public String name;
    public double cur_health;
    public double max_health;
    public boolean is_alive ;
    public double x_cord;
    public double y_cord;
    public double protection;
    public Player(String name, String race){
        super(race);
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
    public WarriorGuard(String name, String race) {
        super(name, race);
    }
    public void attack(Player player){
        if (Math.sqrt(Math.pow(this.x_cord - player.x_cord, 2) + Math.pow(this.y_cord - player.y_cord, 2)) <= this.attack_radius){
            player.decreaseHealth(damage);
        }
    }
    @Override
    public String toString(){
        return "Текущее здоровье война WarriorGuard: " + this.name + " составляет: " + this.cur_health;
    }
}

class WarriorTank extends WarriorGuard implements Tank{
    public WarriorTank(String name, String race) {
        super(name, race);
        this.cur_health += additional_defense();
    }
    @Override
    public String toString(){
        return "Текущее здоровье война WarriorTank - " + this.name + " составляет: " + this.cur_health;
    }
}

class Mage extends Player implements castSpell{
    public Mage(String name, String race) {
        super(name, race);
    }
    void castSpell(Player player){
        player.decreaseHealth(this.damage);
    }
    @Override
    public String toString(){
        return "Текущее здоровье мага Mage - " + this.name + " составляет: " + this.cur_health;
    }
}

class MageTank extends Mage implements Tank{
    public MageTank(String name, String race) {
        super(name, race);
        this.cur_health += additional_defense();
    }
    public double additional_defense = 20;
    @Override
    public String toString(){
        return "Текущее здоровье мага MageTank - " + this.name + " составляет: " + this.cur_health;
    }
}

class Priest extends Player implements heal{
    public Priest(String name, String race) {
        super(name, race);
    }
    void heal(Player player) {
        player.increaseHealth(healPower);
    }
    @Override
    public String toString(){
        return "Текущее здоровье священника Priest - " + this.name + " составляет: " + this.cur_health;
    }
}

class PriestTank extends Priest implements Tank{
    public PriestTank(String name, String race) {
        super(name, race);
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
        return "Текущее здоровье мага PriestTank - " + this.name + " составляет: " + this.cur_health;
    }
}

class Main {
    public static void main(String[] args) {
        WarriorTank warrior = new WarriorTank("Some Ork", "human");
        MageTank mage = new MageTank("Some Mage", "human");
        Priest priest = new Priest("Some Healer", "human");
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