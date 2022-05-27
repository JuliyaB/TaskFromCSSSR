package pojo;

import java.time.LocalDate;

public class SuperheroGenerator {

    public static Superhero getSimpleSuperhero(String fullName, String city,
                                               String mainSkill, String gender) {
        return Superhero.builder()
                .fullName(fullName)
                .birthDate(LocalDate.now())
                .city(city)
                .mainSkill(mainSkill)
                .gender(gender)
                .build();
    }

    public static Superhero getSimpleSuperhero(String city) {
        return Superhero.builder()
                .fullName("Моряк")
                .birthDate(LocalDate.now())
                .city(city)
                .mainSkill("Magic")
                .gender("F")
                .build();
    }

    public static Superhero getSimpleSuperhero() {
        return Superhero.builder()
                .fullName("Моряк")
                .birthDate(LocalDate.now())
                .city("Анапа")
                .mainSkill("Magic")
                .gender("F")
                .build();
    }
}
