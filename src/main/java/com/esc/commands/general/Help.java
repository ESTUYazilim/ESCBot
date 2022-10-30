package com.esc.commands.general;

import com.esc.commands.CommandContext;
import com.esc.commands.abstracts.ICommand;
import com.esc.essentials.CommandManager;
import com.esc.essentials.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.time.Instant;
import java.util.List;

public class Help implements ICommand {

    private final CommandManager manager;

    public Help(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final User user = ctx.getMember().getUser();

        String description = getDescription();

        // create an embed message
        EmbedBuilder message = new EmbedBuilder()
                .setColor(0x00ff00)
                .setTitle("List of all commands")
                .setDescription(description)
                .setFooter("By the command of " + user.getName(), user.getAvatarUrl())
                .setTimestamp(Instant.now());

        // send it to the channel
        channel.sendMessage(MessageCreateData.fromEmbeds(message.build())).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "returns the commands of ESC Bot";
    }

    @Override
    public List<String> getAliases() {
        return List.of("help");
    }

    private String getDescription() {
        String description = "";
        String prefix = Config.getVariable("pre");
        List<ICommand> commands = manager.getCommands();

        for (int i = 0; i < commands.size(); i++) {
            ICommand command = commands.get(i);
            description += prefix + command.getName() + ": " + command.getHelp() + ((i == commands.size() - 1) ? "" : "\n");
        }

        return description;
    }
}
