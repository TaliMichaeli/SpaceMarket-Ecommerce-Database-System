package SpaceMarket;

//the "clean" menu 
public class Main{
	public static void main(String[] args) {
		Market market = new Market(new EntityFactory());
        SpaceMarketControlFacade spaceMarket = SpaceMarketControlFacade.getInstance(market); // Singleton
		spaceMarket.start();
	}
}























