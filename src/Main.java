import HomKit.Home;
import Interfaces.IHMHome.IHMHome;

public class Main {
    public static void main(String[] args) {
        //Run directly the whole system.
        new IHMHome();
        Home.run();
    }
}