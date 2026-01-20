package wat.jeet.lab4;

import jakarta.ejb.Remote;

@Remote
public interface StatelessCalculatorRemote {
    String performCalculations();
}
