package dev.mv.utilsx;

import org.jetbrains.annotations.NotNull;

public enum Infallible {
    ;
    public static @NotNull Infallible exit(int status) {
        System.exit(status);
        return Infallible.loop();
    }

    public static @NotNull Infallible panic() {
        System.err.println("Program panicked");
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stack.length; i++) {
            System.err.println("\tat " + stack[i].toString());
        }
        System.exit(1);
        return Infallible.loop();
    }
    public static @NotNull Infallible panic(Throwable throwable) {
        System.err.println("Program panicked:");
        throwable.printStackTrace(System.err);
        System.exit(1);
        return Infallible.loop();
    }

    public static @NotNull <T> Infallible panic(T message) {
        System.err.println("Program panicked: \"" + message.toString() + "\"");
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stack.length; i++) {
            System.err.println("\tat " + stack[i].toString());
        }
        System.exit(1);
        return Infallible.loop();
    }


    public static @NotNull Infallible loop() {
        for (;;) {}
    }

}
