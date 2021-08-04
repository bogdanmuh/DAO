package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.model.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private  static  int PEOPLE_COUNT;
    private List<Person> people;

    //блок иницилизации
    {
        people= new ArrayList<>();
        people.add(new Person(PEOPLE_COUNT++,"Tom"));
        people.add(new Person(PEOPLE_COUNT++,"Bob"));
        people.add(new Person(PEOPLE_COUNT++,"Mike"));
        people.add(new Person(PEOPLE_COUNT++,"Tom"));
    }
    public List<Person> index(){
        return people;
    }
    public Person show (int id ){
        return people.stream().filter(person ->person.getId()==id).findAny().orElse(null);
    }
    public  void save (Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
    public void update (int id, Person updatePerson){
        Person personToBeUpdate= show(id);
        personToBeUpdate.setName(updatePerson.getName());
    }
    public  void delete(int id ){
        people.removeIf(a->a.getId()==id);
    }
}
