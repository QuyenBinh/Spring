import java.util.ArrayList;
 
class Student {
    private String name;
    private int age;
    public Student(final String name, final int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student@[name=" + name + ", age=" + age + "]";
    }
}

public class ArrayListExample2 {
    public static void main(String[] args) {
        // Create listStudent
       ArrayList<Student> listStudent = new ArrayList<Student>();
        // Create students
        final Student student1 = new Student("Bac", 17);
        final Student student2 = new Student("Nam", 20);
        final Student student3 = new Student("Trung", 19);
        // Add objects to listStudent
        listStudent.add(student1);
        listStudent.add(student2);
        listStudent.add(student3);
        // Show listStudent
        for (final Student student : listStudent) {
            System.out.println(student.toString());
        }
    }
}