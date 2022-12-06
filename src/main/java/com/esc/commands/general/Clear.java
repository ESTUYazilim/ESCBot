package com.esc.commands.general;

import com.esc.commands.CommandContext;
import com.esc.commands.abstracts.ICommand;
import com.esc.essentials.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.time.Instant;
import java.util.List;

public class Clear implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        // get channel, member and user
        final TextChannel channel = ctx.getChannel();
        final Member member = ctx.getMember();
        final User user = member.getUser();

        // control permissions of member
        if (!member.hasPermission(Permission.MESSAGE_MANAGE)) {
            // create error message
            EmbedBuilder message = new EmbedBuilder()
                    .setColor(0x00ff00)
                    .setTitle("Unauthorized usage!")
                    .setDescription("You must have permission to manage messages in current channel to use clear command!")
                    .setFooter("By the command of " + user.getName(), user.getAvatarUrl())
                    .setTimestamp(Instant.now());

            // send message
            channel.sendMessage(MessageCreateData.fromEmbeds(message.build())).queue();
            return;
        }

        // control whether the command is invoked in right way
        if (ctx.getArgs().size() < 1 || !isAppropriate(ctx.getArgs().get(0))) {
            // create error message
            EmbedBuilder message = new EmbedBuilder()
                    .setColor(0x00ff00)
                    .setTitle("Wrong usage!")
                    .setDescription("Please use clear command: " + Config.getVariable("pre") + "clear <number of messages to be cleared>")
                    .setFooter("By the command of " + user.getName(), user.getAvatarUrl())
                    .setTimestamp(Instant.now());

            // send message
            channel.sendMessage(MessageCreateData.fromEmbeds(message.build())).queue();
            return;
        }

        // get number
        int num = Integer.parseInt(ctx.getArgs().get(0));

        // get # messages
        List<Message> messageList = channel.getHistory().retrievePast(num + 1).complete();

        // purge messages
        channel.purgeMessages(messageList);

        // create an embed message
        EmbedBuilder message = new EmbedBuilder()
                .setColor(0x00ff00)
                .setTitle("Messages cleared")
                .setDescription(num + " message" + (num == 1 ? " has" : "s have") + " been deleted")
                .setFooter("By the command of " + user.getName(), user.getAvatarUrl())
                .setTimestamp(Instant.now());

        // send it to the channel
        channel.sendMessage(MessageCreateData.fromEmbeds(message.build())).queue();
    }

    /**
     * Controls the given arg is appropriate - on other words an integer > 0
     * @param arg argument
     * @return true or false
     */
    private boolean isAppropriate(String arg) {
        try {
            int num = Integer.parseInt(arg);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "this command is to delete multiple messages";
    }
}
