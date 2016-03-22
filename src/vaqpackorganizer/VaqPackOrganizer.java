/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpackorganizer;


import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Corey
 */
public class VaqPackOrganizer extends Application {
    
    public ArrayList<Student> students = new ArrayList<Student>();
    public ArrayList<Course> courses = new ArrayList<Course>();
    public ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
    Image imageDecline = new Image(getClass().getResourceAsStream("decline-button.png"));
    Image weekImg = new Image(getClass().getResourceAsStream("calendar_view_week.png"));
    ImageView weekImgView = new ImageView();
    
    Button weeklyScheduleBttn ;
    Button monthlyScheduleBttn;
    Button schoolInfoBttn;
    ToolBar toolBar; 
    ColumnConstraints column;      
    RowConstraints row;      
    GridPane grid;
    ObjectProperty<Font> fontTracking;
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1200, 650);
        
        grid = new GridPane();
        weekImgView.setImage(weekImg);
        weekImgView.setFitHeight(100);
        weekImgView.setFitWidth(100);
        weekImgView.setPreserveRatio(true);
        weekImgView.smoothProperty();
        
        Label weeklyScheduleBttnTxt = new Label ("Weekly Schedule");
        Label monthlyScheduleBttnTxt = new Label ("Monthly Schedule");
        Label schoolInfoBttnTxt = new Label ("School Information");
        Label userManagerTxt = new Label ("User Management");
        ToggleButton userManagerBttn = new ToggleButton(userManagerTxt.getText(), new ImageView(imageDecline));
        userManagerBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleButton weeklyScheduleBttn = new ToggleButton(weeklyScheduleBttnTxt.getText(), new ImageView(imageDecline));
        weeklyScheduleBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleButton monthlyScheduleBttn = new ToggleButton(monthlyScheduleBttnTxt.getText(),new ImageView(imageDecline));
        monthlyScheduleBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleButton schoolInfoBttn = new ToggleButton(schoolInfoBttnTxt.getText(),new ImageView(imageDecline));
        schoolInfoBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleGroup toolBarGroup = new ToggleGroup();   
        weeklyScheduleBttn.setToggleGroup(toolBarGroup);
        monthlyScheduleBttn.setToggleGroup(toolBarGroup);
        schoolInfoBttn.setToggleGroup(toolBarGroup);
        userManagerBttn.setToggleGroup(toolBarGroup);
        
        toolBar = new ToolBar(weeklyScheduleBttn,new Separator(),monthlyScheduleBttn,new Separator(),schoolInfoBttn,new Separator(),userManagerBttn);
        toolBar.boundsInParentProperty();
        toolBar.setBorder(Border.EMPTY);
        toolBar.setBackground(Background.EMPTY);
        borderPane.setMinSize(0,0);
        borderPane.setStyle("-fx-border-color: black;");
        
        //Setting toolbar styles
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.getItems().get(0).setStyle("-fx-base: #b6e7c9;");
        toolBar.getItems().get(2).setStyle("-fx-base: #b6e7c9;");
        toolBar.getItems().get(4).setStyle("-fx-base: #b6e7c9;");

        //On mouse hover enlarge to 1.5.times size
        {
        toolBar.getItems().get(0).setOnMouseEntered((ae) -> {
        toolBar.getItems().get(0).setScaleX(1.1);
        toolBar.getItems().get(0).setScaleY(1.1);
        });
        toolBar.getItems().get(2).setOnMouseEntered((ae) -> {
        toolBar.getItems().get(2).setScaleX(1.1);
        toolBar.getItems().get(2).setScaleY(1.1);
        });
        
        toolBar.getItems().get(4).setOnMouseEntered((ae) -> {
        toolBar.getItems().get(4).setScaleX(1.1);
        toolBar.getItems().get(4).setScaleY(1.1);
        });
        }
        //On mouse exit return Button to normal size
        {
        toolBar.getItems().get(0).setOnMouseExited((ae) -> {
        toolBar.getItems().get(0).setScaleX(1);
        toolBar.getItems().get(0).setScaleY(1);
        });
        toolBar.getItems().get(2).setOnMouseExited((ae) -> {
        toolBar.getItems().get(2).setScaleX(1);
        toolBar.getItems().get(2).setScaleY(1);
        });
        toolBar.getItems().get(4).setOnMouseExited((ae) -> {
        toolBar.getItems().get(4).setScaleX(1);
        toolBar.getItems().get(4).setScaleY(1);
        });
        }
    //Bindings
        fontTracking = new SimpleObjectProperty<Font>(Font.font(8));
        weeklyScheduleBttn.fontProperty().bind(fontTracking);
        schoolInfoBttn.fontProperty().bind(fontTracking);   
        monthlyScheduleBttn.fontProperty().bind(fontTracking);
        userManagerBttn.fontProperty().bind(fontTracking);
        //Binding the toolbar size to the border pane
        toolBar.setMinSize(0, 0);        
        toolBar.prefHeightProperty().bind(borderPane.heightProperty().divide(6));
        toolBar.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        //Toolbar button widths
        weeklyScheduleBttn.prefWidthProperty().bind(toolBar.widthProperty().divide(5));
        monthlyScheduleBttn.prefWidthProperty().bind(toolBar.widthProperty().divide(5));
        schoolInfoBttn.prefWidthProperty().bind(toolBar.widthProperty().divide(5));
        userManagerBttn.prefWidthProperty().bind(toolBar.widthProperty().divide(5));
       //Toolbar button heigths
        weeklyScheduleBttn.prefHeightProperty().bind(toolBar.heightProperty());
        monthlyScheduleBttn.prefHeightProperty().bind(toolBar.heightProperty());
        schoolInfoBttn.prefHeightProperty().bind(toolBar.heightProperty());
        userManagerBttn.prefHeightProperty().bind(toolBar.heightProperty());
        //Bind the borderPane to the scene
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
//Listeners for both height and width of primary scene// resizes buttons & toolbar & text
      
        scene.widthProperty().addListener(new ChangeListener<Number>()
       {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
        {
      fontTracking.set(Font.font(newWidth.doubleValue() / 90));
              }
    });
        
        scene.heightProperty().addListener(new ChangeListener<Number>()
       {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
        {
      fontTracking.set(Font.font(newHeight.doubleValue() / 60));
              }
    });
        
 //Setup times(ticks), these objects are returned by the ToggleButton when pressed and the passed on to the generateTicks function
 Object object = 0;
 Object object1 = 15;
 Object object2 = 30;
 //casting object to int
 int hourly = (int) object;
 int quarters = (int) object1;
 int biHours = (int) object2;
 //Create toggleButtons for bottom 3 button toolBar
 ToggleButton tb1 = new ToggleButton("Bi-Hours");
 //Binding the ToggleButton (tgb)
 tb1.fontProperty().bind(fontTracking);
 //This is where you set the togglebuttons UserData proptertery which in this instance is an object casted as an int 
 tb1.setUserData(biHours);
 ToggleButton tb2 = new ToggleButton("Hours");
 tb2.setUserData(hourly);
 tb2.fontProperty().bind(fontTracking);
 ToggleButton tb3 = new ToggleButton("Quarters");
 tb3.fontProperty().bind(fontTracking);
 tb3.setUserData(quarters);
 tb1.setAlignment(Pos.CENTER);
 tb2.setAlignment(Pos.CENTER);
 tb3.setAlignment(Pos.CENTER);
 ToggleGroup group = new ToggleGroup();
 tb1.setToggleGroup(group);
 tb2.setToggleGroup(group);
 tb3.setToggleGroup(group);   
 
 //Bind button h and w to Toolbar
 ToolBar weeklyViewToolBar = new ToolBar(new Separator(),new Separator(),tb1,new Separator(),tb2,new Separator(),tb3,new Separator(),new Separator());
