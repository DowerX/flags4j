package flags4j;

import java.util.List;
import java.util.LinkedList;

import flags4j.resolvers.Resolver;
import flags4j.types.Flag;
import flags4j.types.Parser;

public class Manager {
    
    private static Manager  instance = new Manager();
    private Parser parser = new Parser();
    private List<Resolver> resolvers = new LinkedList<>();

    private List<Flag> flags = new LinkedList<Flag>();

    private Manager() {}
    public static Manager getInstance() { return instance; }
    
    public Parser getParser() { return this.parser; }
    public void setParser(Parser parser) { this.parser = parser; }

    public void addResolver(Resolver resolver) { this.resolvers.add(resolver); }

    public Flag addFlag(Flag flag) {
        this.flags.add(flag);
        return flag;
    }
    public List<Flag> getFlags() { return this.flags; }

    public void resolve() {
        for (Resolver resolver : this.resolvers) {
            resolver.resolve();
        }
    }

    public void help() {
        System.out.println("Help:");
        for (Resolver resolver : this.resolvers) {
            System.out.printf("    %s\n", resolver.help());
        }
        System.out.println("    Flags:");
        for (Flag flag : this.flags) {
            System.out.printf("      %s Aliases: %s\n", flag.getDescription(), String.join(", ", flag.getAliases()));
        }
    }
}
