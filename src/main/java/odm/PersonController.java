package odm;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

@RestController
public class PersonController {
    
    private PersonRepository repository;
    private static final StatsDClient statsd = new NonBlockingStatsDClient(
      "odm.jm",                          /* prefix to any stats; may be null or empty string */
      "localhost",                        /* common case: localhost */
      8125,                                 /* port */
      new String[] {"tag:value"}            /* DataDog extension: Constant tags, always applied */
    );

    PersonController(PersonRepository repo) {
      this.repository = repo;
    }

    @GetMapping("/persons")
    List<Person> all() {
      return repository.findAll();
    }

    @GetMapping("/persons/{id}")
    public Person getPerson(@PathVariable long id) {
        return findPersonById(id);
    }
 
    @PostMapping("/persons")
    Person newPerson(@RequestBody Person newPerson) {
      statsd.increment("person.new");
      Person retVal = repository.save(newPerson);
      long population = this.repository.count();
      statsd.gauge("person.population", population);
      return retVal;
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable long id) {
      statsd.increment("person.delete");
      repository.delete(findPersonById(id));
      statsd.gauge("person.population", this.repository.count());
    }

    private Person findPersonById(long id)  {
        return this.repository.findById(id)
          .orElseThrow(() -> new PersonNotFoundException(id));
    }
}