//The buttons w and h are binded to 1/4 the size of the toolBar
 tb1.prefHeightProperty().bind(weeklyViewToolBar.heightProperty().divide(4));
 tb1.prefWidthProperty().bind(weeklyViewToolBar.widthProperty().divide(4));
 tb2.prefHeightProperty().bind(weeklyViewToolBar.heightProperty().divide(4));
 tb2.prefWidthProperty().bind(weeklyViewToolBar.widthProperty().divide(4));
 tb3.prefHeightProperty().bind(weeklyViewToolBar.heightProperty().divide(4));
 tb3.prefWidthProperty().bind(weeklyViewToolBar.widthProperty().divide(4));
//We create an Hbox to put the toolBar in, an Hbox is easier to manipulate positioning//
 HBox myHbox = new HBox(weeklyViewToolBar);

//Set up binding for weeklyviewtoolBar to toolBar// We bind the bottom toolBar to the top toolBar for easy manipulation
weeklyViewToolBar.prefHeightProperty().bind(toolBar.heightProperty().divide(4));
weeklyViewToolBar.prefWidthProperty().bind(toolBar.widthProperty().divide(2));
//The previous two lines bind the weeklyViewToolBar to 1/4 the h of the toolBar(the top) and the w to 1/2 of the toolBar
weeklyViewToolBar.boundsInParentProperty();
weeklyViewToolBar.setBorder(Border.EMPTY);
weeklyViewToolBar.setBackground(Background.EMPTY);
myHbox.setAlignment(Pos.BOTTOM_CENTER);
myHbox.prefHeightProperty().bind(toolBar.heightProperty().divide(4));

