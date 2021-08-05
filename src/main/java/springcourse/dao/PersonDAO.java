package springcourse.dao;

import org.springframework.stereotype.Component;
import springcourse.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private  static  int PEOPLE_COUNT;
    private static final String URL="jdbc:postgresql://localhost:5432/first_db";
    // лучше засунуть его в propities
    private  static  final String USERNAME ="postgres";
    private  static  final String PASSWORD="root";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }// рефлексия
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Person> index()  {

        List<Person>people= new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL="SELECT * FROM person";
            ResultSet resultSet=statement.executeQuery(SQL);//метод executeQuery возвращает resultset
            while(resultSet.next()){
                Person person= new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }
    public Person show (int id ){
        //return people.stream().filter(person ->person.getId()==id).findAny().orElse(null);
        return null;
    }
    public  void save (Person person){
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String SQL1="INSERT INTO Person Values("+1+",`"+person.getName()+"`," +
                    person.getAge()+",`"+ person.getEmail() +"`)";
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update (int id, Person updatePerson){
        /*Person personToBeUpdate= show(id);
        personToBeUpdate.setName(updatePerson.getName());
        personToBeUpdate.setAge(updatePerson.getAge());
        personToBeUpdate.setEmail(updatePerson.getEmail());*/

    }
    public  void delete(int id ){
        //people.removeIf(a->a.getId()==id);
    }
}
