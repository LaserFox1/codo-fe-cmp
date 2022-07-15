package com.lkww.codo.codo.persistence;

import com.lkww.codo.codo.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {
    public static final String HASH_KEY = "Project";
    @Autowired
    private RedisTemplate template;
    public Project save(Project project){
        template.opsForHash().put(HASH_KEY, project.getProjectID(), project);
        System.out.println("Saved " + project.JSONize());
        return project;
    }
    public Optional<Project> findProjectById(String id){
        return Optional.of((Project)template.opsForHash().get(HASH_KEY, id));
    }
    public List<Project> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }
    public long deleteProject(String id){
        return template.opsForHash().delete(HASH_KEY, id);
    }
}
