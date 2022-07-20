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

    public JSONObject JSONize() {
        JSONObject obj = new JSONObject();
        JSONObject syn = new JSONObject();

        obj.put("scenarioName", scenarioName);

        syn.put("given", given);

        syn.put("when", when);

        syn.put("then", then);

        obj.put("syntax", syn);
        return obj;
    }

    public static Scenario fromAPI(com.lkww.codo.codo.model.Scenario s) {
        return Scenario.builder()
                .scenarioName(s.getScenarioName())
                .given(s.getGiven())
                .when(s.getWhen())
                .then(s.getThen())
                .build();
    }

    public static com.lkww.codo.codo.model.Scenario toAPI(Scenario s) {
        com.lkww.codo.codo.model.Scenario result = new com.lkww.codo.codo.model.Scenario();

        result.setScenarioName(s.getScenarioName());
        result.setGiven(s.getGiven());
        result.setWhen(s.getWhen());
        result.setThen(s.getThen());

        return result;
    }
}
