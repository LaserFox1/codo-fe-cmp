package com.lkww.codo.codo.presentation.api;


import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.persistence.ProjectRepository;
import com.lkww.codo.codo.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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
public class ProjectRestController {

    public static final String BASE_URL = "/api/project";
    private static final String PATH_ID = "/{id}";
    public static final String BASE_ID = BASE_URL + PATH_ID;
    private static final String PATH_INDEX = "/";
    private static final String PATH_INDEX2 = "";

    private final ProjectService service;


    @GetMapping(PATH_ID)
    public HttpEntity<String> getProject(@PathVariable String id) {
        System.out.println("GetOne");
        var result = service.getById(id);
        return result
                .<HttpEntity<String>>map(project ->
                        ResponseEntity.ok(project.JSONize().toJSONString()))
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @GetMapping({PATH_INDEX, PATH_INDEX2})
    public HttpEntity<List<JSONObject>> getAll() {
        System.out.println("GetAll");
        var result = service.getAll()
                .stream()
                .map(Project::JSONize)
                .collect(Collectors.toList());
        return result.size() == 0 ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @PostMapping({PATH_INDEX, PATH_INDEX2})
    public HttpEntity<Project> post(@RequestBody Project project) {
        System.out.println("post");
        Project result = service.create(project);
        return result == null ? ResponseEntity.badRequest().build() : ResponseEntity.created(createSelfLink(result)).body(result);
    }

    @DeleteMapping({PATH_INDEX, PATH_INDEX2})
    public HttpEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(PATH_ID)
    public HttpEntity<Void> deleteProject(@PathVariable String id) {
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
