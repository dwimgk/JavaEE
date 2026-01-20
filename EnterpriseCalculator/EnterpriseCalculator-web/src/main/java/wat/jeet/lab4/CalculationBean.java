package wat.jeet.lab4;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

@Named("calculationBean")
@SessionScoped
public class CalculationBean implements Serializable {

    private String componentType;   // "stateful" or "stateless"
    private String interfaceType;   // "local" or "remote"
    private List<CalculationResult> results;

    // Local EJB DI (same JVM, no lookup needed)
    @EJB(beanName = "StatefulCalculator")
    private StatefulCalculatorLocal statefulLocal;

    @EJB(beanName = "StatelessCalculator")
    private StatelessCalculatorLocal statelessLocal;

    // Remote EJB DI (short format used in the guide)
    @EJB(lookup = "wat.jeet.lab4.StatefulCalculatorRemote")
    private StatefulCalculatorRemote statefulRemote;

    @EJB(lookup = "wat.jeet.lab4.StatelessCalculatorRemote")
    private StatelessCalculatorRemote statelessRemote;

    // JNDI/IIOP lookup (cast to expected type)
    private ResultStorageRemote resultStorage;

    @PostConstruct
    public void init() {
        results = new ArrayList<>();

        try {
            Hashtable<String, String> jndiProps = new Hashtable<>();
            jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.enterprise.naming.SerialInitContextFactory");
            jndiProps.put(Context.PROVIDER_URL, "iiop://127.0.0.1:3700");
            jndiProps.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");

            InitialContext ctx = new InitialContext(jndiProps);

            resultStorage = (ResultStorageRemote) ctx.lookup(
                    "java:app/EnterpriseCalculator-ejb-1.0-SNAPSHOT/ResultStorage!wat.jeet.lab4.ResultStorageRemote"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void performCalculation() {
        String userAgent = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestHeaderMap()
                .get("User-Agent");

        String result = "";
        try {
            if ("stateful".equalsIgnoreCase(componentType)) {
                if ("local".equalsIgnoreCase(interfaceType)) {
                    result = statefulLocal.performCalculations();
                } else if ("remote".equalsIgnoreCase(interfaceType)) {
                    result = statefulRemote.performCalculations();
                }
            } else if ("stateless".equalsIgnoreCase(componentType)) {
                if ("local".equalsIgnoreCase(interfaceType)) {
                    result = statelessLocal.performCalculations();
                } else if ("remote".equalsIgnoreCase(interfaceType)) {
                    result = statelessRemote.performCalculations();
                }
            }

            // create entity + store + refresh list
            CalculationResult cr = new CalculationResult(
                    componentType, interfaceType, userAgent, result
            );

            if (resultStorage != null) {
                resultStorage.addResult(cr);
                results = resultStorage.getResults();
            } else {
                // fallback, still show something in UI if lookup failed
                results.add(cr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // getters/setters
    public String getComponentType() { return componentType; }
    public void setComponentType(String componentType) { this.componentType = componentType; }

    public String getInterfaceType() { return interfaceType; }
    public void setInterfaceType(String interfaceType) { this.interfaceType = interfaceType; }

    public List<CalculationResult> getResults() { return results; }
    public void setResults(List<CalculationResult> results) { this.results = results; }
}
