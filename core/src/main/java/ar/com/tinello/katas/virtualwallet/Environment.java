package ar.com.tinello.katas.virtualwallet;

public class Environment {
    public String getVariable(final String name, final String defaultValue) {
        return System.getenv(name) == null ? defaultValue : System.getenv(name);
    }

    public int getInt(final String name, final int defaultValue) {
        return System.getenv(name) == null ? defaultValue : Integer.parseInt(System.getenv(name));
    }
}
