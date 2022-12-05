package com.esc.commands.general;

import com.esc.commands.CommandContext;
import com.esc.commands.abstracts.ICommand;
import com.esc.essentials.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

public class Clear implements ICommand  {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final User user = ctx.getMember().getUser();

        if(ctx.getArgs().size() < 1){
            channel.sendMessage("Usage: "+ Config.getVariable("pre") +"clear <Num>").queue();
            return;

        }

        if (!isNum(ctx.getArgs().get(0))){
            channel.sendMessage("Usage: "+ Config.getVariable("pre") +"clear <Num>").queue();
            return;
        }

        int num = Integer.parseInt(ctx.getArgs().get(0));
        if(num > 0){
            List<Message> messageList =  channel.getHistory().retrievePast(num+1).complete();
            channel.purgeMessages(messageList);
            channel.sendMessage(num + " messages deleted by " + user.getName()).queue();
        }
        else{
            channel.sendMessage("The number of messages must be greater than 1 ").queue();
            channel.sendMessage("Usage: "+ Config.getVariable("pre") +"clear <Num>").queue();
        }


    }

    private boolean isNum(String msg){
        try {
            Integer.parseInt(msg);
            return true;
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
