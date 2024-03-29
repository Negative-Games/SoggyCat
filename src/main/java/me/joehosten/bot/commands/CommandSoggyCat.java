package me.joehosten.bot.commands;

import me.joehosten.bot.api.command.Command;
import me.joehosten.bot.api.command.info.CommandInfo;
import me.joehosten.bot.api.command.info.CommandType;
import me.joehosten.bot.core.properties.PropertiesConfiguration;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

@CommandInfo(name = "soggycat", description = "Sends the cat", type = CommandType.DEV)
public class CommandSoggyCat extends Command {

    private final PropertiesConfiguration pc;

    public CommandSoggyCat(PropertiesConfiguration pc) {
        this.pc = pc;
    }

    @Override
    public void onCommand(SlashCommandEvent event) {
        event.reply(pc.getString("catimage")).setEphemeral(true).queue();
    }
}
