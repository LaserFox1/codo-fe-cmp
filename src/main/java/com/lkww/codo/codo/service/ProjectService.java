package com.lkww.codo.codo.service;

import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.exceptions.ServiceException;
import com.lkww.codo.codo.persistence.ProjectRepository;
import com.lkww.codo.codo.util.DemoObjects;
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
            //for(Project p : DemoObjects.demoProjectList(15))
             //   rep.save(p);
            //return project;
        }
        catch (PersistenceException pEx) {
            throw ServiceException.cannotCreateEntity(project, pEx);
        } catch (Throwable t) {
            throw ServiceException.cannotCreateEntityForUndeterminedReason(project, t);
        }
    }


    public Optional<Project> getById(String ID){
        return rep.findByProjectID(ID);
    }

    public List<Project> getAll(){
        return (List<Project>) rep.findAll();
    }

    public long delete(String ID){
        return rep.deleteByProjectID(ID);
    }

    public void deleteAll(){
        rep.deleteAll();
    }

}
