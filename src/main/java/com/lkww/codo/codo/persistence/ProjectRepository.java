package com.lkww.codo.codo.persistence;

import com.lkww.codo.codo.domain.Project;
import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends RedisDocumentRepository<Project, Integer> {

    Long deleteByProjectID(String id);
    Optional<Project> findByProjectID(String id);
}
