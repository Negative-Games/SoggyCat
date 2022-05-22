package me.joehosten.bot.commands;

import me.joehosten.bot.api.command.Command;
import me.joehosten.bot.api.command.info.CommandInfo;
import me.joehosten.bot.api.command.info.CommandType;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

@CommandInfo(name = "soggycat", description = "Sends the cat", type = CommandType.DEV)
public class CommandSoggyCat extends Command {
    @Override
    public void onCommand(SlashCommandEvent event) {
        event.reply("https://pbs.twimg.com/media/FNb3SK0XsAIiDqT?format=jpg&name=medium").setEphemeral(true).queue();
    }
}
