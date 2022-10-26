package me.joehosten.bot.runnables;

import me.joehosten.bot.core.properties.PropertiesConfiguration;
import me.joehosten.bot.core.properties.PropertiesFile;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Timer;
import java.util.TimerTask;

public class CatSendTask {

    public CatSendTask(TextChannel textChannel) {
        new TaskThread(textChannel).start();
    }

    private static class TaskThread extends Thread {

        private final TextChannel channel;

        private TaskThread(TextChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            int seconds = 86400;
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new Task(channel), 0, (1000L * seconds));
        }
    }

    private static class Task extends TimerTask {

        private final TextChannel textChannel;
        private final PropertiesConfiguration pc = new PropertiesConfiguration(new PropertiesFile(null, "bot.properties").asFile());

        public Task(TextChannel channel) {
            textChannel = channel;
        }

        @Override
        public void run() {
            textChannel.sendMessage(pc.getString("catimage")).queue();
            System.out.println("cat");
        }
    }

}
