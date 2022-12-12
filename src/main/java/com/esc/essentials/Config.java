package com.esc.essentials;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Config {

    // variable that used to get dotenv file
    private static Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String getVariable(String key) {
        return dotenv.get(key.toUpperCase());
    }

    public static void updateDotEnv(String newToken, String newPrefix) {
        File envFile = new File(".env");
        // environment content which will be used to write .env file
        String envContent = "";

        if (envFile.exists()) {
            // get current token and prefix variables
            String token = getVariable("token");
            String prefix = getVariable("prefix");

            // if given new token/prefix is blank then do not change token
            // else is not blank then change token variable
            token = newToken.isBlank() ? token : newToken;
            prefix = newPrefix.isBlank() ? prefix : newPrefix;

            // change environment content
            envContent = "TOKEN=" + token
                    + "\nPRE=" + prefix;
        }
        // if there is not environment file in present
        else {
            // change environment content
            envContent = "TOKEN=" + newToken
                    + "\nPRE=" + (newPrefix.isBlank() ? "." : newPrefix);
        }

        // write .env file
        try (FileWriter writer = new FileWriter(".env")) {
            writer.write(envContent);
        } catch (IOException exception) {
            System.out.println("An error occurred during creating the .env");
        }
    }

    public static void configureDotEnv() {
        File envFile = new File(".env");
        if (!envFile.exists()) {
            try (FileWriter writer = new FileWriter(".env")) {
                writer.write("TOKEN="
                        + "\nPRE=.");
            } catch (IOException exception) {
                System.out.println("An error occurred during creating the .env");
            }
        }
        // load dotEnv one more time
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public static void configureBot() {
        // get api key
        String token = getVariable("token");

        // create jda builder
        JDABuilder.createDefault(token)
                .addEventListeners(new MessageListener())
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.listening(".help"))
                .setCompression(Compression.NONE)
                .setAutoReconnect(true)
                .setLargeThreshold(300)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.MEMBER_OVERRIDES)
                .disableIntents(GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGE_TYPING, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_INVITES)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_PRESENCES)
                .setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
                .setChunkingFilter(ChunkingFilter.NONE)
                .build();
    }


}
