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

    private int indexOfStudent = 0;

    private boolean isName = false;
    private boolean isAge = false;
    private boolean isCourse = false;
    private boolean isScore = false;

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

        LinearLayout linearLayout = findViewById(R.id.detailStudent);
        linearLayout.setTranslationY(1000f);

        LinearLayout editingStudent = findViewById(R.id.editingStudentFieldLayout);
        editingStudent.setScaleX(0f);
        editingStudent.setScaleY(0f);

    }
    public void showStudent(View view){
        // Showing button image
        ImageButton closeButton = findViewById(R.id.btnCloseDetailStudent);
        closeButton.animate().rotation(360f).setDuration(500);
        closeButton.setVisibility(View.VISIBLE);

        whichStudent(view);

        // Showing layout
        LinearLayout linearLayout = findViewById(R.id.detailStudent);
        linearLayout.animate().translationY(0f).setDuration(500);
        linearLayout.setVisibility(View.VISIBLE);

    }
    private void whichStudent(View view){
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
    public void closeStudent(View view){
        LinearLayout linearLayout = findViewById(R.id.detailStudent);
        ImageButton closeButton = findViewById(R.id.btnCloseDetailStudent);

        linearLayout.animate().translationY(1000f).setDuration(500);
        //linearLayout.setVisibility(View.INVISIBLE);
        closeButton.setVisibility(View.INVISIBLE);
    }
    public void editField(View view){
        whichField(view);
        launchFieldEditor(view);
    }
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
    private void handleFieldsVariables(boolean name,boolean age, boolean course, boolean score){
        isName = name;
        isAge = age;
        isCourse = course;
        isScore = score;
    }
    private void launchFieldEditor(View view){
        LinearLayout layoutEditing = findViewById(R.id.editingStudentFieldLayout);
        EditText dataToChange = findViewById(R.id.fieldEditStudent);

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


        layoutEditing.animate().scaleX(1).scaleY(1).setDuration(300);
        layoutEditing.setVisibility(View.VISIBLE);

    }
    public void finishEditStudent(View view){
        LinearLayout layoutEditing = findViewById(R.id.editingStudentFieldLayout);
        layoutEditing.animate().scaleX(0).scaleY(0).setDuration(300);
        EditText fieldChanged = findViewById(R.id.fieldEditStudent);

        TextView editName = findViewById(R.id.editTextName);
        TextView editAge = findViewById(R.id.editTextAge);
        TextView editCourse = findViewById(R.id.editTextCourse);
        TextView editScore = findViewById(R.id.editTextScore);

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
    private void whichStudentHasBeenChanged(Student student, EditText editText){

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
