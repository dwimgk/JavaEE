package wat.jeet.lab4;

import jakarta.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ResultStorage implements ResultStorageRemote {

    private final List<CalculationResult> results = new ArrayList<>();

    @Override
    public void addResult(CalculationResult result) {
        results.add(result);
    }

    @Override
    public List<CalculationResult> getResults() {
        return new ArrayList<>(results);
    }
}
