package com.lkww.codo.codo.domain;

import com.lkww.codo.codo.domain.weak.Feature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Project")
@Builder
public class Project implements Serializable {

    @Id
    String projectID;
    List<Feature> features;

    public JSONObject JSONize() {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("projectID", projectID);
        for (Feature feature : features) {
            arr.add(feature.JSONize());
        }
        obj.put("features", arr);
        return obj;
    }

    public static Project fromAPI(com.lkww.codo.codo.model.Project p) {
        return Project.builder()
                .projectID(p.getProjectID())
                .features(p.getFeatures()
                        .stream()
                        .map(Feature::fromAPI)
                        .collect(Collectors.toList()))
                .build();
    }

    public static com.lkww.codo.codo.model.Project toAPI(Project p) {
        com.lkww.codo.codo.model.Project result = new com.lkww.codo.codo.model.Project();

        result.setProjectID(p.getProjectID());
        result.setFeatures(p.getFeatures()
                .stream()
                .map(Feature::toAPI)
                .collect(Collectors.toList()));

        return result;
    }
}
