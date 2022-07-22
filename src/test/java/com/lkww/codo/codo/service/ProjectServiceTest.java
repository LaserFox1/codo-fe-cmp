package com.lkww.codo.codo.service;

import com.lkww.codo.codo.domain.Project;
import com.lkww.codo.codo.exceptions.ServiceException;
import com.lkww.codo.codo.persistence.ProjectRepository;
import com.lkww.codo.codo.util.DemoObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository rep;
    @InjectMocks
    private ProjectService service;

    private final String notExists = "DemoID";

    private Project pr;

    @BeforeEach
    void setup() {
        rep = mock(ProjectRepository.class);
        assumeThat(rep).isNotNull();
        service = new ProjectService(rep);
        pr = DemoObjects.demoProject1();
    }

    @Test
    void ensureDatabaseErrorsGotWrappedProperly() {
        //given
        Exception pEx = new PersistenceException("connection lost");
        when(rep.save(any())).thenThrow(pEx);

        // when
        var ex = assertThrows(ServiceException.class, () -> service.create(pr));

        // then
        assertThat(ex).hasMessageContaining("Project")
                .hasMessageContaining(pr.getProjectID())
                .hasMessageContaining("database problem")
                .hasRootCause(pEx);
        verify(rep).save(any());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void ensureCreateWorksProperly() {
        when(rep.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Project project = service.create(pr);

        assertThat(project).isNotNull();
        verify(rep).save(any());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void ensureDeleteWorksProperly() {
        when(rep.deleteByProjectID(pr.getProjectID())).thenReturn(1L);
        when(rep.deleteByProjectID(notExists)).thenReturn(0L);

        assertEquals(1, service.delete(pr.getProjectID()));
        assertEquals(0, service.delete(notExists));

        verify(rep).deleteByProjectID(pr.getProjectID());
        verify(rep).deleteByProjectID(notExists);
        verifyNoMoreInteractions(rep);
    }

    @Test
    void ensureGetByIdWorksProperly() {
        when(rep.findByProjectID(pr.getProjectID())).thenReturn(Optional.of(pr));
        when(rep.findByProjectID(notExists)).thenReturn(Optional.empty());


        Optional<Project> result = service.getById(pr.getProjectID());
        assertThat(result).isPresent();
        assertEquals(pr, result.get());
        assertThat(service.getById(notExists)).isEmpty();

        verify(rep).findByProjectID(pr.getProjectID());
        verify(rep).findByProjectID(notExists);
        verifyNoMoreInteractions(rep);
    }
}
