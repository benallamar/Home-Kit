import HomKit.Home;
import Interfaces.IHMHome.IHMHome;

public class Main {
    public static void main(String[] args) {
        //Run directly the whole system.
        try {
            new IHMHome();
            Home.run();
        } catch (InterruptedException e) {

        }

    }
}