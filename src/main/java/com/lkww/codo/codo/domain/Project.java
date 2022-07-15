package com.lkww.codo.codo.domain;

import com.lkww.codo.codo.domain.weak.Feature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Project")
public class Project implements Serializable {

    @Id
    String projectID;
    List<Feature> features;

    public JSONObject JSONize() {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        obj.put("Project", projectID);
        for (Feature feature : features) {
            arr.add(feature.JSONize());
        }
        obj.put("Features", arr);
        return obj;
    }
}
