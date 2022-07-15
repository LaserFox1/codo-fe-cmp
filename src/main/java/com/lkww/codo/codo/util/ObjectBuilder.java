package com.lkww.codo.codo.util;


import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.domain.weak.Feature;
import com.lkww.codo.codo.domain.weak.Scenario;
import com.lkww.codo.codo.exceptions.CrawlerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.lkww.codo.codo.util.InputHandler.concat;
import static com.lkww.codo.codo.util.InputHandler.tokenize;


public class ObjectBuilder {

    public static List<Project> builder(InputStream stream) {

        BufferedReader in = new BufferedReader(new InputStreamReader(stream));

        boolean isDescription = false;


        List<Scenario> scenarios = new ArrayList<>();
        List<Feature> features = new ArrayList<>();
        List<Project> projects = new ArrayList<>();

        try {
            Scenario scenario = null;
            Feature feature = null;
            Project project = null;

            StringBuilder description = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty() || line.isBlank())
                    continue;

                List<String> tokens = tokenize(line);
                String keyword = tokens.get(0);


                switch (keyword) {
                    case "Project:":
                        project = new Project();
                        project.setProjectID(concat(line, "Project:"));
                        break;
                    case "Feature:":
                        feature = new Feature();
                        feature.setFeatureName(concat(line, "Feature:"));
                        isDescription = true;
                        break;
                    case "Scenario:":
                        isDescription = false;
                        scenario = new Scenario();
                        scenario.setScenarioName(concat(line, "Scenario"));
                        break;

                    case "Given":
                        assert scenario != null;
                        scenario.setGiven(concat(line));
                        break;
                    case "When":
                        assert scenario != null;
                        scenario.setWhen(concat(line));
                        break;
                    case "Then":
                        assert scenario != null;
                        scenario.setThen(concat(line));
                        break;


                    case "ProjectDone":
                        assert project != null;
                        project.setFeatures(features);
                        projects.add(project);
                        features = new ArrayList<>();
                        break;
                    case "FeatureDone":
                        assert feature != null;
                        feature.setScenarios(scenarios);
                        feature.setDescription(description.toString());
                        features.add(feature);
                        scenarios = new ArrayList<>();
                        description = new StringBuilder();
                        break;
                    case "ScenarioDone":
                        scenarios.add(scenario);
                        break;


                    case "DemoGaming":
                        features = DemoObjects.demoFeatureList();
                        break;

                }

                if (!keyword.equals("Feature:") && isDescription) {
                    description.append(concat(line));
                }
            }
            in.close();
        } catch (IOException ioE) {
            throw CrawlerException.readIO(ioE);
        }
        catch (Throwable t){
            throw CrawlerException.readUndetermined(t);
        }

        return projects;
    }
}
