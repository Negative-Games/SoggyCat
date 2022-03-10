package me.joehosten.bot.api.command.registry;

import lombok.Getter;
import lombok.NonNull;
import me.joehosten.bot.api.command.Command;

import java.util.ArrayList;

public class CommandRegistry {

    @Getter
    private final ArrayList<Command> commands = new ArrayList<>();

    public void registerCommand(@NonNull Command command) {
        commands.add(command);
    }
}
