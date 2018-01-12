package homekit;

import component.Equipement;
import java.io.IOException;
import java.util.HashSet;
import ui.IHMHome.IHMHome;
import ui.IHMHome.Loading;

/**
 * Project Name : TL_crypto
 */
public class Home extends Thread implements Runnable {

  private HashSet<Equipement> equipements = new HashSet<Equipement>();
  private static IHMHome homeInterface = null;
  private boolean check_new_equi = false;
  final static int TIME_CHECK = 5000; //Check new equipments every TIME_CHECK seconds
  public static Loading loadPage = null;
  public static boolean newCo = false;

  public void checkComponent() {
    try {
      //TODO: Remplement the components loader
    } catch (IOException e) {
      System.out.print(e.fillInStackTrace());
    }
  }

  //Check for new componenet
  public void checkNewComponent() {
    while (true) {
      try {
        checkComponent();
        Thread.sleep(TIME_CHECK);
        if (newCo) {
          homeInterface.update();
        }
      } catch (InterruptedException e) {

      }
    }
  }

  public void run() {
    if (!check_new_equi) {
      //TODO: Reimplement the runner code (optimize the generation of useless threads)
      loadPage = new Loading();
      checkComponent();
      homeInterface = new IHMHome(equipements);
      homeInterface.update();
      check_new_equi = true;
      new Thread(this).start();
    } else {
      checkNewComponent();
    }

  }

  public static void update() {
    homeInterface.update();
  }
}
