package com.team7;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.*;
import java.util.List;

public class GUI extends JFrame {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    int titleSize = (int)screenSize.getHeight()/30;
    int headerSize = (int)screenSize.getHeight()/40;
    int bodySize = (int)screenSize.getHeight()/50;

    Font titleFont = new Font("Tahoma", Font.BOLD, titleSize);
    Font headerFont = new Font("Tahoma", Font.PLAIN, headerSize);
    Font bodyFont = new Font("Tahoma", Font.PLAIN, bodySize);

    Font myFont = new Font("Arial", Font.BOLD, 18);
    
    JPanel mainPanel;
    JPanel entryCardPanel;

    PlayerEntryScreen pes;
    GameActionScreen gas;
    ButtonPanel bp;
    CardLayout cl;
    
    public GUI() 
    {
        super("Photon");
        mainPanel = new JPanel();
        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new BorderLayout());

        cl = new CardLayout();
        entryCardPanel = new JPanel(cl);

        pes = new PlayerEntryScreen();

        gas = new GameActionScreen();

        bp = new ButtonPanel();

        mainPanel.add(entryCardPanel);
        //mainPanel.add(bp);

        entryCardPanel.add(pes);
        entryCardPanel.add(gas);

        pes.setOpaque(true);

        setSize(screenSize.width, screenSize.height);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel); 
        //pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setVisible(true);  
    }

    class PlayerEntryScreen extends JPanel{

        JTextField idField;
        JTextField usernameField;

        JButton submitID;
        JButton submitUsername;

        ButtonGroup group;
        JRadioButton redTeamButton;
        JRadioButton greenTeamButton;

	    JTable redTeamTable;
	    JTable greenTeamTable;

        DefaultTableModel redTeamModel;
        DefaultTableModel greenTeamModel;

        JPanel entryPanel;
        CardLayout entryCardLayout;
	    
        JPanel countdownPanel;
        CardLayout countdownLayout;
        JLabel countdownLabel;

        PlayerEntryScreen()
        {
            setLayout(new BorderLayout(0, screenSize.height/20));
            setBackground(Color.BLACK);

            entryCardLayout = new CardLayout();
            group = new ButtonGroup();

            entryPanel = new JPanel();
            add(entryPanel, "North");
            entryPanel.setLayout(entryCardLayout);
            
            JPanel idPanel = new JPanel();
            idPanel.setBackground(Color.BLACK);
            entryPanel.add(idPanel, "name_275215514144300");
            idPanel.setLayout(new FlowLayout(FlowLayout.CENTER, screenSize.width/40, screenSize.height/40));

            JLabel idLabel = new JLabel("ID: ");
            idLabel.setFont(headerFont);
            idLabel.setForeground(Color.WHITE);
            idLabel.setBackground(Color.BLACK);
            idPanel.add(idLabel);

            idField = new JTextField();
            idField.setFont(headerFont);
            idPanel.add(idField);
            idField.setColumns(screenSize.width/200);   //length of text field
            
            redTeamButton = new JRadioButton("Red", true);
            redTeamButton.setFont(headerFont);
            redTeamButton.setForeground(Color.RED);
            redTeamButton.setBackground(Color.BLACK);
            idPanel.add(redTeamButton);
            group.add(redTeamButton);
            
            greenTeamButton = new JRadioButton("Green");
            greenTeamButton.setFont(headerFont);
            greenTeamButton.setForeground(Color.GREEN);
            greenTeamButton.setBackground(Color.BLACK);
            idPanel.add(greenTeamButton);
            group.add(greenTeamButton);
            
            submitID= new JButton("Submit");
            submitID.setFocusable(false);
            submitID.setFont(headerFont);
            idPanel.add(submitID);
            
            JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, screenSize.width/40, screenSize.height/40));
            usernamePanel.setBackground(Color.BLACK);
            entryPanel.add(usernamePanel, "name_275236543660799");

            JLabel usernameLabel = new JLabel("Username: ");
            usernameLabel.setFont(headerFont);
            usernameLabel.setBackground(Color.BLACK);
            usernameLabel.setForeground(Color.WHITE);
            usernamePanel.add(usernameLabel);
            
            usernameField = new JTextField();
            usernameField.setFont(headerFont);
            usernamePanel.add(usernameField);
            usernameField.setColumns(screenSize.width/150);       //length of text field
            
            submitUsername = new JButton("Submit");
            submitUsername.setFocusable(false);
            submitUsername.setFont(headerFont);
            usernamePanel.add(submitUsername);
            
            JPanel displayPanel = new JPanel();
            displayPanel.setBackground(Color.BLACK);
            add(displayPanel, "Center");
            displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));
            
            JPanel redTeamPanel = new JPanel(new BorderLayout());
            redTeamPanel.setBackground(Color.BLACK);
            displayPanel.add(redTeamPanel);
            
            JLabel redTeamLabel = new JLabel("Red Team");
            redTeamLabel.setFont(titleFont);
            redTeamLabel.setForeground(Color.RED);
            redTeamLabel.setBackground(Color.BLACK);
            redTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            redTeamPanel.add(redTeamLabel, "North");
            
            redTeamTable = new JTable();

            redTeamModel = new DefaultTableModel(0, 2){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            redTeamTable.setModel(redTeamModel);
            setTableLook(redTeamTable, Color.BLACK, Color.RED);
            redTeamPanel.add(redTeamTable, "Center");
            
            JPanel greenTeamPanel = new JPanel(new BorderLayout());
            greenTeamPanel.setBackground(Color.BLACK);
            displayPanel.add(greenTeamPanel);
            
            JLabel greenTeamLabel = new JLabel("Green Team");
            greenTeamLabel.setFont(titleFont);
            greenTeamLabel.setForeground(Color.GREEN);
            greenTeamLabel.setBackground(Color.BLACK);
            greenTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            greenTeamPanel.add(greenTeamLabel, "North");
            
            greenTeamTable = new JTable();
    
            greenTeamModel = new DefaultTableModel(0, 2){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            greenTeamTable.setModel(greenTeamModel);
            setTableLook(greenTeamTable, Color.BLACK, Color.GREEN);
            greenTeamPanel.add(greenTeamTable, "Center");

            countdownPanel = new JPanel();
            countdownLayout = new CardLayout();
            countdownLabel = new JLabel("Press the 'S' key to start the game");
            countdownLabel.setFont(headerFont);
            countdownLabel.setForeground(Color.WHITE);
            countdownLabel.setBackground(Color.BLACK);
            countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
            countdownPanel.add(countdownLabel);
            countdownPanel.setLayout(countdownLayout);
            countdownPanel.setBackground(Color.BLACK);


            add(countdownPanel, "South");
            
        }

        void setTableLook(JTable table, Color bg, Color fg)
        {
            table.setRowHeight(headerSize*2);
            table.setFont(headerFont);
            table.setFocusable(false);
            table.setCellSelectionEnabled(false);
            table.setShowGrid(false);
            table.setBackground(bg);
            table.setForeground(fg);
            table.setFillsViewportHeight(true);
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            
            for(int j = 0; j < table.getColumnCount(); j++)
            {
                //table.getColumnModel().getColumn(j).setCellRenderer(new CustomCellRenderer(bg, fg));
                table.getColumnModel().getColumn(j).setCellRenderer( centerRenderer );
            }
        }
        
        //Custom cell renderer that allows us to change the look of the default table
        class CustomCellRenderer extends DefaultTableCellRenderer {

            Color background, foreground;

            public CustomCellRenderer(Color bg, Color fg)
            {
                super();
                background = bg;
                foreground = fg;
            }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
            {
      
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                cell.setBackground(background);
                cell.setForeground(foreground);
        
                return cell;
            }
      
        }

    }

    class GameActionScreen extends JPanel {

        //could use component listener to dynamically change font size as window is resized

        JLabel timerLabel;

        JTable redScoreboard;
        JTable greenScoreboard;

        DefaultTableModel redModel;
        DefaultTableModel greenModel;

        JTextArea actionText;

        GameActionScreen()
        {
            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(Color.BLACK);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            
            JPanel teamPanel = new JPanel();
            mainPanel.add(teamPanel);
            teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.X_AXIS));
            
            JPanel redTeamPanel = new JPanel(new BorderLayout());
            redTeamPanel.setBackground(Color.BLACK);
            teamPanel.add(redTeamPanel);
            
            JLabel redTeamLabel = new JLabel("RED TEAM");
            redTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            redTeamLabel.setFont(titleFont);
            redTeamLabel.setForeground(Color.RED);
            redTeamPanel.add(redTeamLabel, "North");
            
            redScoreboard = new JTable();
            redModel = new DefaultTableModel(0, 2)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            redScoreboard.setModel(redModel);
            setTableLook(redScoreboard, Color.BLACK, Color.WHITE);
            redTeamPanel.add(redScoreboard, "Center");
            
            JPanel greenTeamPanel = new JPanel(new BorderLayout());
            greenTeamPanel.setBackground(Color.BLACK);
            teamPanel.add(greenTeamPanel);
            
            JLabel greenTeamLabel = new JLabel("GREEN TEAM");
            greenTeamLabel.setFont(titleFont);
            greenTeamLabel.setHorizontalAlignment(SwingConstants.CENTER);
            greenTeamLabel.setForeground(Color.GREEN);
            greenTeamPanel.add(greenTeamLabel, "North");
            
            greenScoreboard = new JTable();
            greenModel = new DefaultTableModel(0, 2)
            {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            greenScoreboard.setModel(greenModel);
            setTableLook(greenScoreboard, Color.BLACK, Color.WHITE);
            greenTeamPanel.add(greenScoreboard, "Center");
            
            JPanel actionPanel = new JPanel();
            mainPanel.add(actionPanel);
            actionPanel.setLayout(new BorderLayout(0, 0));
            
            actionText = new JTextArea();
            actionText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, bodySize));
            actionText.setForeground(Color.WHITE);
            //actionText.setText("Player 1 hit Player 4\r\nPlayer 2 hit Player 3\r\nPlayer 2 hit Player 4\r\nPlayer 3 hit Player 2");
            actionText.setBackground(Color.BLUE);
            actionText.setEditable(false);
            actionPanel.add(actionText, BorderLayout.CENTER);
            
            JPanel timerPanel = new JPanel();
            FlowLayout flowLayout = (FlowLayout) timerPanel.getLayout();
            flowLayout.setHgap(10);
            flowLayout.setAlignment(FlowLayout.RIGHT);
            timerPanel.setBackground(Color.BLACK);
            mainPanel.add(timerPanel);
            
            timerLabel = new JLabel("Time Remaining: 0:37");
            timerLabel.setForeground(Color.WHITE);
            timerLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, headerSize));
            timerPanel.add(timerLabel);

            setLayout(new BorderLayout());
            add(mainPanel);
        }

        void setTableLook(JTable table, Color bg, Color fg)
        {
            table.setFont(new Font("Tahoma", Font.PLAIN, bodySize));
            table.setRowHeight(bodySize*2);
            table.setFocusable(false);
            table.setCellSelectionEnabled(false);
            table.setShowGrid(false);
            table.setBackground(bg);
            table.setForeground(fg);
            table.setFillsViewportHeight(true);
            
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            
            for(int j = 0; j < table.getColumnCount(); j++)
            {
                //table.getColumnModel().getColumn(j).setCellRenderer(new CustomCellRenderer(bg, fg));
                table.getColumnModel().getColumn(j).setCellRenderer( centerRenderer );
            }
        }

        public void addPlayersToScoreboard(List<Object[]> redPlayers, List<Object[]> greenPlayers)
        {
            for(int r = 0; r < redPlayers.size(); r++)
            {
                redModel.addRow(new Object[]{redPlayers.get(r)[1] + " (" + redPlayers.get(r)[0] + ")", 0});
            }
    
            for(int g = 0; g < greenPlayers.size(); g++)
            {
                greenModel.addRow(new Object[]{greenPlayers.get(g)[1] + " (" + greenPlayers.get(g)[0] + ")", 0});
            }            
        }

        public void setActionText(String action)
        {
            actionText.append(action);
        }
    }
    
    class ButtonPanel extends JPanel{

        String[] buttonNames = {"Edit Game", "Game Parameters", "Start Game", "Preentered Games", "View Game", "Flick Sync", "Clear Game"};
        

        ButtonPanel()
        {
            JButton[] buttons = new JButton[buttonNames.length];
            setLayout(new GridLayout(1, buttons.length));
            
            addButtons(this, buttons);
        }

        void addButtons(JPanel panel, JButton[] buttons)
        { 
            for(int i = 0; i < buttons.length; i++)
            {
                JButton button = buttons[i];
                button = new JButton(buttonNames[i]);
                //button.setLayout(new BorderLayout());
                button.setFocusPainted(false); 
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                
                
                panel.add(button);
            }
        }
    }

    

}