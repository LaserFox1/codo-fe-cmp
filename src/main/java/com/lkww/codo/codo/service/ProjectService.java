package com.lkww.codo.codo.service;

import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.exceptions.ServiceException;
import com.lkww.codo.codo.persistence.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository rep;

    public Project create(Project project){
        try {
            return rep.save(project);
        }
        catch (PersistenceException pEx) {
            throw ServiceException.cannotCreateEntity(project, pEx);
        } catch (Throwable t) {
            throw ServiceException.cannotCreateEntityForUndeterminedReason(project, t);
        }
    }


    public Optional<Project> getById(String ID){
        return rep.findProjectById(ID);
    }

    public List<Project> getAll(){
        return rep.findAll();
    }

    public long delete(String ID){
        return rep.deleteProject(ID);
    }

    public void deleteAll(){
        getAll().forEach(p -> rep.deleteProject(p.getProjectID()));
    }

}
