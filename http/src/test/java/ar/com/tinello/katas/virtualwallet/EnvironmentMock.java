package ar.com.tinello.katas.virtualwallet;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentMock extends Environment {

    private Map<String, String> variables;

    public EnvironmentMock() {
        super();
        variables = new HashMap<String, String>();
    }

    @Override
    public String getVariable(String name, String defaultValue) {
        return variables.get(name) == null ? defaultValue : variables.get(name);
    }

    public void setVariable(String name, String value) {
        variables.put(name, value);
    }

    @Override
    public int getInt(String name, int defaultValue) {
        return variables.get(name) == null ? defaultValue : Integer.parseInt(variables.get(name));
    }

}
