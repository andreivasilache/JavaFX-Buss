package main.easybussro.state;

import java.util.HashMap;

public class Context {
    private HashMap<String, Object> states;

    public Context() {
        states = new HashMap<>();
    }

    public void putItem(String key, Object item) {
        states.put(key, item);
    }
    public Object getState(String stateName) {
        Object item = states.get(stateName);
        return item;
    }

    public void removeState(String stateName) {
        states.remove(stateName);
    }

    public void emptyState() {
        states.clear();
    }
}