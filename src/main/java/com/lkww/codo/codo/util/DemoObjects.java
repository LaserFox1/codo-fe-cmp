package com.lkww.codo.codo.util;

import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.domain.weak.Feature;
import com.lkww.codo.codo.domain.weak.Scenario;

import java.util.Arrays;
import java.util.List;

public class DemoObjects {

    public static Project demoProject(){
        return new Project("com.ind:codo-cmp", demoFeatureList());
    }

    public static List<Feature> demoFeatureList() {
        Scenario[] scenarios1 = {
                Scenario.builder()
                        .scenarioName("<ScenarioName1-1>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build(),
                Scenario.builder()
                        .scenarioName("<ScenarioName1-2>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build(),
                Scenario.builder()
                        .scenarioName("<ScenarioName1-3>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build()
        };
        Scenario[] scenarios2 = {
                Scenario.builder()
                        .scenarioName("<ScenarioName2-1>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build(),
                Scenario.builder()
                        .scenarioName("<ScenarioName2-2>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build(),
                Scenario.builder()
                        .scenarioName("<ScenarioName2-3>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build()
        };
        Scenario[] scenarios3 = {
                Scenario.builder()
                        .scenarioName("<ScenarioName3-1>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build(),
                Scenario.builder()
                        .scenarioName("<ScenarioName3-2>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build(),
                Scenario.builder()
                        .scenarioName("<ScenarioName3-3>")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build()
        };

        Feature[] features = {
                Feature.builder()
                        .featureName("FeatureName1")
                        .description("Sadly a demo feature for now...")
                        .scenarios(Arrays.asList(scenarios1))
                        .build(),
                Feature.builder()
                        .featureName("FeatureName2")
                        .description("Sadly a demo feature for now...")
                        .scenarios(Arrays.asList(scenarios2))
                        .build(),
                Feature.builder()
                        .featureName("FeatureName3")
                        .description("Sadly a demo feature for now...")
                        .scenarios(Arrays.asList(scenarios3))
                        .build()
        };
        return Arrays.asList(features);
    }
}
