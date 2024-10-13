import javax.swing.*;
import java.awt.*;

public class Simulation {
    public static JFrame window;
    public static void main (String[] args){
        Garden garden = Garden.getInstance();

        EventQueue.invokeLater(() -> {
            window = new Window();
            window.setVisible(true);
            Timer time = new Timer(1400, t -> {
                garden.repaint();
                changeDay();
                DataExport.export();
            });
            time.start();
        });
    }
    int weather1;

    //metoda odpowiadająca za funkcjonowanie ogrodu z dnia na dzień
    public static void changeDay(){
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            //dodaje obrażenia dla kwiatka w zależnosci od jego typu i posiadanego szkodnika
            if (FlowerManagement.getInstance().getFlowers().get(i).isAlive()){
                FlowerManagement.getInstance().getFlowers().get(i).changeLife(FlowerManagement.getInstance().getFlowers().get(i).dmgDealt());
                //usuwa martwe kwiaty
                if(!FlowerManagement.getInstance().getFlowers().get(i).isAlive()){
                    FlowerManagement.getInstance().getFlowers().add(Flower.death(FlowerManagement.getInstance().getFlowers().get(i)));
                    FlowerManagement.getInstance().getFlowers().remove(i);
                }
                if(!FlowerManagement.getInstance().getFlowers().get(i).getPestType().equals("Dead")){
                    FlowerManagement.getInstance().getFlowers().get(i).feedPest();
                    FlowerManagement.getInstance().pestManagement(FlowerManagement.getInstance().getFlowers().get(i));
                }
                //wywołuje rozmnażanie kwiatów
                FlowerManagement.getInstance().getFlowers().get(i).reproduction();
            }
        }
    }
}
