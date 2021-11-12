package com.team7;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/*

javac -cp ".;com/resources/postgresql-42.2.24.jar" -d . com/team7/*.java
java -splash:com/img/Photon.jpg -cp ".;com/resources/postgresql-42.2.24.jar" com/team7/App

*/


public class App {

    long splashDuration = 3;	
    
    GUI g;
    Database data;
    EventHandler ev;
    
    public App()
    {
        final SplashScreen splash = SplashScreen.getSplashScreen();
        
        if (splash == null)
        {
            System.out.println("SplashScreen.getSplashScreen() returned null");
        }
        else
        {
            Graphics2D graphics = splash.createGraphics();
        
            if (graphics == null) 
            {
                System.out.println("g is null");
            }
            else
            {
                for(int i=0; i<100; i++) 
                {
                    splash.update();

                    try 
                    {
                        Thread.sleep(splashDuration*10);
                    }
                    catch(InterruptedException e) 
                    {

                    }
                }
                
                splash.close();
            }
        }
        
        data = new Database();
        
        g = new GUI();
        ev = new EventHandler(g, data);
    }

    public static void main(String[] args) {
        App app = new App();
    }
}
