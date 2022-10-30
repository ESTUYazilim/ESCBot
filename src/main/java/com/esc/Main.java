package com.esc;

import com.esc.essentials.Config;

public class Main {


    /**
     * Main class of ESC Bot
     * @param args
     */
    public static void main(String[] args) {
        Config.configureDotEnv();
        Config.configureBot();
    }
}