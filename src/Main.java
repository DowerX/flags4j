import flags4j.Manager;
import flags4j.resolvers.ArgumentResolver;
import flags4j.resolvers.EnvironmentResolver;
import flags4j.types.Flag;

public class Main {
    public static void main(String[] args) {
        Manager flagManager = Manager.getInstance();
        
        flagManager.addResolver(new ArgumentResolver(args, "--", ":="));
        flagManager.addResolver(new EnvironmentResolver());
        
        flagManager.addFlag(new Flag<Integer>("int,INT", "an int", 0));
        flagManager.addFlag(new Flag<Boolean>("bool,BOOL", "a bool", false));
        flagManager.addFlag(new Flag<String>("USER", "a string", ""));
        
        flagManager.help();

        flagManager.resolve();
        
        for (Flag flag : flagManager.getFlags()) {
            System.out.printf("%s %s %s\n", flag.getDescription(), flag.isSet(), flag.valueToString());
        }
    }
}