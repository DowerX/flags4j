package flags4j.resolvers;

import java.util.Map;
import java.util.Map.Entry;

import flags4j.Manager;
import flags4j.types.Flag;

public class EnvironmentResolver implements Resolver {

    public EnvironmentResolver() {}

    @Override
    public void resolve() {
        Map<String, String> env = System.getenv();
        for (Flag flag : Manager.getInstance().getFlags()){
            for (Entry<String, String> entry : env.entrySet()) {
                if (flag.isSet()) break;
                for (String alias : flag.getAliases()) {
                    if (!flag.isSet() && alias.toLowerCase().equals(entry.getKey().toLowerCase())) {
                        flag.setValue(entry.getValue());
                        break;
                    }
                }
            }  
        }
    }

    @Override
    public String help() {
        return "Environment: loads flags from environment.";
    }
    
}
