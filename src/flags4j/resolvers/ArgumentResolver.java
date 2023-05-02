package flags4j.resolvers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import flags4j.Manager;
import flags4j.types.Flag;

public class ArgumentResolver implements Resolver {

    private final String[] args;
    private final String prefix;
    private final String separators;

    public ArgumentResolver(String[] args, String prefix, String separators) {
        this.args = args;
        this.prefix = prefix;
        this.separators = separators;
    }

    @Override
    public void resolve() {
        for (Flag flag : Manager.getInstance().getFlags()) {
            for (String alias : flag.getAliases()) {
                if (flag.isSet()) break;
                Pattern pattern = Pattern.compile(String.format("^%s%s([%s].+)?$", this.prefix, alias, this.separators));
                for (String input : args) {
                    Matcher matcher = pattern.matcher(input);
                    if (!flag.isSet() && matcher.find()) {
                        String[] parts = input.split(String.format("[%s]", this.separators));
                        flag.setValue((parts[Math.min(1, parts.length-1)]));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String help() {
        return String.format("Arguments: %salias[%s]value", this.prefix, this.separators);
    }
}
