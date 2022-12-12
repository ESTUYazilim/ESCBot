package com.esc;

import com.esc.essentials.Config;
import com.esc.utils.ParamTypes;
import com.esc.utils.Params;
import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.Option;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    private static String token = "";
    private static String prefix = "";

    /**
     * Main class of ESC Bot
     *
     * @param args
     */
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8)); // to avoid OS dependent outputs
        Params params;
        try {
            params = CliFactory.parseArguments(Params.class, args);
        } catch (ArgumentValidationException e) {
            System.out.println(e.getMessage());
            return;
        }

        // control if there is a prefix or token argument in present if it is then assign that parameter
        if (assignParameter(params.getPrefix(), ParamTypes.Prefix) | assignParameter(params.getToken(), ParamTypes.Token)) {
            // and update .env file
            Config.updateDotEnv(token, prefix);
        }

        // configure the dotenv file
        Config.configureDotEnv();
        // and start the bog
        Config.configureBot();
    }

    /**
     * Assigns given value to the parameter
     * @param value value
     * @param type parameter type
     * @return assignment is done or not
     */
    public static boolean assignParameter(String value, ParamTypes type) {
        // if value is blank return false
        if (value.isBlank()) return false;

        // control type parameter type then assign value
        if (type == ParamTypes.Prefix) {
            prefix = value;
        } else {
            token = value;
        }

        // return true
        return true;
    }
}