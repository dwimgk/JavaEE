package wat.jeet.lab4;

import jakarta.ejb.Remote;

@Remote
public interface StatefulCalculatorRemote {
    String performCalculations();
}
