package flags4j.types;

import java.util.HashMap;

public class Parser {

    public enum Types {
        INTEGER,
        FLOAT,
        DOUBLE,
        BOOLEAN,
        STRING
    }

    private static HashMap<Class, Types> allowedTypes = new HashMap<>();

    public Parser(){
        allowedTypes.put(Integer.class, Types.INTEGER);
        allowedTypes.put(Float.class, Types.FLOAT);
        allowedTypes.put(Double.class, Types.DOUBLE);
        allowedTypes.put(Boolean.class, Types.BOOLEAN);
        allowedTypes.put(String.class, Types.STRING);
    }

    public boolean check(Object object) {
        return allowedTypes.keySet().contains(object.getClass());
    }

    public Object parse(String input, Object type) {
        if(!this.check(type)) throw new IllegalArgumentException("The parser can't handle the following type: "+type.getClass().getName()+".");
        try {
            switch(allowedTypes.get(type.getClass())) {
                case INTEGER:
                    return Integer.parseInt(input);
                case FLOAT:
                    return Float.parseFloat(input);
                case DOUBLE:
                    return Double.parseDouble(input);
                case BOOLEAN:
                    return Boolean.parseBoolean(input);
                case STRING:
                    return input;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
