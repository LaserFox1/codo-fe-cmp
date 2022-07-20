package com.lkww.codo.codo.util;

import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.domain.weak.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoObjects {

    public static Project demoProject1() {
        return new Project("com.ind:codo-cmp", demoFeatureList(15, 15));
    }

    public static Project demoProject2() {
        return new Project("com.ind:codo-fe-cmp", demoFeatureList(15, 15));
    }

    public static List<Project> demoProjectList(int pc) {
        List<Project> p = new ArrayList<>();
        for (int i = 0; i < pc; i++)
            p.add(new Project("<ProjectID" + i + ">", demoFeatureList(15, 15)));
        return p;
    }

    public static List<Feature> demoFeatureList(int fc, int sc) {

        List<Feature> f = new ArrayList<>();
        List<Scenario> s = new ArrayList<>();
        for (int i = 0; i < fc; i++) {
            for (int j = 0; j < sc; j++) {
                s.add(Scenario.builder()
                        .scenarioName("<ScenarioName" + i + "-" + j + ">")
                        .given("<statement>")
                        .when("<statement>")
                        .then("<statement>")
                        .build());
            }
            f.add(Feature.builder()
                    .featureName("<FeatureName" + i + ">")
                    .description("Sadly a demo Feature for now...")
                    .scenarios(s)
                    .build());
            s = new ArrayList<>();
        }
        return f;

    }
}
