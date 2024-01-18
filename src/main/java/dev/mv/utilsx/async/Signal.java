package dev.mv.utilsx.async;

public class Signal {
    public void wake() {
        try {
             notifyAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
