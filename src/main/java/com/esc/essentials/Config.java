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
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String getVariable(String key) {
        return dotenv.get(key.toUpperCase());
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
