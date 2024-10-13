import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlowerManagement {
    private final List<Flower> flowers = new ArrayList<>(); //lista przechowująca kwiaty
    public static int numOfFlowersBeginning = 0; //licznik, ile kwiatów powstało na początku
    public static int numOfTulipsBeginning = 0; //licznik, ile tulipanów powstało na początku
    public static int numOfRosesBeginning = 0; //licznik, ile róż powstało na początku
    public static int numOfIrisesBeginning = 0; //licznik, ile irysów powstało na początku
    public static int numOfPestsBeginning = 0; //licznik, ile szkodników powstało na początku
    public static int numOfSpidersBeginning = 0; //licznik, ile pająków powstało na początku
    public static int numOfLarvasBeginning = 0; //licznik, ile larw powstało na początku
    public static int numOfFlyingsBeginning = 0; //licznik, ile latających szkodników powstało na początku

    private static final FlowerManagement instance = new FlowerManagement();
    public static FlowerManagement getInstance() {
        return instance;
    }
    //początkowo wypełnia planszę losowo kwiatami, losowo dobierając im szkodniki
    private FlowerManagement(){
        for(int i = Constants.gardenXBeginning; i < Constants.gardenXEnding; i++){
            for(int j = Constants.gardenYBeginning; j < Constants.gardenYEnding; j++){
                int whichFlower = randomInt(Constants.rand10);//losowanie typu kwiatu
                int whichPest = randomInt(Constants.rand4);//losowanie szkodnika
                if(whichFlower == Constants.roseType){
                    Rose rose = new Rose(i,j,Constants.fullLife);
                    rose.setWhichPest(whichPest);
                    flowers.add(rose);
                    numOfFlowersBeginning++;
                    numOfRosesBeginning++;
                    pestCounting(whichPest); //odsyłanie do metody liczącej szkodniki
                }
                if(whichFlower == Constants.tulipType){
                    Tulip tulip = new Tulip(i,j,Constants.fullLife);
                    tulip.setWhichPest(whichPest);
                    flowers.add(tulip);
                    numOfFlowersBeginning++;
                    numOfTulipsBeginning++;
                    pestCounting(whichPest); //odsyłanie do metody liczącej szkodniki
                }
                if(whichFlower == Constants.irisType){
                    Iris iris = new Iris(i,j,Constants.fullLife);
                    iris.setWhichPest(whichPest);
                    flowers.add(iris);
                    numOfFlowersBeginning++;
                    numOfIrisesBeginning++;
                    pestCounting(whichPest); //odsyłanie do metody liczącej szkodniki
                }
                else if (whichFlower > Constants.irisType){ //dodanie braku kwiatka
                    Flower dead = new Flower(i, j, Constants.noLife);
                    dead.setWhichPest(Constants.noPestType);
                    flowers.add(dead);
                }
            }
        }
    }
    //metoda losująca liczbę
    public int randomInt(int range){
        Random rand = new Random();
        return rand.nextInt(range);
    }
    public List<Flower> getFlowers() {
        return flowers;
    }
    public int getListSize(){
        return flowers.size();
    }

    //metoda po kolei przechodzi po kwiatach i podlewa te które tego potrzebują, odsyła do sprawdzania szkodników
    public void flowerCare(){
        for(Flower flower : flowers){
            if(flower.getLife() > Constants.noLife && flower.getPestType().equals("Dead")){
                if(flower.getType().equals("Rose") && flower.getLife() < Constants.roseWateringReq){
                    flower.changeLife(Constants.roseWateringWorth);//podlewanie
                    antiPest(flower);
                }
                if(flower.getType().equals("Tulip") && flower.getLife() < Constants.tulipWateringReq){
                    flower.changeLife(Constants.tulipWateringWorth);//podlewanie
                    antiPest(flower);
                }
                if(flower.getType().equals("Iris") && flower.getLife() < Constants.irisWateringReq){
                    flower.changeLife(Constants.irisWateringWorth);//podlewanie
                    antiPest(flower);
                }
            }
        }
    }
    //zlicza szkodniki powstałe na początku symulacji
    public void pestCounting(int whichPest){
        if(whichPest == Constants.spiderType){
            numOfSpidersBeginning++;
            numOfPestsBeginning++;
        }
        if(whichPest == Constants.larvaType){
            numOfLarvasBeginning++;
            numOfPestsBeginning++;
        }
        if(whichPest == Constants.flyingType){
            numOfFlyingsBeginning++;
            numOfPestsBeginning++;
        }
    }
    //metoda zarządzająca szkodnikami, losowo pozwala im na rozmnażanie lub przemieszczanie, jesli mają wystarczająco energii
    public void pestManagement(Flower flower){
        if(flower.isAlive() && !flower.getPestType().equals("Dead")){
            int whatWillItDo = randomInt(Constants.rand4);
            if(flower.getPestType().equals("Spider") && flower.getPestFulness() > Constants.spiderFullnessReq){
                choice(whatWillItDo, flower);
            }
            if(flower.getPestType().equals("Flying") && flower.getPestFulness() > Constants.flyingFullnessReq){
                choice(whatWillItDo, flower);
            }
            if(flower.getPestType().equals("Larva") && flower.getPestFulness() > Constants.larvaFullnessReq){
                choice(whatWillItDo, flower);
            }
        }
    }
    public void choice(int choice, Flower flower){
        if(choice == 0) flower.pestReproduction();
        if(choice == 1) flower.pestMovement();
    }
    //metoda usuwająca szkodniki, jeżeli są w sąsiedztwie podlewanego kwiatka
    public void antiPest(Flower flower){
        if(flower.getType().equals("Rose")){
            for(Flower neighbor: flowers){
                if(neighbor.isAlive() && !neighbor.getPestType().equals("Dead")){
                    if(neighbor.getX() == flower.getX()-1 && neighbor.getY() == flower.getY()-1 ||
                            neighbor.getX() == flower.getX()-1 && neighbor.getY() == flower.getY()+1 ||
                            neighbor.getX() == flower.getX()+1 && neighbor.getY() == flower.getY()-1 ||
                            neighbor.getX() == flower.getX()+1 && neighbor.getY() == flower.getY()+1){
                        neighbor.killPest();
                    }
                }
            }
        }
        if(flower.getType().equals("Tulip")){
            for(Flower neighbor: flowers){
                if(neighbor.isAlive() && !neighbor.getPestType().equals("Dead")){
                    if(neighbor.getX() == flower.getX() && neighbor.getY() == flower.getY()-1 ||
                            neighbor.getX() == flower.getX()-1 && neighbor.getY() == flower.getY() ||
                            neighbor.getX() == flower.getX()+1 && neighbor.getY() == flower.getY() ||
                            neighbor.getX() == flower.getX() && neighbor.getY() == flower.getY()+1){
                        neighbor.killPest();
                    }
                }
            }
        }
        if(flower.getType().equals("Iris")){
            for(Flower neighbor: flowers){
                if(neighbor.getX() == flower.getX()-1 && neighbor.getY() == flower.getY()-1 ||
                        neighbor.getX() == flower.getX()-1 && neighbor.getY() == flower.getY() ||
                        neighbor.getX() == flower.getX()-1 && neighbor.getY() == flower.getY()+1 ||
                        neighbor.getX() == flower.getX()+1 && neighbor.getY() == flower.getY()-1 ||
                        neighbor.getX() == flower.getX()+1 && neighbor.getY() == flower.getY() ||
                        neighbor.getX() == flower.getX()+1 && neighbor.getY() == flower.getY()+1){
                    neighbor.killPest();
                }
            }
        }
    }
}
