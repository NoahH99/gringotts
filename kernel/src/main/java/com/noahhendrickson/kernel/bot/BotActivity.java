package com.noahhendrickson.kernel.bot;

public record BotActivity(Type type, String text) {

    public static BotActivity playing(String text) {
        return new BotActivity(Type.PLAYING, text);
    }

    public static BotActivity watching(String text) {
        return new BotActivity(Type.WATCHING, text);
    }

    public static BotActivity listening(String text) {
        return new BotActivity(Type.LISTENING, text);
    }

    public static BotActivity competing(String text) {
        return new BotActivity(Type.COMPETING, text);
    }

    public enum Type {PLAYING, WATCHING, LISTENING, COMPETING}
}
