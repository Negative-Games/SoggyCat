package me.joehosten.bot.api.command.info;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommandType {

    DEV("822346437240815656"),
    GLOBAL(null);

    // Guild ID
    private final String id;
}
