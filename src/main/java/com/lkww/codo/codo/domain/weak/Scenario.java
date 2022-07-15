package com.lkww.codo.codo.domain.weak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scenario implements Serializable {

    String scenarioName;
    String given;
    String when;
    String then;

    public JSONObject JSONize(){
        JSONObject obj = new JSONObject();
        JSONObject syn = new JSONObject();

        obj.put("Scenario", scenarioName);

        syn.put("Given", given);

        syn.put("When", when);

        syn.put("Then", then);

        obj.put("Syntax", syn);
        return obj;
    }
}