//This is the tgb listener//When pressed the tgb will run function addTimeToGrid passing the userData set earlier 
//
group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    public void changed(ObservableValue<? extends Toggle> ov,
        Toggle toggle, Toggle new_toggle) {
            if (new_toggle == null)
                addTimeToGrid(tb1.getUserData());
             else
             addTimeToGrid(group.getSelectedToggle().getUserData());
    }
});

  borderPane.setTop(toolBar);
        borderPane.setPadding(new Insets(5,10,5,10));

         weeklyScheduleBttn.setOnAction((ae) -> {
        //The tb1.fire() line sets of the onButtonClick event, this allows us to have a default view whenever the weeklyScheduleBttn is clicked
        tb1.fire();
        //This sets the center of the borderPane to weeklyViewGrid
        borderPane.setBottom(myHbox);
        borderPane.setCenter(grid);    
        grid.setHgrow(grid,Priority.ALWAYS);
         });
        
        monthlyScheduleBttn.setOnAction((ae) -> {
        GridPane monthlyGrid = new GridPane();
        HBox monthlyHbox = new HBox();
        monthlyHbox.prefHeightProperty().bind(toolBar.prefHeightProperty());
        borderPane.setCenter(monthlyGrid);
        borderPane.setLeft(monthlyHbox);
         });
       
        //------------------------------------------------------------------TESTING AREA-------------------Create courses and students
    String courseName = "Science";
    String coursePrefix = "3345";
    String courseNumber = "03I";
    String courseDesc = "CSCI";
    String startIndex= "CSCI";
    String endIndex= "CSCI";
    String classRoom= "CSCI";
    String startTime = "CSCI";
    String endTime= "CSCI";
    String firstName= "Corey";
    String middleName= "Brian";
    String LastName= "Solana";
    String passWord= "Pass!234";
    String userName= "solanac";
    String studentId= "CS0366882";
    String emailAddress= "coreysolana@gmail.com";
    String phoneNumber= "956-243-0434";
    int privLvl= 0;
    Professor myProf = new Professor("Emmit","Smith","Brown","emittbrown@gmail.com","9565640987","JHSAJDASD","ASKJDKASD","AKSJDASHD");
    ArrayList<Course> courseList = new ArrayList<Course>();
    ArrayList<Student> studentsRegistered = new ArrayList<Student>();
    ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
    
 
    //These set how many courses and students we are creatig to play around with
    int courseCount = 8;
    int studentCount = 4;
    //Declaring a myStudent and myCourse object
    Student myStudent;
    Course myCourse;
    
    //This for loop creates 8 course objects
    for (int i = 0;i<courseCount;i++)
    {
        //Each time the for loop executes it initializes a new Course object
        myCourse = new Course(courseName + " " + i ,coursePrefix,courseNumber,classRoom,startTime,endTime,courseDesc,startIndex,endIndex,myProf,studentsRegistered);   
        //Add the course to the courses ArrayList
        courses.add(myCourse);
    }
