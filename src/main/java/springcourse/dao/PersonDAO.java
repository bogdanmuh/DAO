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

        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return people;
    }
    public Person show (int id ){
        Person person=null;
        try {
            PreparedStatement preparedStatement=
                    connection.prepareStatement
                            ("SELECT *FROM Person where id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }
    public  void save (Person person){
        Statement statement ;
        try {
            /*statement = connection.createStatement();
            String SQL1="INSERT INTO Person Values("+1+",`"+person.getName()+"`," +
                    person.getAge()+",`"+ person.getEmail() +"`)";
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);*/
            // лучше использовать prepareStatment(),для недопускания SQL-инъекций
            // и для большей скорости
            PreparedStatement preparedStatement=
                    connection.prepareStatement
                            ("INSERT INTO Person Values(1,?,?,?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update (int id, Person updatePerson){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement
                    ("UPDATE Person Set name=?,age=?,email=? where id=?");
            preparedStatement.setString(1, updatePerson.getName());
            preparedStatement.setInt(2,updatePerson.getAge());
            preparedStatement.setString(3,updatePerson.getEmail());
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  void delete(int id ){
        PreparedStatement preparedStatement= null;
        try {
            preparedStatement = connection.prepareStatement
                    ("DELETE FROM Person WHERE id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
