package me.joehosten.bot.runnables;

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
            int seconds = 1800;
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new Task(channel), 0, (1000L * seconds));
        }
    }

    private static class Task extends TimerTask {

        private final TextChannel textChannel;

        public Task(TextChannel channel) {
            textChannel = channel;
        }

        @Override
        public void run() {
            textChannel.sendMessage("https://pbs.twimg.com/media/FNb3SK0XsAIiDqT?format=jpg&name=medium").queue();
            System.out.println("cat");
        }
    }

}
