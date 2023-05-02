package flags4j.types;

import java.util.HashSet;
import java.util.Set;

import flags4j.Manager;

public class Flag<T> {
    private final Set<String> aliases = new HashSet<>();
    private final String description;
    private T value;
    private boolean set;

    public Flag(String aliases, String description, T defaultValue) {
        Manager.getInstance().getParser().check(defaultValue);
        for (String alias : aliases.split(",")) {
            this.aliases.add(alias);
        }
        this.description = description;
        this.value = defaultValue;
    }

    public String[] getAliases() {
        String[] aliases = new String[this.aliases.size()];
        aliases = this.aliases.toArray(aliases);
        return aliases;
    }
    public String getDescription() { return this.description; }
    public T getValue() { return this.value; }
    public boolean isSet() { return this.set; }
    
    public void setValue(String input) {
        T value = (T)Manager.getInstance().getParser().parse(input, this.value);
        if (value != null) {
            this.set = true;
            this.value = value;
        }
    }

    public String valueToString() {
        return this.value.toString();
    }
}
