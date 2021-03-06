package xyz.camiloarguello.students2;

import android.arch.core.util.Function;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    // This variable defines the user or student for Method finishedEditStudent from 0 to length of students
    private int indexOfStudent = 0;

    // These variables define if editing a specific data of the each student. Are basically to know which is the data to modify.
    // Only 1 can be true at time, the method whichField handle it
    private boolean isName = false;
    private boolean isAge = false;
    private boolean isCourse = false;
    private boolean isScore = false;

    // This is the data.
    // Is a Student class with data passed over the constructor
    Student camilo = new Student("Camilo Arguello",25,10,1,4.0, R.drawable.camilo);
    Student juliana = new Student("Juliana Ruiz",24,10,1,4.5, R.drawable.juliana);
    Student victoria = new Student("Victoria Cortes",25,10,1,3.5, R.drawable.robot);
    Student blanca = new Student("Blanca Rincon",44,11,2,5.0, R.drawable.robot);
    Student sophia = new Student("Sophia Arguello",1,11,2,5.0, R.drawable.robot);
    Student angela = new Student("Angela Arguello",13,11,2,5.0, R.drawable.robot);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the blue container under the screen
        LinearLayout linearLayout = findViewById(R.id.detailStudent);
        linearLayout.setTranslationY(1000f);

        // Set the middle blue container with scale 0
        LinearLayout editingStudent = findViewById(R.id.editingStudentFieldLayout);
        editingStudent.setScaleX(0f);
        editingStudent.setScaleY(0f);

    }

    /**
     * Only trigger when User tap over the big grid of images.
     * Each image open a different user.
     * @param view
     */
    public void showStudent(View view){
        // Showing button image
        ImageButton closeButton = findViewById(R.id.btnCloseDetailStudent);
        closeButton.animate().rotation(360f).setDuration(500);
        closeButton.setVisibility(View.VISIBLE);

        // Getting data depending view tapped
        whichStudent(view);

        // Showing layout
        LinearLayout linearLayout = findViewById(R.id.detailStudent);
        linearLayout.animate().translationY(0f).setDuration(500);
        linearLayout.setVisibility(View.VISIBLE);

    }

    /**
     * This method decide which of the Students has been clicked or tapped
     * @param view
     */
    private void whichStudent(View view){
        // Based on the view, catch the tag from xml and compare it with some values
        // I can't just put view.getTag(), due each case needs a CONSTANT even when I put 0, my solution was change it for String
        switch(view.getTag().toString()){
            case "0":
                showDataStudent(camilo);
                indexOfStudent = 0;
                break;
            case "1":
                showDataStudent(juliana);
                indexOfStudent = 1;
                break;
            case "2":
                showDataStudent(victoria);
                indexOfStudent = 2;
                break;
            case "3":
                showDataStudent(blanca);
                indexOfStudent = 3;
                break;
            case "4":
                showDataStudent(sophia);
                indexOfStudent = 4;
                break;
            default:
                showDataStudent(angela);
                indexOfStudent = 5;
        }
    }

    /**
     * This method only has the job of render the data on the screen, just with the Student passed as argument
     * The data rendered is located inside of blue bottom LinearLayout
     * @param student
     *
     */
    private void showDataStudent(Student student){

        ImageView image = findViewById(R.id.imgDetailStudent);
        image.setImageResource(student.getImage());

        TextView name = findViewById(R.id.txtName);
        name.setText(student.getName());

        TextView age = findViewById(R.id.txtAge);
        age.setText(String.valueOf(student.getAge()));

        TextView course = findViewById(R.id.txtCourse);
        course.setText(String.valueOf(student.getCourse()) + " - " + String.valueOf(student.getGroup()));

        TextView score = findViewById(R.id.txtScore);
        score.setText(String.valueOf(student.getScore()));
    }

    /**
     * When user tap over the "X" ImageButton
     * This method occurs
     * Just become invisible and translate outside of the screen
     * @param view
     */
    public void closeStudent(View view){
        LinearLayout linearLayout = findViewById(R.id.detailStudent);
        ImageButton closeButton = findViewById(R.id.btnCloseDetailStudent);

        linearLayout.animate().translationY(1000f).setDuration(500);
        closeButton.setVisibility(View.INVISIBLE);
    }

    /**
     * Trigger when user tap on each "Editar" TextView
     * @param view
     */
    public void editField(View view){
        whichField(view); // set the variable which gonna be edited
        launchFieldEditor(view); // launch blue layout over the screen
    }

    /**
     * Get the view and using a third function, to set only ONE variable to true for each iteration
     * If was tapped the TextView with tag "e0" it calls the handleFieldsVariables method and only tells it that name is true
     * @param view
     */
    private void whichField(View view){
        switch (view.getTag().toString()){
            case "e0":
                handleFieldsVariables(true,false,false,false);
                break;
            case "e1":
                handleFieldsVariables(false,true,false,false);
                break;
            case "e2":
                handleFieldsVariables(false,false,true,false);
                break;
            default:
                handleFieldsVariables(false,false,false,true);

        }

    }

    /**
     * Only ONE should be true at time
     * This method is only to assign variables passed as argument to a global variables
     * @param name
     * @param age
     * @param course
     * @param score
     */
    private void handleFieldsVariables(boolean name,boolean age, boolean course, boolean score){
        isName = name;
        isAge = age;
        isCourse = course;
        isScore = score;
    }

    /**
     * This method is initialized since user tap over "Editar" Text View using EditField method as father
     * Here I launch the blue middle layout with the description and the EditText and button as well
     * It recieves a view as an actually is a TextView with a Tag
     * @param view
     */
    private void launchFieldEditor(View view){
        LinearLayout layoutEditing = findViewById(R.id.editingStudentFieldLayout);
        EditText dataToChange = findViewById(R.id.fieldEditStudent);

        // Find the TextView with tag "e0" and "e2" (name,course)
        // Change data input field to a text
        // The difference is the hint value for each one
        // All the others can be only numbers (age,score)
        if(view.getTag().toString().equals("e0")){
            dataToChange.setInputType(InputType.TYPE_CLASS_TEXT);
            dataToChange.setHint("Nombre");

        }else if(view.getTag().toString().equals("e2")){
            dataToChange.setInputType(InputType.TYPE_CLASS_TEXT);
            dataToChange.setHint("Curso - grupo");
        }else{
            dataToChange.setInputType(InputType.TYPE_CLASS_NUMBER);
            dataToChange.setHint("Número");
        }

        // Scale the layout from 0 to 1
        layoutEditing.animate().scaleX(1).scaleY(1).setDuration(300);
        layoutEditing.setVisibility(View.VISIBLE);

    }

    /**
     * When the user tap on "Enviar" button this method starts
     * First call the layout to scale to 0 a so on
     * the important part is testing if the user has typing something
     * Look the "!" before the if. I am negating that the String is empty... in english'd be: If the textfield isn't empty
     * The variable indexOfStudent classifies the Student 0 for Student1, 1 for student2, 2 for student3, ...
     *
     * @param view
     */
    public void finishEditStudent(View view){
        LinearLayout layoutEditing = findViewById(R.id.editingStudentFieldLayout);
        layoutEditing.animate().scaleX(0).scaleY(0).setDuration(300);
        EditText fieldChanged = findViewById(R.id.fieldEditStudent);

        if(!fieldChanged.getText().toString().isEmpty()){
            switch (indexOfStudent){
                case 0: whichStudentHasBeenChanged(camilo,fieldChanged); break;
                case 1: whichStudentHasBeenChanged(juliana,fieldChanged); break;
                case 2: whichStudentHasBeenChanged(victoria,fieldChanged); break;
                case 3: whichStudentHasBeenChanged(blanca,fieldChanged); break;
                case 4: whichStudentHasBeenChanged(sophia,fieldChanged); break;
                default: whichStudentHasBeenChanged(angela,fieldChanged); break;
            }
        } else{
            Toast.makeText(getApplicationContext(), "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @param student Who has been changed?
     * @param editText What was the change?
     *
     * Inside I tell the app: Where should be shown to the user?
     */
    private void whichStudentHasBeenChanged(Student student, EditText editText){
        // if name was changed so..
        if(isName){
            editName(student, editText.getText().toString());
        }else if(isAge){
            editAge(student, Integer.parseInt(editText.getText().toString()));
        }else if(isCourse){
            editCourse(student, editText.getText().toString());
        }else{
            editScore(student , Double.parseDouble(editText.getText().toString()));
        }
    }

    /**
     *
     * This methods do:
     * 1. Set the values inside of the class Student
     * What value?
     * - The second argument
     * How ?
     * Using the setters from the Class Student
     *
     * 2. Get the values and render it again
     * How?
     * Using the Getters from the Class Student
     *
     * Some erros happened here:
     * - Should only render string values
     * - Take in count of the blank spaces values in editCourse, more of 3 and the app crash
     *
     * @param student
     * @param newName/newAge/newCourse/newScore
     */

    private void editName(Student student,String newName){
        TextView name = findViewById(R.id.txtName);
        student.setName(newName);
        name.setText(student.getName());
    }
    public void editAge(Student student, int newAge){
        TextView age = findViewById(R.id.txtAge);
        student.setAge(newAge);
        age.setText(String.valueOf(student.getAge()));
    }
    private void editCourse(Student student, String newCourse){

        if (newCourse.contains("  ") || newCourse.contains("   ")) {
            Toast.makeText(getApplicationContext(), "Procura no dejar espacios", Toast.LENGTH_SHORT).show();
        }else{
            String[] tokens = newCourse.replace(" ","").split("-");

            for(int i=0; i<tokens.length;i++){
                student.setCourse(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
            }

            TextView course = findViewById(R.id.txtCourse);
            course.setText(String.valueOf(student.getCourse()) + " - " + String.valueOf(student.getGroup()));
        }

    }
    private void editScore(Student student, double newScore){
        TextView score = findViewById(R.id.txtScore);
        student.setScore(newScore);
        score.setText(String.valueOf(student.getScore()));
    }


}
