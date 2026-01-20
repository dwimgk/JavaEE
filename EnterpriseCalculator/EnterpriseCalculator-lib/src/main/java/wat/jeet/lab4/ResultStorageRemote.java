package wat.jeet.lab4;

import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface ResultStorageRemote {
    void addResult(CalculationResult result);
    List<CalculationResult> getResults();
}
