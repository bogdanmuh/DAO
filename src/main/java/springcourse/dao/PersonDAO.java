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
        people.add(new Person(PEOPLE_COUNT++,"Tom",22,"Tom@mail.ru"));
        people.add(new Person(PEOPLE_COUNT++,"Bob",54,"Bob@gmail.com"));
        people.add(new Person(PEOPLE_COUNT++,"Mike",33,"Mike@yandex.ru"));
        people.add(new Person(PEOPLE_COUNT++,"Tom",19,"Tom@mail.ru"));
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
        personToBeUpdate.setAge(updatePerson.getAge());
        personToBeUpdate.setEmail(updatePerson.getEmail());

    }
    public  void delete(int id ){
        people.removeIf(a->a.getId()==id);
    }
}
