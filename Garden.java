import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Garden extends Canvas {
    private static final Garden instance = new Garden();
    public static Garden getInstance(){
        return instance;
    }
    private Garden(){
        setSize(Constants.columns * Constants.size,Constants.rows * Constants.size);
    }
    
    @Override
    public void update(Graphics g){
        Graphics backg; // background g
        Image backScreen = null;

        // tworzenie tylnego ekranu
        backScreen = createImage(Constants.columns * Constants.size,Constants.rows * Constants.size);
        backg = backScreen.getGraphics();
        backg.setColor(getBackground());
        backg.fillRect(0, 0, Constants.columns * Constants.size,Constants.rows * Constants.size);
        backg.setColor(getForeground());

        String[] ImageTypes = {
                "Rose0.png", "Rose1.png", "Rose2.png", "Rose3.png",
                "Iris0.png", "Iris1.png", "Iris2.png", "Iris3.png",
                "Tulip0.png", "Tulip1.png", "Tulip2.png", "Tulip3.png",
                "FlyingPest.png", "SpiderPest.png", "LarvaPest.png"};

        Map<String, Image> ImageMap = new HashMap<>();

        for(int i=0; i<ImageTypes.length; i++){
            try {
                File path = new File("obrazki" + File.separator + ImageTypes[i]);
                if (!path.exists()) {
                    System.err.println("File doesn't exist: " + path.getAbsolutePath());
                    continue;
                }
                Image image = ImageIO.read(path);
                if (image == null) {
                    throw new IOException("Image can't be read: " + path.getAbsolutePath());
                }
                ImageMap.put(ImageTypes[i], image);
            } catch (IOException ex) {
                System.err.println("Failed to load image: " + ImageTypes[i]);
                ex.printStackTrace();
            }
        }
        Insets insets = new Insets(0,0,0,0);

        // tworzenie zielonej planszy
        backg.setColor(Color.green);
        backg.fillRect(insets.left+Constants.size, insets.top+Constants.size, Constants.gardenSize*Constants.size+1, getHeight() - insets.bottom - insets.top-2*Constants.size);

        //ustalanie koloru ramki okienka ze względu na pogodę
        int weather = Weather.weather();
        if(weather <= Constants.sunWeatherLim){
            Weather.sun();
            backg.setColor(Color.yellow);
        }
        else if(weather <= Constants.rainWeatherUpperLim){
            Weather.rain();
            backg.setColor(Color.blue);
        }
        else if(weather == Constants.droughtWeather){
            Weather.drought();
            backg.setColor(Color.gray);
        }
        else if(weather == Constants.hurricaneWeather){
            Weather.hurricane();
            backg.setColor(Color.black);
        }
        else if(weather == Constants.snowWeatherOption1 || weather == Constants.snowWeatherOption2){
            Weather.snow();
            backg.setColor(Color.white);
        }


        //tworzenie ramki pokazującej pogodę
        backg.fillRect(insets.left, insets.top, getWidth() - insets.right - insets.left, Constants.size); // góra
        backg.fillRect(insets.left, insets.top, Constants.size, getHeight() - insets.bottom + insets.top ); // lewy bok
        backg.fillRect(insets.left, getHeight() - Constants.size - insets.bottom, getWidth() - insets.right - insets.left, Constants.size); // dół
        backg.fillRect(getWidth() - Constants.size - insets.right, insets.top, Constants.size, getHeight() - insets.bottom - insets.top); // prawy bok

        for (int i = 0; i < FlowerManagement.getInstance().getListSize(); i++) {
            if(FlowerManagement.getInstance().getFlowers().get(i).getPestType().equals("Dead")){  //kwiat bez szkodnika
                if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Rose")){
                    backg.drawImage(ImageMap.get("Rose0.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // róża
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Iris")){
                    backg.drawImage(ImageMap.get("Iris0.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // irys
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Tulip")){
                    backg.drawImage(ImageMap.get("Tulip0.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // tulipan
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }

            }
            else if(FlowerManagement.getInstance().getFlowers().get(i).getPestType().equals("Larva")){  //kwiat z larwą
                backg.setColor(Color.pink);
                if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Rose")){
                    backg.drawImage(ImageMap.get("Rose3.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // róża
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Iris")){
                    backg.drawImage(ImageMap.get("Iris3.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // irys
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Tulip")){
                    backg.drawImage(ImageMap.get("Tulip3.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // tulipan
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }

            }
            else if(FlowerManagement.getInstance().getFlowers().get(i).getPestType().equals("Spider")){  //kwiat z pająkiem
                backg.setColor(Color.red);
                if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Rose")){
                    backg.drawImage(ImageMap.get("Rose2.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // róża
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Iris")){
                    backg.drawImage(ImageMap.get("Iris2.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // irys
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Tulip")){
                    backg.drawImage(ImageMap.get("Tulip2.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // tulipan
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }

            }
            else if(FlowerManagement.getInstance().getFlowers().get(i).getPestType().equals("Flying")){  //kwiat z latającym szkodnikiem
                backg.setColor(Color.orange);
                if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Rose")){
                    backg.drawImage(ImageMap.get("Rose1.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // róża
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Iris")){
                    backg.drawImage(ImageMap.get("Iris1.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // irys
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }
                else if(FlowerManagement.getInstance().getFlowers().get(i).type.equals("Tulip")){
                    backg.drawImage(ImageMap.get("Tulip1.png"), FlowerManagement.getInstance().getFlowers().get(i).getX() * Constants.size,    // tulipan
                            FlowerManagement.getInstance().getFlowers().get(i).getY() * Constants.size, null);
                }

            }

        }

        backg.setColor(Color.black);
        backg.setFont(new Font("Default", Font.BOLD, 20));
        backg.drawString("Legenda pogody:", Constants.size * (Constants.columns - 10),Constants.size * 3);
        backg.setFont(new Font("Default", Font.PLAIN, 14));
        backg.setColor(Color.yellow);
        backg.fillRect(Constants.size * (Constants.columns - 10),Constants.size * 4, Constants.size, Constants.size);
        backg.setColor(Color.black);
        backg.drawString("           Słońce", Constants.size * (Constants.columns - 10),Constants.size * 5);
        backg.setColor(Color.blue);
        backg.fillRect(Constants.size * (Constants.columns - 10),Constants.size * 6, Constants.size, Constants.size);
        backg.setColor(Color.black);
        backg.drawString("           Deszcz", Constants.size * (Constants.columns - 10),Constants.size * 7);
        backg.setColor(Color.gray);
        backg.fillRect(Constants.size * (Constants.columns - 10),Constants.size * 8, Constants.size, Constants.size);
        backg.setColor(Color.black);
        backg.drawString("           Susza", Constants.size * (Constants.columns - 10),Constants.size * 9);
        backg.setColor(Color.white);
        backg.fillRect(Constants.size * (Constants.columns - 10),Constants.size * 10, Constants.size, Constants.size);
        backg.setColor(Color.black);
        backg.drawString("           Śnieg", Constants.size * (Constants.columns - 10),Constants.size * 11);
        backg.setColor(Color.black);
        backg.fillRect(Constants.size * (Constants.columns - 10),Constants.size * 12, Constants.size, Constants.size);
        backg.drawString("           Huragan", Constants.size * (Constants.columns - 10),Constants.size * 13);
        backg.setColor(Color.black);
        backg.setFont(new Font("Default", Font.BOLD, 20));
        backg.drawString("Legenda", Constants.size * (Constants.columns - 10),Constants.size * 15);
        backg.drawString("kwiatów i szkodników:", Constants.size * (Constants.columns - 10),Constants.size * 16);
        backg.setFont(new Font("Default", Font.PLAIN, 14));
        backg.drawImage(ImageMap.get("Rose0.png"), Constants.size * (Constants.columns - 10), Constants.size * 17, Constants.size, Constants.size,null);
        backg.setColor(Color.black);
        backg.drawString("           Róża", Constants.size * (Constants.columns - 10),Constants.size * 18);
        backg.drawImage(ImageMap.get("Iris0.png"), Constants.size * (Constants.columns - 10), Constants.size * 19, Constants.size, Constants.size,null);
        backg.setColor(Color.black);
        backg.drawString("           Irys", Constants.size * (Constants.columns - 10),Constants.size * 20);
        backg.drawImage(ImageMap.get("Tulip0.png"), Constants.size * (Constants.columns - 10), Constants.size * 21, Constants.size, Constants.size,null);
        backg.setColor(Color.black);
        backg.drawString("           Tulipan", Constants.size * (Constants.columns - 10),Constants.size * 22);
        backg.drawImage(ImageMap.get("FlyingPest.png"), Constants.size * (Constants.columns - 6), Constants.size * 17, Constants.size, Constants.size,null);
        backg.setColor(Color.black);
        backg.drawString("           Szkodnik 1", Constants.size * (Constants.columns - 6),Constants.size * 18);
        backg.drawImage(ImageMap.get("SpiderPest.png"), Constants.size * (Constants.columns - 6), Constants.size * 19, Constants.size, Constants.size,null);
        backg.setColor(Color.black);
        backg.drawString("           Szkodnik 2", Constants.size * (Constants.columns - 6),Constants.size * 20);
        backg.drawImage(ImageMap.get("LarvaPest.png"), Constants.size * (Constants.columns - 6), Constants.size * 21, Constants.size, Constants.size,null);
        backg.setColor(Color.black);
        backg.drawString("           Szkodnik 3", Constants.size * (Constants.columns - 6),Constants.size * 22);

        paint(backg);
        // nadpisywanie ekranu
        g.drawImage(backScreen, 0, 0, this);
    }
    @Override
    public void paint(Graphics g) {
    }
}
