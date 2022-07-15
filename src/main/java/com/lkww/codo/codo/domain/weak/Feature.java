package com.lkww.codo.codo.domain.weak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feature implements Serializable {

    String featureName;
    String description;
    List<Scenario> scenarios;

    public JSONObject JSONize(){
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("Feature", featureName);
        obj.put("Description", description);
        for(Scenario scenario : scenarios){
            arr.add(scenario.JSONize());
        }
        obj.put("Scenarios", arr);
        return obj;
    }
}
