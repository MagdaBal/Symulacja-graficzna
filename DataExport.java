import java.io.FileWriter;
import java.io.IOException;

public class DataExport {
    public static void export(){
        try{
            FileWriter writer = new FileWriter("dane_ogrod.txt");
            writer.write("Liczba wszystkich kwiatów utworzonych na początku: " + FlowerManagement.numOfFlowersBeginning +
                    "\nW tym:\n" + FlowerManagement.numOfRosesBeginning + " róż\n" +
                     FlowerManagement.numOfTulipsBeginning + " tulipanów\n" +
                     FlowerManagement.numOfIrisesBeginning + " irysów\n\n");
            writer.write("Liczba nowo powstałych kwiatów w trakcie symulacji: " + Flower.numOfNewFlowers +
                    "\nW tym:\n" + Flower.numOfNewRoses + " róż\n" +
                    Flower.numOfNewTulips + " tulipanów\n" +
                    Flower.numOfNewIrises + " irysów\n\n");
            writer.write("Liczba wszystkich szkodników utworzonych na początku: " + FlowerManagement.numOfPestsBeginning +
                    "\nW tym:\n" + FlowerManagement.numOfSpidersBeginning + " pająków\n" +
                    FlowerManagement.numOfLarvasBeginning + " larw\n" +
                    FlowerManagement.numOfFlyingsBeginning + " latających\n\n");
            writer.write("Liczba nowo powstałych szkodników w trakcie symulacji: " + Flower.numOfNewPests +
                    "\nW tym:\n" + Flower.numOfNewSpiders + " pająków\n" +
                    Flower.numOfNewLarvas + " larw\n" +
                    Flower.numOfNewFlyings + " latających\n\n");
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
