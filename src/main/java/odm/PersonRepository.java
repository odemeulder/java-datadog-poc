package odm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("repository")
interface PersonRepository extends JpaRepository<Person, Long> {

}