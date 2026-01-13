package cis;

import cis.threads.Thread1Console;
import cis.threads.Thread2GUI;

/**
 * Controls the overall flow of the program.
 *
 * @author cis1232 BJM
 * @since 2025-01
 */
public class Controller {
    public static void main(String[] args) {
        Thread1Console threadConsole = new Thread1Console();
        Thread threadGUI = new Thread(new Thread2GUI());
        threadConsole.start();
        threadGUI.start();
    }
}
