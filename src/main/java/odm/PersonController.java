package odm;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RestController
public class PersonController {
    
    private PersonRepository repository;

    @Autowired
    private MetricsInterface metrics;
    
    PersonController(PersonRepository repo, MetricsInterface m) {
      this.repository = repo;
      this.metrics = m;
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
      this.metrics.increment("person.new");
      Person retVal = repository.save(newPerson);
      long population = this.repository.count();
      this.metrics.gauge("person.population", population);
      return retVal;
    }

    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable long id) {
      this.metrics.increment("person.delete");
      repository.delete(findPersonById(id));
      this.metrics.gauge("person.population", this.repository.count());
    }

    private Person findPersonById(long id)  {
        return this.repository.findById(id)
          .orElseThrow(() -> new PersonNotFoundException(id));
    }
}