//This initializes a new student
 for (int i = 0;i<courseCount;i++)
 {
     myStudent = new Student(i + "" + firstName,middleName,LastName,passWord,userName,studentId,emailAddress,phoneNumber, privLvl,courseList,appointmentList);    
     students.add(myStudent);
 }
 //Adds course 2 and 3 to student 1
 students.get(0).getCourseList().add(courses.get(1));
 students.get(0).getCourseList().add(courses.get(2));
 
 
 //Add student 0 to courses registered students
 //courses.get(1).getStudentsRegistered().add(students.get(0));
 //courses.get(2).getStudentsRegistered().add(students.get(0));
 int x = courses.get(1).getStudentsRegistered().size();
System.out.print(x);
for (int i = 0;i<courses.get(1).getStudentsRegistered().size();i++)
        System .out.print(courses.get(1).getStudentsRegistered().get(i).getFirstName() + " " + " \n");

 for (int i = 0;i<students.get(0).getCourseList().size();i++)
        System.out.print(students.get(0).getCourseList().get(i).getCourseName() + " " + " \n");
//adding junk for testting git

 
 
 
    primaryStage.setTitle("VaqPack");
        primaryStage.setScene(scene);
        primaryStage.show();
 
 
    }
 public static void main(String[] args) {
           launch(args);
    }

public void addTimeToGrid(Object timeIncrement)
{
        grid.setGridLinesVisible(false);
        int time = Integer.parseInt(timeIncrement.toString());
        grid.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null)));;
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        TimeTicks timeTicks = new TimeTicks(time);
        timeTicks.generateTicks();
        String[] timeIntervals= timeTicks.getTimeTicksStrings();
//This setups up the different views of the weeklySchedule
       {    
    final int numCols =  6;
    //The number of rows is dependent on the timeIncrement object passed from the tgb
    final int numRows = timeIntervals.length;
    for(int i = 0;i<numCols;i++)   
     {
     column = new ColumnConstraints();
     column.setPercentWidth(50);
     grid.getColumnConstraints().add(column);
      }
     for(int i = 0;i<numRows;i++)   
     {
     row = new RowConstraints();
     row.setMinHeight(5);
     row.prefHeightProperty().bind(grid.heightProperty());
     grid.getRowConstraints().add(row);
     }   
        }
    //Add time data to column 1
     for(int i = 0;i<timeIntervals.length;i++)
        {
        Label myButton1 = new Label();
        myButton1.getProperties().clear();
        myButton1.prefWidthProperty().bind(grid.widthProperty().divide(7));
        //myButton1.prefHeightProperty().bind(grid.heightProperty().divide(100));
        myButton1.setAlignment(Pos.CENTER);
        myButton1.setText(timeIntervals[i]);
        myButton1.fontProperty().bind(fontTracking);
        grid.addColumn(0, myButton1);
        }
grid.setGridLinesVisible(true);      
    }


/*students.get(0).getCourseList().add(courses.get(1));
//Register students in course 0
courses.get(0).getStudentsRegistered().add(students.get(0));
courses.get(0).getStudentsRegistered().add(students.get(1));
*///courses.get(0).getStudentsRegistered().add(students.get(2));
 
 }

