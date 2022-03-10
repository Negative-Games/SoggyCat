package me.joehosten.bot.commands;

import me.joehosten.bot.api.command.Command;
import me.joehosten.bot.api.command.info.CommandInfo;
import me.joehosten.bot.api.command.info.CommandType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@CommandInfo(name = "sudo", description = "Make the bot say anything on your behalf", type = CommandType.DEV)
public class CommandSudo extends Command {

    public CommandSudo() {
        setData(data -> {
            data.addOption(OptionType.CHANNEL, "channel", "Provide a channel name.", true).
                    addOption(OptionType.STRING, "content", "State what you want the bot to say!", true);
        });
    }

    @Override
    public void onCommand(SlashCommandEvent event) {
        String content = Objects.requireNonNull(event.getOption("content")).getAsString();
        MessageChannel channel = Objects.requireNonNull(event.getOption("channel")).getAsMessageChannel();
        assert channel != null;

        event.reply("Successfully sent message to " + ((TextChannel) channel).getAsMention() + "!")
                .setEphemeral(true).queue();
        channel.sendMessage(content).delay(1, TimeUnit.SECONDS).queue();
    }
}
