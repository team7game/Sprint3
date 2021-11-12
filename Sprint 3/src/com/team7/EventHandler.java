package com.team7;

import java.awt.event.*;
import java.util.*;

import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.table.*;
import java.util.Random;


public class EventHandler implements KeyListener, ActionListener
{
    //List<int[]> occupiedIDCells = new ArrayList<int[]>();

    List<Object[]> redPlayers = new ArrayList<Object[]>();
    List<Object[]> greenPlayers = new ArrayList<Object[]>();

    final int ROW_LIMIT = 10;

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";

    JTable redTable;
    JTable greenTable;

    JRadioButton redButton;
    JRadioButton greenButton;

    Database database;
    GUI gui;

    int gameTimerDuration = 180;
    int currentGameTime;

    int countdownTimerDuration = 10;
    int currentCountdownTime;

    int userID;

    boolean team; //red = true, green = false

    boolean gameAction;

    int redTeamCount = 0;
    int greenTeamCount = 0;
    
    public EventHandler(GUI g, Database db)
    {
        database = db;
        gui = g;
        gui.addKeyListener(this);
        
        redTable = gui.pes.redTeamTable;
        greenTable = gui.pes.greenTeamTable;

        gui.pes.submitID.addActionListener(this);
        redButton = gui.pes.redTeamButton;

        gui.pes.submitUsername.addActionListener(this);
        greenButton = gui.pes.greenTeamButton;

        gameAction = false;
    }

    @Override
    public void keyPressed(KeyEvent event) 
    {
    
    }

    @Override
    public void keyReleased(KeyEvent event) 
    {

    }

    @Override
    public void keyTyped(KeyEvent event) 
    {
        //System.out.println("Key Typed, " + gameAction);

        if((event.getKeyChar() == KeyEvent.VK_F5 || event.getKeyChar() == 's') && gameAction == false)
        {
            //TEMPORARY
            gameAction = true;
            startCountdownTimer();
            
            
        }
    }    

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("action performed");

        if(e.getSource() == gui.pes.submitID)
        {
            System.out.println("Submitted ID");

            try
            {
                userID = Integer.parseInt(gui.pes.idField.getText());

                if(gui.pes.greenTeamButton.isSelected())
                {
                    team = false;
                }
                else
                {
                    team = true;
                }

                database.connect();

                if(database.checkID(userID))
                {
                    System.out.println("Player exists");

                    addPlayerToTable(userID, database.getName(userID), team);
                }
                else
                {
                    System.out.println("Player does not exist");
                    gui.pes.entryCardLayout.next(gui.pes.entryPanel);
                }

                database.close();
            }
            catch(NumberFormatException exception)
            {
                gui.pes.idField.setText("Invalid Input");
            }

            //Clears text after submission
            gui.pes.idField.setText("");
        }

        if(e.getSource() == gui.pes.submitUsername)
        {
            if(gui.pes.usernameField.getText() != null)
            {
                System.out.println("Created player. ID: " + userID + " Username: " + gui.pes.usernameField.getText());

                addPlayerToTable(userID, gui.pes.usernameField.getText(), team);
                database.createPlayer(userID, gui.pes.usernameField.getText());

                gui.pes.entryCardLayout.next(gui.pes.entryPanel);
            }

            //Clears text after submission
            gui.pes.usernameField.setText("");
        }

        gui.requestFocus();
    }

    void startCountdownTimer()
    {
        gui.pes.countdownLayout.next(gui.pes.countdownPanel);

        Timer countdownTimer = new Timer();
        currentCountdownTime = countdownTimerDuration;

        countdownTimer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                
                //String displayTime = currentTime/60 + ":" + currentTime%60;
                //gui.gas.timerLabel.setText("Time left: " + displayTime);
                //currentTime--;

                currentCountdownTime--;

                gui.pes.countdownLabel.setText("Game starting in " + currentCountdownTime);
        
                if (currentCountdownTime <= 0) {
                    countdownTimer.cancel();
                    switchToGameAction();
                    //gameAction = false
                }
            }

        }, 0, 1000);
    }

    void startGameTimer()
    {
        Timer timer = new Timer();
        currentGameTime = gameTimerDuration;

            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    
                    String minutes = (currentGameTime/60) + "";
                    String seconds = "";
                    
                    if(currentGameTime%60 < 10)
                    {
                        seconds = "0" + currentGameTime%60;
                    }
                    else
                    {
                        seconds = "" + currentGameTime%60;
                    }

                    String displayTime = minutes + ":" + seconds;
                    gui.gas.timerLabel.setText("Time left: " + displayTime);
                    currentGameTime--;
            
                    if (currentGameTime == gameTimerDuration/2){
                        gui.gas.timerLabel.setText("Warning: Half the time remaining");
                    }
            
                    if (currentGameTime == 3){
                        gui.gas.timerLabel.setText("Warning: 3 seconds remaining");
                    }
            
                    if (currentGameTime <= 0) {
                        timer.cancel();
                        gui.gas.timerLabel.setText("Game Over");
                        //gameAction = false
                    }
                }

            }, 0, 1000);
    }

    void switchToGameAction()
    {   
        gui.gas.setActionText(generateActionText());
        gui.cl.next(gui.entryCardPanel);
        gui.gas.addPlayersToScoreboard(redPlayers, greenPlayers);
        startGameTimer();
    }

    String generateActionText()
    {   
        //change font to make mulicolored text?
        String actionText = redPlayers.get(0)[1] + " (" + redPlayers.get(0)[0] + ")     hit     " + greenPlayers.get(0)[1] + " (" + greenPlayers.get(0)[0] + ")";

        return actionText;
    }

    void addPlayerToTable(int id, String username, boolean team)
    {
        if(team)
        {   
            if(redPlayers.size() < ROW_LIMIT)
            {
                DefaultTableModel model = (DefaultTableModel) redTable.getModel();
                model.addRow(new Object[]{id, username});
                redPlayers.add(new Object[]{id, username});
                //redTeamCount++;
            }           
        }
        else
        {
            if(greenPlayers.size() < ROW_LIMIT)
            {
                DefaultTableModel model = (DefaultTableModel) greenTable.getModel();
                model.addRow(new Object[]{id, username});
                greenPlayers.add(new Object[]{id, username});
                //greenTeamCount++;
            }
        }
    }
}






