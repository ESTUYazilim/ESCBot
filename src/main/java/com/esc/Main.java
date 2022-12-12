package com.esc;

import com.esc.essentials.Config;

public class Main {


    /**
     * Main class of ESC Bot
     * @param args
     */
    public static void main(String[] args) {
        if(args.length==0){
            System.out.println("Arguments are empty.");
        }
        else{
            //This will be added prefix arguments.
        }
        Config.configureDotEnv();
        Config. configureBot();
    }
}