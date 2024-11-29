import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisualizationData {
    private final JSONObject data;

    public VisualizationData() throws JSONException {
        this.data = new JSONObject();
        this.data.put("width", Simulation.WORLD_SIZE_X); // Add world width
        this.data.put("height", Simulation.WORLD_SIZE_Y); // Add world height
        this.data.put("steps", new JSONArray());
    }

    public void addStep(int simStep, ArrayList<Dot> dots) throws JSONException {
        JSONObject stepData = new JSONObject();
        stepData.put("simStep", simStep);

        JSONArray dotsArray = new JSONArray();
        for (Dot dot : dots) {
            JSONObject dotInfo = new JSONObject();
            dotInfo.put("id", dot.getId());
            dotInfo.put("x", dot.getPosition().x);
            dotInfo.put("y", dot.getPosition().y);
            dotsArray.put(dotInfo);
        }

        stepData.put("dots", dotsArray);
        this.data.getJSONArray("steps").put(stepData);
    }

    public void saveToFile(String fileName) {
        try {
            Files.write(Paths.get(fileName), data.toString(4).getBytes()); // Pretty-print JSON
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

