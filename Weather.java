import java.util.Random;

public class Weather {
    public static int weather(){ //losowanie pogody
        Random rand = new Random();
        return rand.nextInt(Constants.rand20);
    }
    public static void sun(){ //przy słońcu normalnie
        FlowerManagement.getInstance().flowerCare();
        for(Flower flower : FlowerManagement.getInstance().getFlowers()){
            FlowerManagement.getInstance().pestManagement(flower);
        }
    }
    public static void rain(){ //deszcz podlewa kwiaty, nie wywołuje się pielegnacja, dodatkowe rozmnażanie szkodników
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            if (FlowerManagement.getInstance().getFlowers().get(i).isAlive()){
                if(FlowerManagement.getInstance().getFlowers().get(i).getLife() >= Constants.rainLifeWorth){
                    FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.fullLife);
                }
                else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.rainWeatherLifeAdd);
            }
            FlowerManagement.getInstance().getFlowers().get(i).reproduction();
            for(Flower flower : FlowerManagement.getInstance().getFlowers()){
                FlowerManagement.getInstance().pestManagement(flower);
            }
        }
    }
    public static void drought(){//susza zabiera życie, wywołuje się pielęgnacja
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            if (FlowerManagement.getInstance().getFlowers().get(i).isAlive()){
                if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.droughtLifeWorth){
                    FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                }
                else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.droughtLifeWorth);
            }
        }
        FlowerManagement.getInstance().flowerCare();
    }
    public static void snow(){ //zabiera różnym kwiatom inaczej zycie, brak pielęgnacji
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            if (FlowerManagement.getInstance().getFlowers().get(i).isAlive()){
                if(FlowerManagement.getInstance().getFlowers().get(i).getType().equals("Rose")){
                    if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.snowLifeWorthForRose){
                        FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                    }
                    else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.snowLifeWorthForRose);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).getType().equals("Iris")){
                    if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.snowLifeWorthForIris){
                        FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                    }
                    else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.snowLifeWorthForIris);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).getType().equals("Tulip")){
                    if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.snowLifeWorthForTulip){
                        FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                    }
                    else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.snowLifeWorthForTulip);
                }
            }
        }
    }
    public static void hurricane(){ //zabiera różnym kwiatom inaczej zycie, wywołuje pielęgnacje, dodatkowe rozmnażanie szkodników
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            if (FlowerManagement.getInstance().getFlowers().get(i).isAlive()){
                if(FlowerManagement.getInstance().getFlowers().get(i).getType().equals("Rose")){
                    if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.hurricaneLifeWorthForRose){
                        FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                    }
                    else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.hurricaneLifeWorthForRose);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).getType().equals("Iris")){
                    if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.hurricaneLifeWorthForIris){
                        FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                    }
                    else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.hurricaneLifeWorthForIris);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).getType().equals("Tulip")){
                    if(FlowerManagement.getInstance().getFlowers().get(i).getLife() <= Constants.hurricaneLifeWorthForTulip){
                        FlowerManagement.getInstance().getFlowers().get(i).setLife(Constants.noLife);
                    }
                    else FlowerManagement.getInstance().getFlowers().get(i).changeLife(Constants.hurricaneLifeWorthForTulip);
                }
            }
        }
        FlowerManagement.getInstance().flowerCare();
        for(Flower flower : FlowerManagement.getInstance().getFlowers()){
            FlowerManagement.getInstance().pestManagement(flower);
        }
    }
}
