package com.esc.commands.abstracts;

import com.esc.commands.CommandContext;

import java.util.List;

public interface ICommand {

    void handle(CommandContext ctx);

    String getName();

    String getHelp();

    default List<String> getAliases() {
        return List.of();
    }
}
