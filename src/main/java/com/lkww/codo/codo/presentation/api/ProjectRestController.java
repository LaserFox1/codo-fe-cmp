package com.lkww.codo.codo.presentation.api;


import com.lkww.codo.codo.api.ProjectApi;
import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(ProjectRestController.BASE_URL)
public class ProjectRestController implements ProjectApi {

    public static final String BASE_URL = "/api/project";
    private static final String PATH_ID = "/{id}";
    public static final String BASE_ID = BASE_URL + PATH_ID;
    private static final String PATH_INDEX = "/";
    private static final String PATH_INDEX2 = "";

    private final ProjectService service;


    @GetMapping(PATH_ID)
    @Override
    public ResponseEntity<com.lkww.codo.codo.model.Project> projectIDGet(@PathVariable String id) {
        System.out.println("GetOne");
        var result = service.getById(id);
        return result.map(project -> ResponseEntity.ok(Project
                .toAPI(project))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping({PATH_INDEX, PATH_INDEX2})
    public ResponseEntity<List<JSONObject>> getAll() {
        System.out.println("GetAll");
        var result = service.getAll()
                .stream()
                .map(Project::JSONize)
                .collect(Collectors.toList());
        return result.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @PostMapping({PATH_INDEX, PATH_INDEX2})
    @Override
    public ResponseEntity<com.lkww.codo.codo.model.Project> projectPost(@RequestBody com.lkww.codo.codo.model.Project project) {
        System.out.println("post");
        Project result = service.create(Project.fromAPI(project));
        return result == null ? ResponseEntity.badRequest().build() : ResponseEntity.created(createSelfLink(result)).body(Project.toAPI(result));
    }

    @DeleteMapping({PATH_INDEX, PATH_INDEX2})
    public ResponseEntity<Void> projectDelete() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(PATH_ID)
    public HttpEntity<Void> projectIDDelete(@PathVariable String id) {
        switch ((int)service.delete(id)){
            case 0:
                return ResponseEntity.notFound().build();
            case 1:
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    private URI createSelfLink(Project body) {
        return UriComponentsBuilder
                .fromPath(BASE_ID)
                .uriVariables(Map.of("id", body.getProjectID()))
                .build().toUri();
    }
}
