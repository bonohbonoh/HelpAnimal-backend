package com.jeonggolee.helpanimal.domain.recruitment.repository;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class AnimalRepositoryTest {
    @Autowired
    private AnimalRepository animalRepository;

    private static final String name = "개";

    private Animal initEntity() {
        return Animal.builder()
                .name(name)
                .build();
    }

    @Test
    @DisplayName("동물 등록")
    void 동물등록() {
        //given
        Animal animal = initEntity();

        //when
        Long id = animalRepository.save(animal).getId();

        //then
        assertThat(animalRepository.findById(id).isPresent()).isTrue();
    }

    @Test
    @DisplayName("동물 ID로 조회")
    void 동물ID로_조회() {
        //given
        Animal animal = initEntity();
        Long id = animalRepository.save(animal).getId();

        //when
        Optional<Animal> findAnimal = animalRepository.findById(id);

        //then
        assertThat(findAnimal.isPresent()).isTrue();
    }

    @Test
    @DisplayName("동물 ID로 조회 실패")
    void 동물ID로_조회_실패() {
        //given
        Long id = 1000L;

        //when
        Optional<Animal> animal = animalRepository.findById(id);

        //then
        assertThat(animal.isPresent()).isFalse();
    }

    @Test
    @DisplayName("동물명 조회")
    void 동물명으로_조회() {
        //given
        Animal animal = initEntity();
        animalRepository.save(animal);

        //when
        Optional<Animal> findAnimal = animalRepository.findByName(name);

        //then
        assertThat(findAnimal.isPresent()).isTrue();
    }

    @Test
    @DisplayName("동물명으로 조회 실패")
    void 동물명으로_조회_실패() {
        //given

        //when
        Optional<Animal> animal = animalRepository.findByName("테스트실패");

        //then
        assertThat(animal.isPresent()).isFalse();
    }

    @Test
    @DisplayName("동물 수정")
    void 동물수정() {
        //given
        Animal animal = initEntity();
        animalRepository.save(animal);

        //when
        animal.updateAnimalName("수정");
        Animal updateAnimal = animalRepository.save(animal);

        //then
        assertThat(updateAnimal.getName()).isEqualTo(animal.getName());
    }

    @Test
    @DisplayName("동물 삭제")
    void 동물삭제() {
        //given
        Animal animal = initEntity();
        Long id = animalRepository.save(animal).getId();

        //when
        animalRepository.delete(animal);

        //then
        Optional<Animal> deleteAnimal = animalRepository.findById(id);
        assertThat(deleteAnimal.isPresent()).isFalse();
    }
}
