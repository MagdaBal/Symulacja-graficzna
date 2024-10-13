public class Flower{
    //prywatna klasa Pest, po której dziedziczą klasy okreslające rodzaj szkodnika na kwiatku
    private class Pest{
        protected int life;
        protected String type;
        protected int dmgDealt = 0; //obrażenia zadawane przez szkodnika
        protected int fullness = 0; //poziom najedzenia szkodnika
        public Pest(int life){
            this.life = life;
            if(life == 0) this.type = "Dead";
        }

        //zmienia poziom najedzenia
        public void changeFullness(int change){
            fullness -= change;
        }
        public void changeLife(int change){
            life -= change;
        }
        public int getLife(){
            return life;
        }
    }
    private class FlyingPest extends Pest{
        public FlyingPest (int life){
            super(life);
            super.type = "Flying";
            super.dmgDealt = Constants.dmgDealtByFlyingPest;
        }
    }
    private class SpiderPest extends Pest{
        public SpiderPest (int life){
            super(life);
            super.type = "Spider";
            super.dmgDealt = Constants.dmgDealtBySpiderPest;
        }
    }
    private class LarvaPest extends Pest{
        public LarvaPest (int life){
            super(life);
            super.type = "Larva";
            super.dmgDealt = Constants.dmgDealtByLarvaPest;
        }
    }
    protected int life;
    protected String type;
    protected int dmgByDay; //obrażenia kwiata ze względu na brak podlania, niezależne od posiadanego szkodnika
    private int x;
    private int y;
    private Pest whichPest; //szkodnik będący obecnie na kwiatku

    public Flower(int x, int y, int life){
        this.x = x;
        this.y = y;
        this.life = life;
        if(this.life == Constants.noLife) {
            this.type = "Dead";
            this.whichPest = new Pest(Constants.noLife);
        }
    }

    public static int numOfNewFlowers = 0; //licznik kwiatów powstających w trakcie symulacji
    public static int numOfNewTulips= 0; //liczba tulipanów powstających w trakcie symulacji
    public static int numOfNewRoses = 0; //liczba róż powstających w trakcie symulacji
    public static int numOfNewIrises = 0; //liczba irysów powstających w trakcie symulacji
    public static int numOfNewPests = 0; //liczba szkodników powstających w trakcie symulacji
    public static int numOfNewSpiders= 0; //liczba pająków powstających w trakcie symulacji
    public static int numOfNewLarvas = 0; //liczba larw powstających w trakcie symulacji
    public static int numOfNewFlyings = 0; //liczba latających szkodników powstających w trakcie symulacji
    //metoda przypisująca kwiatowi odpowiedniego szkodnika
    public void setWhichPest(int pest){
        if(pest == Constants.larvaType){
            this.whichPest = new LarvaPest(Constants.fullLife); //szkodnik typu larwa
        }
        else if(pest == Constants.flyingType){
            this.whichPest = new FlyingPest(Constants.fullLife); //szkodnik typu latający
        }
        else if(pest == Constants.spiderType){
            this.whichPest = new SpiderPest(Constants.fullLife); //szkodnik typu pająk
        }
        else if (pest > Constants.spiderType){
            this.whichPest = new Pest(Constants.noLife); //brak szkodnika
        }
    }
    //rozmnażanie kwiatów
    public void reproduction(){
        if(life > Constants.noLife){
            int willReproduct = FlowerManagement.getInstance().randomInt(Constants.rand10); //losowanie decyzji, czy kwiat się będzie rozmnażał
            if(willReproduct == 1){ //wylosowanie 1 powoduje rozmnażanie
                int howMany = FlowerManagement.getInstance().randomInt(Constants.rand4) + 1; //losowanie ilosci pól, na które kwiat zrzuci nasiona
                int where; //zmienna oznaczająca, na które pole rozmnoży się kwiat
                if(type.equals("Rose") && life >= Constants.roseLifeLowerLimForRep){ //rozmnażanie dla róży
                    for(int i = 0; i < howMany; i++){
                        numOfNewRoses++; //licznik powstających róż
                        where = FlowerManagement.getInstance().randomInt(Constants.rand4); //losowanie miejsca, na które róża zrzuci nasiona
                        placementCheck(where);
                    }
                }
                if(this.type.equals("Tulip") && life <= Constants.tulipLifeUpperLimForRep/* &&
                        whichPest.type.equals("Spider")*/){ //rozmnażanie dla tulipana
                    for(int i = 0; i < howMany; i++){
                        numOfNewTulips++; //licznik powstających tulipanów
                        where = FlowerManagement.getInstance().randomInt(Constants.rand4); //losowanie miejsca, na które tulipan zrzuci nasiona
                        placementCheck(where);
                    }
                }
                if(this.type.equals("Iris") && life >= Constants.irisLifeLowerLimForRep &&
                        whichPest.type.equals("Dead")){ //rozmnażanie dla irysa
                    for(int i = 0; i < howMany; i++){
                        numOfNewIrises++; //licznik powstających irysów
                        where = FlowerManagement.getInstance().randomInt(Constants.rand4); //losowanie miejsca, na które irys zrzuci nasiona
                        placementCheck(where);
                    }
                }
            }
        }
    }
    //metoda pilnująca, żeby nowe kwiaty nie powstawały poza ogrodem i przypisująca im nowe miejsce w ogrodzie w zależnosci od gatunku
    public void placementCheck(int where){
        int newX, newY;
        int choosePest = FlowerManagement.getInstance().randomInt(Constants.rand4);//przy rozmnażaniu nowy kwiat dostaje losowego szkodnika
        newPestCounting(choosePest); //zliczenie powstałych szkodników
        if(type.equals("Rose")){ //dla róży
            if(where == 0){
                newX = x-1;
                newY = y-1;
                if(newX == 0) newX = x + 1;
                if(newY == 0) newY = y + 1;
                Rose newRose = new Rose(newX, newY, Constants.fullLife);
                newRose.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newRose);
            }
            if(where == 1){
                newX = x+1;
                newY = y-1;
                if(newX == Constants.gardenXEnding) newX = x - 1;
                if(newY == 0) newY = y + 1;
                Rose newRose = new Rose(newX, newY, Constants.fullLife);
                newRose.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newRose);
            }
            if(where == 2){
                newX = x-1;
                newY = y+1;
                if(newX == 0) newX = x + 1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                Rose newRose = new Rose(newX, newY, Constants.fullLife);
                newRose.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newRose);
            }
            if(where == 3){
                newX = x+1;
                newY = y+1;
                if(newX == Constants.gardenXEnding) newX = x - 1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                Rose newRose = new Rose(newX, newY, Constants.fullLife);
                newRose.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newRose);
            }
        }
        if(type.equals("Tulip")){ //dla tulipana
            if(where == 0){
                newX = x;
                newY = y-1;
                if(newY == 0) newY = y + 1;
                Tulip newTulip = new Tulip(newX, newY, Constants.fullLife);
                newTulip.setWhichPest(choosePest); //przy rozmnażaniu tulipana ten nowy na początku nie ma szkodnika
                placeNewFlower(newX, newY, newTulip);
            }
            if(where == 1){
                newX = x-1;
                newY = y+1;
                if(newX == 0) newX = x;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                Tulip newTulip = new Tulip(newX, newY, Constants.fullLife);
                newTulip.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newTulip);
            }
            if(where == 2){
                newX = x;
                newY = y+1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                Tulip newTulip = new Tulip(newX, newY, Constants.fullLife);
                newTulip.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newTulip);
            }
            if(where == 3){
                newX = x+1;
                newY = y+1;
                if(newX == Constants.gardenXEnding) newX = x;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                Tulip newTulip = new Tulip(newX, newY, Constants.fullLife);
                newTulip.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newTulip);
            }
        }
        if(type.equals("Iris")){ //dla irysa
            if(where == 0){
                newX = x;
                newY = y-1;
                if(newY == 0) newY = y + 1;
                Iris newIris = new Iris(newX, newY, Constants.fullLife);
                newIris.setWhichPest(choosePest); //przy rozmnażaniu irysa ten nowy na początku nie ma szkodnika
                placeNewFlower(newX, newY, newIris);
            }
            if(where == 1){
                newX = x-1;
                newY = y;
                if(newX == 0) newX = x + 1;
                Iris newIris = new Iris(newX, newY, Constants.fullLife);
                newIris.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newIris);
            }
            if(where == 2){
                newX = x;
                newY = y+1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                Iris newIris = new Iris(newX, newY, Constants.fullLife);
                newIris.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newIris);
            }
            if(where == 3){
                newX = x+1;
                newY = y;
                if(newX == Constants.gardenXEnding) newX = x;
                Iris newIris = new Iris(newX, newY, Constants.fullLife);
                newIris.setWhichPest(choosePest);
                placeNewFlower(newX, newY, newIris);
            }
        }
    }
    public void newPestCounting(int whichPest){
        if(whichPest == Constants.spiderType){
            numOfNewPests++;
            numOfNewSpiders++;
        }
        if(whichPest == Constants.larvaType){
            numOfNewPests++;
            numOfNewLarvas++;
        }
        if(whichPest == Constants.flyingType){
            numOfNewPests++;
            numOfNewFlyings++;
        }
    }
    //metoda dodająca nowy kwiat do ogrodu, jesli na wybranym miejscu nie ma jeszcze żadnego kwiatka
    public void placeNewFlower(int newX, int newY, Flower newFlower){
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            if(FlowerManagement.getInstance().getFlowers().get(i).getX() == newX &&
                    FlowerManagement.getInstance().getFlowers().get(i).getY() == newY &&
                    FlowerManagement.getInstance().getFlowers().get(i).getLife() == Constants.noLife){
                FlowerManagement.getInstance().getFlowers().remove(i);
                FlowerManagement.getInstance().getFlowers().add(newFlower);
            }
        }
        numOfNewFlowers++; //licznik wszystkich nowo powstałych kwiatów
    }
    //metoda przeprowadzająca rozmnażanie szkodników
    public void pestReproduction(){
        int where; //losuje miejsce, na którym pojawi się nowy szkodnik
        int howMany = FlowerManagement.getInstance().randomInt(Constants.rand4) + 1;
        int pestType;
        if(whichPest.type.equals("Spider")){ //dla pająka
            pestType = Constants.spiderType;
            newPestCounting(pestType);
            whichPest.fullness -= Constants.spiderRepCost; //koszt energetyczny reprodukcji dla pająka
            for(int i = 0; i < howMany; i++){
                where = FlowerManagement.getInstance().randomInt(Constants.rand4);
                placementCheckForPests(where, pestType);
            }
        }
        if(whichPest.type.equals("Larva")){ //dla larwy
            pestType = Constants.larvaType;
            newPestCounting(pestType);
            whichPest.fullness -= Constants.larvaRepCost; //koszt energetyczny dla larwy
            for(int i = 0; i < howMany; i++){
                where = FlowerManagement.getInstance().randomInt(Constants.rand4);
                placementCheckForPests(where, pestType);
            }
        }
        if(whichPest.type.equals("Flying")){ //dla latających szkodników
            pestType = Constants.flyingType;
            newPestCounting(pestType);
            whichPest.fullness -= Constants.flyingRepCost; //koszt energetyczny reprodukcji dla latającego szkodnika
            for(int i = 0; i < howMany; i++){
                where = FlowerManagement.getInstance().randomInt(Constants.rand4);
                placementCheckForPests(where, pestType);
            }
        }
    }
    public void placementCheckForPests(int where, int pestType){
        int newX, newY;
        if(pestType == Constants.spiderType){
            if(where == 0){
                newX = x - 1;
                newY = y - 1;
                if(newX == 0) newX = x + 1;
                if(newY == 0) newY = y + 1;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 1){
                newX = x + 1;
                newY = y - 1;
                if(newX == Constants.gardenXEnding) newX = x - 1;
                if(newY == 0) newY = y + 1;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 2){
                newX = x-1;
                newY = y+1;
                if(newX == 0) newX = x + 1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 3){
                newX = x+1;
                newY = y+1;
                if(newX == Constants.gardenXEnding) newX = x - 1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                placeNewPest(newX, newY, pestType);
            }
        }
        if(pestType ==Constants.flyingType){
            if(where == 0){
                newX = x - 2;
                newY = y - 2;
                if(newX == 0) newX = x + 2;
                if(newY == 0) newY = y + 2;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 1){
                newX = x + 2;
                newY = y - 2;
                if(newX == Constants.gardenXEnding) newX = x - 2;
                if(newY == 0) newY = y + 2;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 2){
                newX = x - 2;
                newY = y + 2;
                if(newX == 0) newX = x + 2;
                if(newY == Constants.gardenYEnding) newY = y - 2;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 3){
                newX = x + 2;
                newY = y + 2;
                if(newX == Constants.gardenXEnding) newX = x - 2;
                if(newY == Constants.gardenYEnding) newY = y - 2;
                placeNewPest(newX, newY, pestType);
            }
        }
        if(pestType == Constants.larvaType){
            if(where == 0){
                newX = x;
                newY = y-1;
                if(newY == 0) newY = y + 1;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 1){
                newX = x-1;
                newY = y;
                if(newX == 0) newX = x + 1;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 2){
                newX = x;
                newY = y+1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                placeNewPest(newX, newY, pestType);
            }
            if(where == 3){
                newX = x+1;
                newY = y;
                if(newX == Constants.gardenXEnding) newX = x;
                placeNewPest(newX, newY, pestType);
            }
        }
    }
    //metoda odpowiadająca za przemieszczanie się szkodników
    public void pestMovement(){
        int where = FlowerManagement.getInstance().randomInt(Constants.rand4); //losowanie miejsca, na które się przeniesie
        int newX, newY;
        int pestType;
        if(whichPest.type.equals("Spider")){ //dla pająka
            pestType = Constants.spiderType;
            whichPest.fullness -= Constants.spiderMoveCost; //koszt energetyczny poruszania dla pająka
            if(where == 0){
                newX = x - 1;
                newY = y - 1;
                if(newX == 0) newX = x + 1;
                if(newY == 0) newY = y + 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 1){
                newX = x + 1;
                newY = y - 1;
                if(newX == Constants.gardenXEnding) newX = x - 1;
                if(newY == 0) newY = y + 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 2){
                newX = x-1;
                newY = y+1;
                if(newX == 0) newX = x + 1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 3){
                newX = x+1;
                newY = y+1;
                if(newX == Constants.gardenXEnding) newX = x - 1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
        }
        if(whichPest.type.equals("Larva")){ //dla larwy
            pestType = Constants.larvaType;
            whichPest.fullness -= Constants.larvaMoveCost; //koszt energetyczny poruszania dla larwy
            if(where == 0){
                newX = x;
                newY = y-1;
                if(newY == 0) newY = y + 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 1){
                newX = x-1;
                newY = y;
                if(newX == 0) newX = x + 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 2){
                newX = x;
                newY = y+1;
                if(newY == Constants.gardenYEnding) newY = y - 1;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 3){
                newX = x+1;
                newY = y;
                if(newX == Constants.gardenXEnding) newX = x;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
        }
        if(whichPest.type.equals("Flying")){ //dla latającego szkodnika
            pestType = Constants.flyingType;
            whichPest.fullness -= Constants.flyingMoveCost; //koszt energetyczny przemieszczania dla latającego szkodnika
            if(where == 0){
                newX = x - 2;
                newY = y - 2;
                if(newX == 0) newX = x + 2;
                if(newY == 0) newY = y + 2;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 1){
                newX = x + 2;
                newY = y - 2;
                if(newX == Constants.gardenXEnding) newX = x - 2;
                if(newY == 0) newY = y + 2;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 2){
                newX = x - 2;
                newY = y + 2;
                if(newX == 0) newX = x + 2;
                if(newY == Constants.gardenYEnding) newY = y - 2;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
            if(where == 3){
                newX = x + 2;
                newY = y + 2;
                if(newX == Constants.gardenXEnding) newX = x - 2;
                if(newY == Constants.gardenYEnding) newY = y - 2;
                placeNewPest(newX, newY, pestType);
                killPest();
            }
        }
    }
    //metoda umieszczająca nowe szkodniki na mapie, jesli wybrane miejsce jest wolne
    public void placeNewPest(int newX, int newY, int pestType){
        for(int i = 0; i < FlowerManagement.getInstance().getListSize(); i++){
            if(FlowerManagement.getInstance().getFlowers().get(i).getX() == newX &&
                    FlowerManagement.getInstance().getFlowers().get(i).getY() == newY &&
                    FlowerManagement.getInstance().getFlowers().get(i).isAlive() &&
                    FlowerManagement.getInstance().getFlowers().get(i).getPestType().equals("Dead")){
                FlowerManagement.getInstance().getFlowers().get(i).setWhichPest(pestType);
            }
        }
    }
    //metoda do karmienia szkodników ze względu na ich gatunek i tego, na jakim kwiacie się znajdują
    public void feedPest(){
        if(type.equals("Rose")){
            if(whichPest.type.equals("Spider")){
                whichPest.changeFullness(Constants.roseFeedSpider);
            }
            if(whichPest.type.equals("Flying")){
                whichPest.changeFullness(Constants.roseFeedFlying);
            }
            if(whichPest.type.equals("Larva")){
                whichPest.changeFullness(Constants.roseFeedLarva);
            }
        }
        if(type.equals("Tulip")){
            if(whichPest.type.equals("Spider")){
                whichPest.changeFullness(Constants.tulipFeedSpider);
            }
            if(whichPest.type.equals("Flying")){
                whichPest.changeFullness(Constants.tulipFeedFlying);
            }
            if(whichPest.type.equals("Larva")){
                whichPest.changeFullness(Constants.tulipFeedLarva);
            }
        }
        if(type.equals("Iris")){
            if(whichPest.type.equals("Spider")){
                whichPest.changeFullness(Constants.irisFeedSpider);
            }
            if(whichPest.type.equals("Flying")){
                whichPest.changeFullness(Constants.irisFeedFlying);
            }
            if(whichPest.type.equals("Larva")){
                whichPest.changeFullness(Constants.irisFeedLarva);
            }
        }
    }
    //metoda do usmiercania kwiatów
    public static Flower death(Flower flower){
        return new Flower(flower.getX(), flower.getY(), Constants.noLife);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getType(){
        return type;
    }
    public int getLife(){
        return life;
    }
    //metoda do usmiercania szkodników
     void killPest(){
        this.whichPest.changeLife(25);
        if(this.whichPest.getLife() == 0)
            this.whichPest = new Pest(Constants.noLife);
    }
    public String getPestType(){return whichPest.type;}
    public int getPestFulness(){
        return whichPest.fullness;
    }
    public void setLife(int life){
        this.life = life;
    }
    //zmiana ilosci życia kwiatka
    public void changeLife(int change){
        this.life -= change;
    }
    //suma obrażeń zadanych kwiatowi w ciągu dnia
    public int dmgDealt(){
        return dmgByDay + whichPest.dmgDealt;
    }
    public boolean isAlive(){
        if(this.life > Constants.noLife) return true;
        return false;
    }
}
