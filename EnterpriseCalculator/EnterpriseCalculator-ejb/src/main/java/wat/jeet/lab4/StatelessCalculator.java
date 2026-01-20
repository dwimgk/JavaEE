package wat.jeet.lab4;

import jakarta.ejb.Stateless;

@Stateless
public class StatelessCalculator
        implements StatelessCalculatorLocal, StatelessCalculatorRemote {

    private long lastAccess = -1;

    @Override
    public String performCalculations() {
        long now = System.currentTimeMillis();

        double randomValue = Math.random();
        String last =
                (lastAccess < 0) ? "Never accessed" : String.valueOf(lastAccess);

        lastAccess = now;

        try {
            Thread.sleep(2000); // simulate long computation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "StatelessCalculator interrupted";
        }

        return "Stateless result: " + randomValue +
               ", last accessed: " + last;
    }
}
