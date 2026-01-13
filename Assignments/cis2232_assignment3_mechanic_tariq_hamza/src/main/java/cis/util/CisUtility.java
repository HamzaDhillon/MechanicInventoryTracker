package cis.util;

import javax.swing.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

/**
 * Has some useful methods to be used in our programs.
 *
 * @author bjmaclean
 * @since Oct 19, 2021
 */
public class CisUtility {

    private Scanner input = new Scanner(System.in);

    //The isGUI will be used to determine if JOptionPane is used or console
    private boolean isGUI = false;
    public void setIsGUI(boolean isGUI) {
        this.isGUI = isGUI;
    }

    /*
    Methods to get input from the user
     */
    /**
     * Method which will prompt the user and return the value entered as a
     * String.
     *
     * @author BJ MacLean
     * @param prompt The user prompt
     * @return The String entered by the user
     * @since 20181121
     */
    public  String getInputString(String prompt) {

        String userOption;

        if (isGUI) {
            userOption = JOptionPane.showInputDialog(prompt);
        } else {
            System.out.println(prompt);
            userOption = input.nextLine();
        }

        return userOption;
    }

    /**
     * Method which will prompt the user and return an int value.
     *
     * @author BJ MacLean
     * @param prompt The user prompt
     * @return The number entered by the user
     * @since 20181121
     */
    public  int getInputInt(String prompt) {
        String enteredString = getInputString(prompt);
        int entered = Integer.parseInt(enteredString);
        return entered;
    }

    /**
     * Method which will prompt the user and return a double value.
     *
     * @author BJ MacLean
     * @param prompt The user prompt
     * @return The number entered by the user
     * @since 20181121
     */
    public  double getInputDouble(String prompt) {
        String enteredString = getInputString(prompt);
        double entered = Double.parseDouble(enteredString);
        return entered;
    }

    /**
     * Method to display a string for the user
     *
     * @param output The string that will be displayed to the user
     * @since 20181115
     * @author BJM
     */
    public void display(String output) {
        //System.out.println(output);
        if (isGUI) {
            JOptionPane.showMessageDialog(null, output);
        } else {
            System.out.println(output);
        }
    }

    /**
     * Method to input a boolean value.The prompt will have y/n instructions
     appended to it.
     *
     * @author BJ MacLean
     * @param prompt Base prompt for the user
     * @return true/false
     * @since 20200129
     */
    public  boolean getInputBoolean(String prompt) {
        String temp = getInputString(prompt+" (y/n)");
        return temp.equalsIgnoreCase("y") || temp.equalsIgnoreCase("yes") || temp.equalsIgnoreCase("true")
                || temp.equalsIgnoreCase("1");
    }

    /**
     * Return the default currency String value of the double passed in as a
     * parameter.
     *
     * @param inputDouble double to be formatted
     * @return String in default currency format
     *
     * @since 20211020
     * @author BJM
     */
    public String toCurrency(double inputDouble) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(inputDouble);
    }

    /**
     * Provide today's date in the specified format
     *
     * @param format Date format desired
     * @return Today's date in specified format
     * @since 20211021
     * @author BJM
     */
    public String getTodayString(String format) {
        //https://www.javatpoint.com/java-get-current-date

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Get a random number between min and max
     * @since 20211109
     * @author BJM
     */
    public int getRandom(int min, int max){
        Random rand = new Random();
        int theRandomNumber = rand.nextInt((max - min) + 1) + min;
        return theRandomNumber;
    }

}
