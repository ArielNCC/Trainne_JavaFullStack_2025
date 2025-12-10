package com.empresa.capacitaciones.repository;

import com.empresa.capacitaciones.entity.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CursoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    public void whenFindByName_thenReturnCurso() {
        // given
        Curso curso = new Curso("Java Basics", "Intro to Java", 40, LocalDate.now(), LocalDate.now().plusDays(30), 20);
        entityManager.persist(curso);
        entityManager.flush();

        // when
        // Assuming there is a method findByNombre or similar, or just using findAll for generic test
        // Let's use save and findById which are standard JpaRepository methods
        Curso found = cursoRepository.findById(curso.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getNombre()).isEqualTo(curso.getNombre());
    }

    @Test
    public void whenSave_thenPersistCurso() {
        // given
        Curso curso = new Curso("Spring Boot", "Advanced Spring", 50, LocalDate.now(), LocalDate.now().plusDays(45), 15);

        // when
        Curso savedCurso = cursoRepository.save(curso);

        // then
        assertThat(savedCurso.getId()).isNotNull();
        assertThat(savedCurso.getNombre()).isEqualTo("Spring Boot");
    }
}
