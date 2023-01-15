package com.techelevator;

import com.techelevator.DAO.ClimberDao;
import com.techelevator.DAO.JdbcClimberDao;
import com.techelevator.Model.Climber;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;

public class ClimberDbCLI {
    private final ClimberDao climberDao;
    private Scanner input = new Scanner(System.in);
    private static String url = "jdbc:postgresql://localhost:5432/climber";
    private static String username = "postgres";
    private static String password = "postgres1";
    private String[] daysOfTheWeek = new String[]{"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

    public ClimberDbCLI(DataSource dataSource){
        climberDao = new JdbcClimberDao(dataSource);
    }

    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        ClimberDbCLI application = new ClimberDbCLI(dataSource);
        application.run();
    }

    public void run(){
        boolean running = true;
        dispalyBanner();
        while(running){
            displayMainMenu();
            int option = promptForInt("Select an option please: ");
            if(option == 1){
                displayClimbers(climberDao.getAllClimber());
            } else if(option == 2){
                addClimber();
            } else if(option == 3){
                displayClimbers(climberDao.getAllClimber());
                updateClimber();
            } else if(option == 4){
                setAvailability();
            } else if(option == 5){
                running = false;
            } else{
                displayError("Invalid option");
            }
        }
    }

    public void addClimber(){
        boolean done = false;
        String name = "";
        while(!done) {
            name = promptForString("What is the climber's name?");
            int answer = promptForInt("The climber's name is " + name + ". " +
                    "\nIs that correct? " +
                    "\n1. Yes " +
                    "\n2. No\n");
            if(answer==1){
                done=true;
            }
        }
        climberDao.insertClimber(new Climber(name));
    }

    public void updateClimber(){
        int climberId = promptForInt("Select the number of the climber you'd like to update: \n");
        Climber climberToUpdate = climberDao.getClimber(climberId);
        String name = promptForString("Enter the new name of the climber or press enter to leave unchanged: ");
        if(name != null && name.length()>0){
            climberToUpdate.setClimberName(name);
        }
        int injured = promptForInt("Are they injured?\n1. Yes \n2. No \n");
        climberToUpdate.setInjured(injured==1);
        climberDao.updateClimber(climberToUpdate);
    }

    public void setAvailability(){
        boolean done = false;
        while(!done) {
            displayClimbers(climberDao.getAllClimber());
            int climberId = promptForInt("Which climber would you like to set the availability for: ");
            Climber climber = climberDao.getClimber(climberId);
            for (int i = 0; i < daysOfTheWeek.length; i++) {
                displayMessage((i + 1) + ". " + daysOfTheWeek[i]);
            }
            displayMessage(climber.getClimberName() + " is currently available on the following days:");
            displayClimberAvailability(climber);
            int dayIndex = promptForInt("What day would you like to make this climber available? Please select only one");
            String day = "";
            try {
                day = daysOfTheWeek[dayIndex];
            } catch (Exception e) {
                displayError("Please select a number from 1 to 7");
            }
            if (day.length() > 0) {
                climber.addDayAvailable(day);
            }
            climberDao.setClimberAvailability(climber);
            int option = promptForInt("Would you like to change another day? \n1. Yes \n2. No");
            done = option!=1;
        }


    }

    public void displayClimbers(List<Climber> climbers){
        for(Climber climber : climbers){
            System.out.println(climber.getClimberId() + ". " +climber);
        }
        System.out.println();
    }

    public void displayClimberAvailability(Climber climber){
        int count = 1;
        for (String day : daysOfTheWeek) {
            if (climber.getDaysAvailable().contains(day)){
                displayMessage(count + ". " + day);
                count++;
            }
        }
    }

    public void displayMainMenu(){
        System.out.println();
        System.out.println("1. View the climbing group");
        System.out.println("2. Add a climber to the group");
        System.out.println("3. Update a climber");
        System.out.println("4. Set days available for a climber");
        System.out.println("5. Exit");
        System.out.println();
    }


    private int promptForInt(String prompt) {
        return (int) promptForDouble(prompt);
    }

    private double promptForDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String response = input.nextLine();
            try {
                return Double.parseDouble(response);
            }  catch (NumberFormatException e) {
                if (response.isBlank()) {
                    return -1; //Assumes negative numbers are never valid entries.
                } else {
                    displayError("Numbers only, please.");
                }
            }
        }
    }
    private String promptForString(String prompt){
        System.out.println(prompt);
        return input.nextLine();
    }

    private void displayError(String message)
    {
        System.out.println();
        System.out.println("*** " + message + "***");
        System.out.println();
    }

    private void displayMessage(String message){
        System.out.println(message);
    }

    private void dispalyBanner(){
        System.out.println();
        System.out.println("*****************************************");
        System.out.println("**************  Welcome  ****************");
        System.out.println("*****************************************");
        System.out.println();
    }







}
