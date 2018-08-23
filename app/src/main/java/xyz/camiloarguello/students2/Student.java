package xyz.camiloarguello.students2;

public class Student {

    private String name;
    private int age;
    private int course;
    private int group;
    private double score;
    private int image;

    public Student(String name, int age, int course, int group, double score, int image) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.group = group;
        this.score = score;
        this.image = image;
    }
    public Student(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course, int group) {
        this.course = course;
        this.group = group;
    }

    public int getGroup() {
        return group;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", course=" + course +
                ", group=" + group +
                ", score=" + score +
                '}';
    }
}
