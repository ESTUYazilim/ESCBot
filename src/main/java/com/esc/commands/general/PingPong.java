package com.esc.commands.general;

import com.esc.commands.CommandContext;
import com.esc.commands.abstracts.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.time.Instant;
import java.util.List;

public class PingPong implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final User user = ctx.getMember().getUser();

        // create an embed message
        EmbedBuilder message = new EmbedBuilder()
                .setColor(0x00ff00)
                .setTitle("Pong ping")
                .setDescription("Detailed description")
                .setFooter("Pong pinged by " + user.getName(), user.getAvatarUrl())
                .setTimestamp(Instant.now());

        // send it to the channel
        channel.sendMessage(MessageCreateData.fromEmbeds(message.build())).queue();
    }

    @Override
    public String getName() {
        return "pingpong";
    }

    @Override
    public String getHelp() {
        return "bot responds you as pong ping";
    }

    @Override
    public List<String> getAliases() {
        return List.of("ping,pong,pingpong");
    }
}
