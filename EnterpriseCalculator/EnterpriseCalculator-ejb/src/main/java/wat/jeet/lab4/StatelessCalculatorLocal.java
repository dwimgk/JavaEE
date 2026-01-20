package wat.jeet.lab4;

import jakarta.ejb.Local;

@Local
public interface StatelessCalculatorLocal {
    String performCalculations();
}
