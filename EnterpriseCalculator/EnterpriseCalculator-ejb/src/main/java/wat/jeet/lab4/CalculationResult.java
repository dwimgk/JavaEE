package wat.jeet.lab4;

import java.io.Serializable;

/**
 * Data structure for storing individual calculation results
 */
public class CalculationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int counter = 0; // for identifying purposes

    private final int id;
    private final String componentType;   // Stateful / Stateless
    private final String interfaceType;   // Local / Remote
    private final String userAgent;        // Browser info
    private final String result;

    public CalculationResult(String componentType,
                             String interfaceType,
                             String userAgent,
                             String result) {
        this.id = ++counter;
        this.componentType = componentType;
        this.interfaceType = interfaceType;
        this.userAgent = userAgent;
        this.result = result;
    }

    // ===== Getters (required for JSF data table) =====

    public int getId() {
        return id;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getResult() {
        return result;
    }
}
