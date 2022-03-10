package me.joehosten.bot;

import lombok.Getter;
import lombok.SneakyThrows;
import me.joehosten.bot.api.command.info.CommandType;
import me.joehosten.bot.api.command.registry.CommandRegistry;
import me.joehosten.bot.commands.CommandSoggyCat;
import me.joehosten.bot.commands.CommandSudo;
import me.joehosten.bot.core.properties.PropertiesConfiguration;
import me.joehosten.bot.core.properties.PropertiesFile;
import me.joehosten.bot.core.util.ProductionDiscord;
import me.joehosten.bot.listeners.BotLaunchListener;
import me.joehosten.bot.listeners.MemberJoinLeaveListener;
import me.joehosten.bot.runnables.CatSendTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Bot extends ListenerAdapter {
    @Getter
    private static Bot instance;
    @Getter
    private final JDA jda;
    private final CommandRegistry commandRegistry;
    @Getter
    private final PropertiesConfiguration botConfig;
    @Getter
    private final PropertiesConfiguration modulesConfig;

    @SneakyThrows
    public Bot() {
        PropertiesFile propertiesFile = new PropertiesFile(null, "bot.properties");
        propertiesFile.createFile(true);

        PropertiesFile modulesFile = new PropertiesFile(null, "modules.properties");
        modulesFile.createFile(true);
        modulesConfig = new PropertiesConfiguration(modulesFile.asFile());


        botConfig = new PropertiesConfiguration(propertiesFile.asFile());
        ProductionDiscord.init(botConfig);

        String token = botConfig.getString("token");

        instance = this;

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        builder.setActivity(Activity.watching("soggy cat"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);

        commandRegistry = new CommandRegistry();

        // Global Commands
        // Developer Commands
        commandRegistry.registerCommand(new CommandSudo());
        commandRegistry.registerCommand(new CommandSoggyCat());


        builder.addEventListeners(this);
        builder.addEventListeners(new MemberJoinLeaveListener());

        jda = builder.build().awaitReady();
        new BotLaunchListener().onLaunch(jda);

        registerDevCommands();

        new CatSendTask(jda.getTextChannelById(Long.parseLong(botConfig.getString("catchannel"))));

        CommandListUpdateAction globalCommands = jda.updateCommands();
        registerGlobalCommands(globalCommands);
        globalCommands.queue();
    }

    @SuppressWarnings("all")
    private void registerGlobalCommands(CommandListUpdateAction globalCommands) {
        commandRegistry.getCommands().stream()
                .filter(command -> command.getCommandType().equals(CommandType.GLOBAL))
                .forEach(command -> {

                    if (!command.getAliases().isEmpty()) {
                        command.getAliases().forEach(name -> {
                            CommandData commandData = new CommandData(name, command.getDescription());
                            Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                            if (!command.getSubCommands().isEmpty()) {
                                command.getSubCommands().forEach(subCommand -> {
                                    if (!subCommand.getAliases().isEmpty()) {
                                        subCommand.getAliases().forEach(subName -> {
                                            SubcommandData subcommandData = new SubcommandData(subName, subCommand.getDescription());
                                            Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                                            commandData.addSubcommands(subcommandData);
                                        });
                                    }

                                    SubcommandData subcommandData = new SubcommandData(subCommand.getName(), subCommand.getDescription());
                                    Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                                    commandData.addSubcommands(subcommandData);
                                });
                            }
                            globalCommands.addCommands(commandData);
                        });
                    }

                    CommandData commandData = new CommandData(command.getName(), command.getDescription());
                    Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                    if (!command.getSubCommands().isEmpty()) {
                        command.getSubCommands().forEach(subCommand -> {
                            if (!subCommand.getAliases().isEmpty()) {
                                subCommand.getAliases().forEach(name -> {
                                    SubcommandData subcommandData = new SubcommandData(name, subCommand.getDescription());
                                    Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                                    commandData.addSubcommands(subcommandData);
                                });
                            }

                            SubcommandData subcommandData = new SubcommandData(subCommand.getName(), subCommand.getDescription());
                            Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                            commandData.addSubcommands(subcommandData);
                        });
                    }
                    globalCommands.addCommands(commandData);

                });
    }

    @SuppressWarnings("all")
    private void registerDevCommands() {
        String id = CommandType.DEV.getId();
        Guild guildById = jda.getGuildById(id);
        assert guildById != null;

        CommandListUpdateAction commands = guildById.updateCommands();

        commandRegistry.getCommands().stream()
                .filter(command -> command.getCommandType().equals(CommandType.DEV))
                .forEach(command -> {

                    if (!command.getAliases().isEmpty()) {
                        command.getAliases().forEach(name -> {
                            CommandData commandData = new CommandData(name, command.getDescription());
                            Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                            if (!command.getSubCommands().isEmpty()) {
                                command.getSubCommands().forEach(subCommand -> {
                                    if (!subCommand.getAliases().isEmpty()) {
                                        subCommand.getAliases().forEach(subName -> {
                                            SubcommandData subcommandData = new SubcommandData(subName, subCommand.getDescription());
                                            Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                                            commandData.addSubcommands(subcommandData);
                                        });
                                    }

                                    SubcommandData subcommandData = new SubcommandData(subCommand.getName(), subCommand.getDescription());
                                    Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                                    commandData.addSubcommands(subcommandData);
                                });
                            }
                            commands.addCommands(commandData);
                        });
                    }

                    CommandData commandData = new CommandData(command.getName(), command.getDescription());
                    Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                    if (!command.getSubCommands().isEmpty()) {
                        command.getSubCommands().forEach(subCommand -> {
                            if (!subCommand.getAliases().isEmpty()) {
                                subCommand.getAliases().forEach(name -> {
                                    SubcommandData subcommandData = new SubcommandData(name, subCommand.getDescription());
                                    Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                                    commandData.addSubcommands(subcommandData);
                                });
                            }

                            SubcommandData subcommandData = new SubcommandData(subCommand.getName(), subCommand.getDescription());
                            Optional.ofNullable(subCommand.getData()).ifPresent(data -> data.accept(subcommandData));
                            commandData.addSubcommands(subcommandData);
                        });
                    }
                    commands.addCommands(commandData);

                });

        commands.queue();
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        commandRegistry.getCommands()
                .stream()
                .filter(command -> command.getName().equalsIgnoreCase(event.getName()))
                .findFirst().ifPresent(command -> command.runCommand(event));
    }

}
