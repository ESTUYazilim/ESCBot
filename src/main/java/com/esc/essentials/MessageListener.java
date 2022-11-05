package com.esc.essentials;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener extends ListenerAdapter {
    // logger variable to log our process
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);
    // command manager class
    private final CommandManager manager = new CommandManager();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // get raw message
        String raw = event.getMessage().getContentRaw();

        // get user
        User user = event.getAuthor();

        // if user is bot or event is weeb hook message then return
        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        // get prefix
        String prefix = Config.getVariable("pre");

        if (raw.startsWith(prefix)) {
            manager.handle(event);
        }
    }


}
