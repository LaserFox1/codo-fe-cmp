package com.lkww.codo.codo.domain.weak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feature implements Serializable {

    String featureName;
    String description;
    List<Scenario> scenarios;

    public JSONObject JSONize() {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("featureName", featureName);
        obj.put("description", description);
        for (Scenario scenario : scenarios) {
            arr.add(scenario.JSONize());
        }
        obj.put("scenarios", arr);
        return obj;
    }

    public static Feature fromAPI(com.lkww.codo.codo.model.Feature f) {
        return Feature.builder()
                .featureName(f.getFeatureName())
                .description(f.getDescription())
                .scenarios(f.getScenarios()
                        .stream()
                        .map(Scenario::fromAPI)
                        .collect(Collectors.toList()))
                .build();
    }

    public static com.lkww.codo.codo.model.Feature toAPI(Feature f) {
        com.lkww.codo.codo.model.Feature result = new com.lkww.codo.codo.model.Feature();

        result.setFeatureName(f.getFeatureName());
        result.setDescription(f.getDescription());

        result.setScenarios(f.getScenarios()
                        .stream()
                        .map(Scenario::toAPI)
                        .collect(Collectors.toList()));
        return result;
    }
}
