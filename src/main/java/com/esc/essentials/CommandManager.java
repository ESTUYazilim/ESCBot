package com.esc.essentials;

import com.esc.commands.CommandContext;
import com.esc.commands.abstracts.ICommand;
import com.esc.commands.general.Clear;
import com.esc.commands.general.Help;
import com.esc.commands.general.PingPong;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new Help(this));
        addCommand(new PingPong());
        addCommand(new Clear());
    }

    private void addCommand(ICommand command) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(command.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present.");
        }

        commands.add(command);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }
        return null;
    }

    public void handle(MessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw().replaceFirst(Config.getVariable("pre"), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();

        ICommand command = this.getCommand(invoke);
        if (command != null) {
            List<String> args = List.of(split).subList(1, split.length);
            CommandContext ctx = new CommandContext(event, args);
            command.handle(ctx);
        }
    }
}
