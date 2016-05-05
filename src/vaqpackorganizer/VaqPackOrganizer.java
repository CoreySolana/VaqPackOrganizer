package vaqpackorganizer;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.Period.between;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class VaqPackOrganizer extends Application {
    
    public ArrayList<Student> students = new ArrayList<Student>();
    public ArrayList<Course> courses = new ArrayList<Course>();
    public ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
    Image imageDecline = new Image(getClass().getResourceAsStream("decline-button.png"));
    Image imageStar = new Image(getClass().getResourceAsStream("iconStarGold.png"));
    Image weekImg = new Image(getClass().getResourceAsStream("calendar_view_week.png"));
    ImageView starView = new ImageView(imageStar);
    ImageView weekImgView = new ImageView();
    Connection myConnection = null;
    Statement myStat = null; 
    ResultSet myRes = null;
    Button weeklyScheduleBttn ;
    Button monthlyScheduleBttn;
    Button schoolInfoBttn;
    Button newCourseBttn = new Button("New Course");
    Button regBttn = new Button("Reg for Course");
    Button myCoursesBttn = new Button("My Courses");
    Button modifyCourseBttn = new Button ("Modify Course");
    Button unregCourseBttn = new Button ("Unregister from Course");
    ToolBar toolBar; 
    ColumnConstraints column;      
    RowConstraints row;      
    GridPane grid;
    ObjectProperty<Font> fontTracking;
    Student loggedInStudent;
        @Override
        public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
        //Setups Connection to database
        myConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test","root","Pass!234"); /////
        myStat = myConnection.createStatement();
        
//--------------------------------- START LOGIN PAGE------------------------------------------------------------------
        Stage loginStage = new Stage();
        BorderPane loginBorderPane = new BorderPane();
        Scene loginScene = new Scene(loginBorderPane,400,400);
        loginStage.setScene(loginScene);
        loginStage.show();
        loginBorderPane.setPadding(new Insets(10,50,50,50));
        HBox loginHbox = new HBox();
        loginHbox.setPadding(new Insets(20,20,20,30));
        GridPane loginGridPane = new GridPane();
        loginGridPane.setPadding(new Insets(20,20,20,20));
        loginGridPane.setHgap(5);
        loginGridPane.setVgap(5);
        Label userNameLbl = new Label("Username");
        TextField userNameTxt = new TextField();
        Label passwordLbl = new Label("Password");
        PasswordField passFld = new PasswordField();
        Button loginBtn = new Button("Login");
        Button newUBtn = new Button("New User");
        Label messageLbl = new Label();
        loginGridPane.add(userNameLbl, 0, 0);
        loginGridPane.add(userNameTxt, 1, 0);
        loginGridPane.add(passwordLbl, 0, 1);
        loginGridPane.add(passFld, 1, 1);
        loginGridPane.add(loginBtn, 2, 1);
        loginGridPane.add(newUBtn, 1, 3);
        loginGridPane.add(messageLbl, 1, 2);
        Reflection reflect = new Reflection ();
        reflect.setFraction(.85);
        loginGridPane.setEffect(reflect);
        Text loginTxt = new Text("VaqPaq Login");
        loginTxt.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        loginHbox.getChildren().add(loginTxt);
        loginBorderPane.setTop(loginHbox);
        loginBorderPane.setCenter(loginGridPane);
        //On loginBtn press
//--------------------------------------------------Checks UserName and Password-----------------------------------------------------------------------------
        loginBtn.setOnAction(ae -> {
            String uName = userNameTxt.getText();
            String passWord = passFld.getText();
            String sql = "SELECT * FROM `test`.`students`";
            ObservableList<Student> studentList = FXCollections.observableArrayList();
                try 
                {
                   studentList = readStudents(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            loggedInStudent = getStudent(studentList,uName);
            if(loggedInStudent != null)
            {
                if (loggedInStudent.getPassWord().equals(passWord))
                {
            loginStage.close();
            primaryStage.show();
                }
                else{ 
            Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Access Denied");
               alert.setHeaderText("Username/Password is incorrect");
               alert.setContentText("Please retry");
               alert.showAndWait();
                   }
            }
            else{ 
            Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Access Denied");
               alert.setHeaderText("Username/Password is incorrect");
               alert.setContentText("Please retry");
               alert.showAndWait();
                }
            });
//---------------------------------New User Creation------------------------------------------------------------------
        newUBtn.setOnAction(ae -> {
        Stage newUStage = new Stage();
        BorderPane newUBorderPane = new BorderPane();
        Scene newUScene = new Scene(newUBorderPane,500,700);
        newUStage.setScene(newUScene);
        newUStage.show();     
        newUBorderPane.setPadding(new Insets(10,50,50,50));
        HBox newUHbox = new HBox();
        newUHbox.setPadding(new Insets(20,20,20,30));
        GridPane newUGridPane = new GridPane();
        newUGridPane.setPadding(new Insets(20,20,20,20));
        newUGridPane.setHgap(5);
        newUGridPane.setVgap(5);
        Label nuserfName = new Label("First Name: ");
        TextField nuserfNameTxt = new TextField();
        Label nusermName = new Label("Middle Name: ");
        TextField nusermNameTxt = new TextField();
        Label nuserlName = new Label("Last Name: ");
        TextField nuserlNameTxt = new TextField();
        Label npasswordLbl = new Label("New Password: ");
        PasswordField npassFld = new PasswordField();
        Label nnuserName = new Label("User Name: ");
        TextField nnuserNameTxt = new TextField();
        Label nuserIDName = new Label("ID number: ");
        TextField nuserIDNameTxt = new TextField();
        Label nusereName = new Label("E-mail: ");
        TextField nusereNameTxt = new TextField();
        Label nuserpName = new Label("Phone Number: ");
        TextField nuserpNameTxt = new TextField();
        Button nnewUBtn = new Button("Create New User");
        Label nmessageLbl = new Label();
        newUGridPane.add(nuserfName, 1, 0);
        newUGridPane.add(nuserfNameTxt, 1, 1);
        newUGridPane.add(nusermName, 1, 2);
        newUGridPane.add(nusermNameTxt, 1, 3);
        newUGridPane.add(nuserlName, 1, 4);
        newUGridPane.add(nuserlNameTxt, 1, 5);
        newUGridPane.add(npasswordLbl, 1, 6);
        newUGridPane.add(npassFld, 1, 7);
        newUGridPane.add(nnuserName, 1, 8);
        newUGridPane.add(nnuserNameTxt, 1, 9);
        newUGridPane.add(nuserIDName, 1, 10);
        newUGridPane.add(nuserIDNameTxt, 1, 11);
        newUGridPane.add(nusereName, 1, 12);
        newUGridPane.add(nusereNameTxt, 1, 13);
        newUGridPane.add(nuserpName, 1, 14);
        newUGridPane.add(nuserpNameTxt, 1, 15);
        newUGridPane.add(nnewUBtn, 1, 17);
        newUGridPane.add(nmessageLbl, 1, 18);
        Text newUTxt = new Text("Create New User");
        newUTxt.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        newUHbox.getChildren().add(newUTxt);
        newUBorderPane.setTop(newUHbox);
        newUBorderPane.setCenter(newUGridPane);
        //Grabs data from fields and passes sql command to database to insert the new user
            nnewUBtn.setOnAction(xe -> {
            //Get text from inputs
            String userName = nnuserNameTxt.getText();
            String passWord = npassFld.getText();
            String firstName = nuserfNameTxt.getText();
            String middleName =  nusermName.getText();
            String lastName = nuserlNameTxt.getText();
            String emailAddress = nusereNameTxt.getText();
            String phoneNumber = nuserpNameTxt.getText();
            //Create SQL String
            String newUserSql = "INSERT INTO `test`.`students` (`userName`, `passWord`, `firstName`, `middleName`, `lastName`, `phoneNumber`, `emailAddress`, `privLevel`) VALUES ('" + userName + "', '"+ passWord + "', '" + firstName + "', '" + middleName + "', '" + lastName + "', '" + emailAddress + "', '" + phoneNumber + "', '0');";
            //Pass SQL string to update records function
            updateRecords(newUserSql);
            newUStage.close();
            primaryStage.show();
            });
        });
//-----------------------------END OF LOGIN PAGE------------------------------------------------------------------
        
//-----------------------------START OF SPLASH PAGE---------------------------------------------------------------        
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1200, 650);
        borderPane.setMinSize(0,0);
        borderPane.setStyle("-fx-border-color: black;");
        grid = new GridPane();
        weekImgView.setImage(weekImg);
        weekImgView.setFitHeight(100);
        weekImgView.setFitWidth(100);
        weekImgView.setPreserveRatio(true);
        weekImgView.smoothProperty();
        
        //Create togglebuttons
        ToggleButton userManagerBttn = new ToggleButton("Course Management", new ImageView(imageDecline));
        userManagerBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleButton weeklyScheduleBttn = new ToggleButton("Weekly Schedule", new ImageView(imageDecline));
        weeklyScheduleBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleButton monthlyScheduleBttn = new ToggleButton("Monthly Schedule",new ImageView(imageDecline));
        monthlyScheduleBttn.setContentDisplay(ContentDisplay.TOP);
        ToggleButton schoolInfoBttn = new ToggleButton("School Information",new ImageView(imageDecline));
        schoolInfoBttn.setContentDisplay(ContentDisplay.TOP);
        
        // Create toggle group and add toggle buttons to group
        ToggleGroup toolBarGroup = new ToggleGroup();   
        weeklyScheduleBttn.setToggleGroup(toolBarGroup);
        monthlyScheduleBttn.setToggleGroup(toolBarGroup);
        schoolInfoBttn.setToggleGroup(toolBarGroup);
        userManagerBttn.setToggleGroup(toolBarGroup);
        
        //Assign toolBarGroup to the toolBar and styles
        toolBar = new ToolBar(weeklyScheduleBttn,new Separator(),monthlyScheduleBttn,new Separator(),schoolInfoBttn,new Separator(),userManagerBttn);
        toolBar.boundsInParentProperty();
        toolBar.setBorder(Border.EMPTY);
        toolBar.setBackground(Background.EMPTY);
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.getItems().get(0).setStyle("-fx-base: #b6e7c9;");
        toolBar.getItems().get(2).setStyle("-fx-base: #b6e7c9;");
        toolBar.getItems().get(4).setStyle("-fx-base: #b6e7c9;");
        toolBar.getItems().get(6).setStyle("-fx-base: #b6e7c9;");   
        //On mouse hover enlarge each togglebutton
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
        toolBar.getItems().get(6).setOnMouseEntered((ae) -> {
        toolBar.getItems().get(6).setScaleX(1.1);
        toolBar.getItems().get(6).setScaleY(1.1);
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
        toolBar.getItems().get(6).setOnMouseExited((ae) -> {
        toolBar.getItems().get(6).setScaleX(1);
        toolBar.getItems().get(6).setScaleY(1);
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
        
 //Setup times(ticks), these objects are returned by the ToggleButton when pressed and then passed on to the generateTicks function
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
group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    public void changed(ObservableValue<? extends Toggle> ov,
        Toggle toggle, Toggle new_toggle) {
            if (new_toggle == null)
                addTimeToGrid(tb1.getUserData());
             else
             addTimeToGrid(group.getSelectedToggle().getUserData());
    }
});
//Set the top of the borderpane as our toolbar
        borderPane.setTop(toolBar);
        borderPane.setPadding(new Insets(5,10,5,10));
//Main Buttons ActionEvents
        weeklyScheduleBttn.setOnAction((ae) -> {
        //The tb1.fire() line sets off the onButtonClick event, this allows us to have a default view whenever the weeklyScheduleBttn is clicked
        tb1.fire();
        //This sets the center of the borderPane to weeklyViewGrid
        borderPane.setBottom(myHbox);
        borderPane.setCenter(grid);    
        grid.setHgrow(grid,Priority.ALWAYS);
        
        LocalDate today =  LocalDate.now();
        LocalDate today1 = LocalDate.now().plusDays(1);
        LocalDate today2 = LocalDate.now().plusDays(2);
        LocalDate today3 = LocalDate.now().plusDays(3);
        LocalDate today4 = LocalDate.now().plusDays(4);
        
        String sqla = ("SELECT * FROM test.appointments WHERE studentId = '" +loggedInStudent.getStudentId() + "' AND apptDate = '" + today.toString() + "';");
        String sqlb = ("SELECT * FROM test.appointments WHERE studentId = '" +loggedInStudent.getStudentId() + "' AND apptDate = '" + today1.toString() + "';");
        String sqlc = ("SELECT * FROM test.appointments WHERE studentId = '" +loggedInStudent.getStudentId() + "' AND apptDate = '" + today2.toString() + "';");
        String sqld = ("SELECT * FROM test.appointments WHERE studentId = '" +loggedInStudent.getStudentId() + "' AND apptDate = '" + today3.toString() + "';");
        String sqle = ("SELECT * FROM test.appointments WHERE studentId = '" +loggedInStudent.getStudentId() + "' AND apptDate = '" + today4.toString() + "';");
        
        ObservableList<Appointment> lista =FXCollections.observableArrayList() ;
            try {
                lista = readAppointments(sqla);
                System.out.println(lista.size());
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        ObservableList<Appointment> listb = FXCollections.observableArrayList();
            try {
                listb = readAppointments(sqlb);
                System.out.println(listb.size());
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        ObservableList<Appointment> listc = FXCollections.observableArrayList();
            try {
                listc = readAppointments(sqlc);
                System.out.println(listc.size());
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        ObservableList<Appointment> listd = FXCollections.observableArrayList();
            try {
                listd = readAppointments(sqld);
                System.out.println(listd.size());
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        ObservableList<Appointment> liste = FXCollections.observableArrayList();
            try {
                liste = readAppointments(sqle);
                System.out.println(liste.size());
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(today.getDayOfWeek().toString(),  lista.size()),
                new PieChart.Data(today1.getDayOfWeek().toString(), listb.size()),
                new PieChart.Data(today2.getDayOfWeek().toString(), listc.size()),
                new PieChart.Data(today3.getDayOfWeek().toString(), listd.size()),
                new PieChart.Data(today4.getDayOfWeek().toString(), liste.size()));
        
        final PieChart chart = new PieChart(pieChartData);
        borderPane.setRight(chart);
         });
        //User Management Button Listener
        userManagerBttn.setOnAction(aq -> {
        borderPane.setCenter(null);
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(regBttn,newCourseBttn,myCoursesBttn,modifyCourseBttn,unregCourseBttn);
        borderPane.setLeft(buttonBox);
        
//---------------------------------------------Unregister for a Course Bttn----------------------------------------------
        
        unregCourseBttn.setOnAction(ai -> {
        
        String sql = ("SELECT * FROM test.registeredstudents WHERE studentId =" + loggedInStudent.getStudentId() + ";");
        ObservableList<Registered> myRegCourses = FXCollections.observableArrayList();
        ObservableList<Course> myCourses = FXCollections.observableArrayList();
        TableView<Course> myCourseView = new TableView<Course>();
        borderPane.setCenter(myCourseView);
        try {
           myRegCourses = readRegCourses(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
        }
         for (int i = 0; i < myRegCourses.size(); i++) {
             int courseId = myRegCourses.get(i).getCourseId();
                String sql2 = ("SELECT * FROM `test`.`courses` WHERE courseId =" + courseId + ";");
                ObservableList<Course> holder= FXCollections.observableArrayList();
                    try {
                        holder = readCourses(sql2);
                    } catch (SQLException ex) {
                        Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                myCourses = FXCollections.concat(myCourses,holder);
             }
            myCourseView.setItems(myCourses);
            TableColumn<Course,Integer> courseId= new TableColumn<>("courseId");
            courseId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("courseId"));
            TableColumn<Course,String> courseName = new TableColumn<>("courseName");
	    courseName.setCellValueFactory(new PropertyValueFactory<Course,String>("courseName"));
            TableColumn<Course,String> classRoom = new TableColumn<>("classRoom");
	    classRoom.setCellValueFactory(new PropertyValueFactory<Course,String>("classRoom"));
            TableColumn<Course,String> startDate = new TableColumn<>("startDate");
            startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            TableColumn<Course,String> startTime = new TableColumn<>("startTime");
            startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            TableColumn<Course,String> endTime = new TableColumn<>("endTime");
            endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            TableColumn<Course,String> courseDesc = new TableColumn<>("courseDesc");
            courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
            TableColumn<Course,Integer> profId= new TableColumn<>("profId");
            profId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("profId"));
            myCourseView.getColumns().setAll(courseId,courseName,classRoom,startDate,startTime,endTime,courseDesc,profId);
        
        myCourseView.setOnMouseClicked(af -> {
        Course thisCourse = myCourseView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Unregister Course: " + " " + thisCourse.getCourseName());
        alert.setContentText("Are you sure you want to Unregister for this Course?");

        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                    try 
                    {
                        unregisterCourse(thisCourse);
                    }
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            else
            {
                // ... user chose CANCEL or closed the dialog
            }
              
        });
          
      });
        
        //----------------------------------------------------------Modify Courses-----------------------------------------------
        modifyCourseBttn.setOnAction(ag -> {
        String mod = ("SELECT * FROM test.courses;");
        TableView<Course> courseView = viewCourses(mod);
        borderPane.setCenter(courseView);
        courseView.setOnMouseClicked(ao -> {
            Stage modCourseStage = new Stage();
            BorderPane myPane = new BorderPane();
            Scene modCourseScene = new Scene(myPane,500,500);
            modCourseStage.setScene(modCourseScene);
            TimeTicks timeTicks = new TimeTicks(30);
            timeTicks.generateTicks();
            String[] timeIntervals= timeTicks.getTimeTicksStrings();
            ObservableList<String> list = FXCollections.observableArrayList(timeIntervals);
            ComboBox apptStartTimeCombo = new ComboBox();
            ComboBox apptEndTimeCombo = new ComboBox();
            apptStartTimeCombo.setItems(list);
            apptEndTimeCombo.setItems(list);
            Course thisCourse = courseView.getSelectionModel().getSelectedItem();
            apptStartTimeCombo.setValue(thisCourse.getStartTime());
            apptEndTimeCombo.setValue(thisCourse.getEndTime());
            Label courseSplash = new Label("Modify course");
            Label courseNameLbl = new Label("Enter Course Name");
            Label courseClassLbl = new Label("Enter Class Room");
            Label courseStartDateLbl = new Label("Pick Start Date");
            Label courseStartTimeLbl = new Label("Pick Start Time");
            Label courseEndTimeLbl = new Label("Pick End Time");
            Label courseDescriptionLbl = new Label("Enter Course Description");
            Button courseSubmit = new Button ("Modify Course");
            TextField courseNameTxt = new TextField();
            courseNameTxt.setText(thisCourse.getCourseName());
            TextField courseClassTxt = new TextField();
            courseClassTxt.setText(thisCourse.getClassRoom());
            TextField courseDescriptionTxt = new TextField();
            courseDescriptionTxt.setText(thisCourse.getCourseDesc());
            String date = thisCourse.getStartDate();
            DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate myDate = LocalDate.parse(date,formatter);
            DatePicker startDatePicker = new DatePicker(myDate);
            VBox myFlowPane = new VBox(5);
            VBox box = new VBox();
            box.setMaxHeight(100);
            myFlowPane.getChildren().addAll(courseSplash,courseNameLbl,courseNameTxt,courseClassLbl,courseClassTxt,courseStartDateLbl,startDatePicker,courseStartTimeLbl,apptStartTimeCombo,courseEndTimeLbl,apptEndTimeCombo,courseDescriptionLbl,courseDescriptionTxt,courseSubmit);
            myPane.setCenter(myFlowPane);
             //Listner for Course Submit 
                courseSubmit.setOnAction(ap -> {
                String courseName = courseNameTxt.getText();
                String courseLocation = courseClassTxt.getText();
                String startDate =  startDatePicker.getValue().toString();
                String courseStartTime = apptStartTimeCombo.getSelectionModel().getSelectedItem().toString();
                String courseEndTime = apptEndTimeCombo.getSelectionModel().getSelectedItem().toString();
                String courseDescription = courseDescriptionTxt.getText();
            //Need to grab profId from a table
                int profId = 2;
                String mySql = ("UPDATE `test`.`courses` SET `courseName`='" + courseName + "', `classRoom`='" + courseLocation + "', `startDate`='" + startDate +"', `startTime`='" + courseStartTime +"', `endTime`='" + courseEndTime + "', `courseDesc`='" + courseDescription +" ', `profId`='4' WHERE `courseId`='" + thisCourse.getCourseId()+"';");
                     try {  
                        myStat.executeUpdate(mySql);
                            } catch (SQLException ex) {
                            Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                     String mod2 = ("SELECT * FROM test.courses;");
                     TableView<Course> courseView2 = viewCourses(mod);
                     borderPane.setCenter(courseView2);
                        });
               modCourseStage.show();
            });
        
        });
        //-----------------------------------------------------------View My Courses---------------------------------------------
        myCoursesBttn.setOnAction(as -> {
        String sql = ("SELECT * FROM test.registeredstudents WHERE studentId =" + loggedInStudent.getStudentId() + ";");
        ObservableList<Registered> myRegCourses = FXCollections.observableArrayList();
        ObservableList<Course> myCourses = FXCollections.observableArrayList();
        TableView<Course> myCourseView = new TableView<Course>();
        borderPane.setCenter(myCourseView);
        try {
           myRegCourses = readRegCourses(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
        }
         for (int i = 0; i < myRegCourses.size(); i++) {
             System.out.println(myRegCourses.get(i).getCourseId());
             int courseId = myRegCourses.get(i).getCourseId();
                String sql2 = ("SELECT * FROM `test`.`courses` WHERE courseId =" + courseId + ";");
                ObservableList<Course> holder= FXCollections.observableArrayList();
                    try {
                        holder = readCourses(sql2);
                    } catch (SQLException ex) {
                        Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                myCourses = FXCollections.concat(myCourses,holder);
             }
            myCourseView.setItems(myCourses);
            TableColumn<Course,Integer> courseId= new TableColumn<>("courseId");
            courseId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("courseId"));
            TableColumn<Course,String> courseName = new TableColumn<>("courseName");
	    courseName.setCellValueFactory(new PropertyValueFactory<Course,String>("courseName"));
            TableColumn<Course,String> classRoom = new TableColumn<>("classRoom");
	    classRoom.setCellValueFactory(new PropertyValueFactory<Course,String>("classRoom"));
            TableColumn<Course,String> startDate = new TableColumn<>("startDate");
            startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            TableColumn<Course,String> startTime = new TableColumn<>("startTime");
            startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            TableColumn<Course,String> endTime = new TableColumn<>("endTime");
            endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            TableColumn<Course,String> courseDesc = new TableColumn<>("courseDesc");
            courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
            TableColumn<Course,Integer> profId= new TableColumn<>("profId");
            profId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("profId"));
            myCourseView.getColumns().setAll(courseId,courseName,classRoom,startDate,startTime,endTime,courseDesc,profId);
        });
         //------------------------------------------------------------Register For Course Button---------------------------------------
        regBttn.setOnAction(ay ->{
        String sql = "SELECT * FROM test.courses";
        TableView<Course> courseView = viewCourses(sql);
        FlowPane regCoursePane = new FlowPane();
        Label regCourseLbl = new Label("Choose a course to register for");
        Button regConfirmBttn = new Button("Register");
        Button regCancelBttn = new Button("Cancel");
        //Confirm/Cancel Button listeners
        regCoursePane.getChildren().addAll(regCourseLbl,regConfirmBttn,regCancelBttn,courseView);
        borderPane.setCenter(regCoursePane);
        //Grabs Course Object
        courseView.setOnMouseClicked(as -> {
            //Takes selection
            Course myCourse = (Course)courseView.getSelectionModel().getSelectedItem();
            regConfirmBttn.setOnAction(ax -> 
                {
                registerCourse(myCourse);
                });
            regCancelBttn.setOnAction(ae -> 
                {
                //TODO Set back to page
                });
          });
    });
        
        //-----------------------------------------------------------New Course Button-------------------------------------------------
        
        newCourseBttn.setOnAction(af -> {
        TimeTicks timeTicks = new TimeTicks(30);
        timeTicks.generateTicks();
        String[] timeIntervals= timeTicks.getTimeTicksStrings();
        ObservableList<String> list = FXCollections.observableArrayList(timeIntervals);
        ComboBox apptStartTimeCombo = new ComboBox();
        ComboBox apptEndTimeCombo = new ComboBox();
        apptStartTimeCombo.setItems(list);
        apptEndTimeCombo.setItems(list);
        Label courseSplash = new Label("Create a new course");
        Label courseNameLbl = new Label("Enter Course Name");
        Label courseClassLbl = new Label("Enter Class Room");
        Label courseStartDateLbl = new Label("Pick Start Date");
        Label courseStartTimeLbl = new Label("Pick Start Time");
        Label courseEndTimeLbl = new Label("Pick End Time");
        Label courseDescriptionLbl = new Label("Enter Course Description");
        Label courseProfLbl = new Label("Choose Professor");
        Button courseSubmit = new Button ("Create Course");
        TextField courseNameTxt = new TextField();
        TextField courseClassTxt = new TextField();
        DatePicker startDatePicker = new DatePicker();
        TextField courseDescriptionTxt = new TextField();
        VBox myFlowPane = new VBox(5);
        VBox box = new VBox();
        box.setMaxHeight(100);
        myFlowPane.getChildren().addAll(courseSplash,courseNameLbl,courseNameTxt,courseClassLbl,courseClassTxt,courseStartDateLbl,startDatePicker,courseStartTimeLbl,apptStartTimeCombo,courseEndTimeLbl,apptEndTimeCombo,courseDescriptionLbl,courseDescriptionTxt,courseSubmit);
        borderPane.setCenter(myFlowPane);
       //Listner for Course Submit 
        courseSubmit.setOnAction(ag -> {
        String courseName = courseNameTxt.getText();
        String courseLocation = courseClassTxt.getText();
        String startDate =  startDatePicker.getValue().toString();
        String courseStartTime = apptStartTimeCombo.getSelectionModel().getSelectedItem().toString();
        String courseEndTime = apptEndTimeCombo.getSelectionModel().getSelectedItem().toString();
        String courseDescription = courseDescriptionTxt.getText();
        //Need to grab profId from a table
        int profId = 2;
        String mySql = "INSERT INTO `test`.`courses` (`courseName`, `classRoom`, `startDate`, `startTime`, `endTime`, `courseDesc`, `profId`) VALUES ('" + courseName + "', '" + courseLocation + "', '" + startDate + "', '" + courseStartTime + "', '" + courseEndTime + "', '" + courseDescription + "', '2');";
        
             try {  
                myStat.executeUpdate(mySql);
                    } catch (SQLException ex) {
                    Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            });
        });
 //-------------------------------------------------------------------END NEW COURSE----------------------------------------------------------------------------
 //-------------------------------------------------------MONTH VIEW-----------------------------------------------------------------------------------------
        monthlyScheduleBttn.setOnAction((ae) -> {
        borderPane.setBottom(null);
        HBox monthlyHbox = new HBox();
        HBox monthlyHbox2 = new HBox();
        monthlyHbox.prefHeightProperty().bind(toolBar.prefHeightProperty());
        Button newApptNoDateBttn = new Button("Create Appointment");
        //---------------------------------SETUP MONTH VISUAL---------------------------------------------------------------------------------------------
        LocalDate date = LocalDate.now();
        String month = date.getMonth().toString();
        String sql = "SELECT * FROM test.appointments WHERE studentId = " + loggedInStudent.getStudentId() + ";";
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> thisMonthAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> mondayAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> tuesdayAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> wednesdayAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> thursdayAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> fridayAppts = FXCollections.observableArrayList();
            try 
            {
                allAppts = readAppointments(sql);
            } catch (SQLException ex)
            
            {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            String monday = "MONDAY";
            String tuesday = "TUESDAY";
            String wednesday = "WEDNESDAY";
            String thursday = "THURSDAY";
            String friday = "FRIDAY";
            for (int i = 0; i < allAppts.size(); i++) 
            {
            String thisDate = allAppts.get(i).getApptDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate myDate = LocalDate.parse(thisDate,formatter);
          
            String thisMonth = myDate.getMonth().toString();
                if (thisMonth.equals(month)) 
                {
                Appointment thisAppt = allAppts.get(i);
                thisMonthAppts.add(thisAppt);
                }
            }
          
            for (int i = 0; i < thisMonthAppts.size(); i++) 
            {
            
            Appointment myAppt = thisMonthAppts.get(i);
            String thisDate = myAppt.getApptDate();
            System.out.println(thisDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate myDate = LocalDate.parse(thisDate,formatter);
            String thisDay = myDate.getDayOfWeek().toString();
            System.out.println(thisDay); 
                
                switch (thisDay)
                {
                    case "MONDAY":
                mondayAppts.add(myAppt);
                        break;
                   
                    case "TUESDAY":
                tuesdayAppts.add(myAppt);
                        break;
                        
                    case "WEDNESDAY":
                wednesdayAppts.add(myAppt);
                        break;
                        
                    case "THURSDAY":
                thursdayAppts.add(myAppt);
                        break;
                    
                    case "FRIDAY":
                fridayAppts.add(myAppt);
                        break;
                }
                
                
                
             }
            
            ObservableList<PieChart.Data> pieChartData2 = FXCollections.observableArrayList(
                new PieChart.Data(monday,  mondayAppts.size()),
                new PieChart.Data(tuesday, tuesdayAppts.size()),
                new PieChart.Data(wednesday, wednesdayAppts.size()),
                new PieChart.Data(thursday, thursdayAppts.size()),
                new PieChart.Data(friday, fridayAppts.size()));
        
        final PieChart chart2 = new PieChart(pieChartData2);
        borderPane.setRight(chart2);
        //-------------------------------------------------NEW APPOINTMENT BUTTON NO DATE (MUST SELECT DATE FROM A DATEPICKER)----------------------------------
            //newApptNoDateBttn listener
        {
        newApptNoDateBttn.setOnAction(af -> {
          Stage apptStage = new Stage();
          FlowPane myFlowPane = new FlowPane();
          Scene apptScene = new Scene(myFlowPane,300,400);
          TimeTicks timeTicks = new TimeTicks(30);
          timeTicks.generateTicks();
          String[] timeIntervals= timeTicks.getTimeTicksStrings();
          ObservableList<String> list = FXCollections.observableArrayList(timeIntervals);
          Label startTime = new Label("Appointment Start Time");
          Label endTime = new Label("Appointment End Time");
          ComboBox apptStartTimeCombo = new ComboBox();
          ComboBox apptEndTimeCombo = new ComboBox();
          apptStartTimeCombo.setItems(list);
          apptEndTimeCombo.setItems(list);
          Label apptDateLbl = new Label(" Select Date");
          Label apptLocLbl = new Label("Input Location");
          Label apptReasonLbl = new Label("Input Reason");
          TextField apptLocTxtFld = new TextField();
          TextField apptReasonTxtFld = new TextField();
          myFlowPane.setPadding(new Insets(10, 10, 10, 10));
          myFlowPane.setHgap(20);
          myFlowPane.setVgap(20);
          DatePicker myPicker = new DatePicker();
          Button cancelBttn = new Button ("Cancel");
          Button submitNewUserBttn = new Button("Create Appt.");
          myFlowPane.getChildren().addAll(apptDateLbl,myPicker,startTime,apptStartTimeCombo,endTime,apptEndTimeCombo,apptLocLbl,apptLocTxtFld,apptReasonLbl,apptReasonTxtFld,submitNewUserBttn, cancelBttn);    
          //---------------------------------------------------------Create NEW User--------------------------------------------------------------------
          submitNewUserBttn.setOnAction(ax -> {
             try {
             String createApptsql = "";
             myStat.executeUpdate(createApptsql);
                  } catch (SQLException ex) {
                    Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                    });
          
                cancelBttn.setOnAction(as -> {
                 apptStage.close();
                  });
                apptStage.setScene(apptScene);
                apptStage.show();
                  });
            }
       
        //This sets up the date for the semester for labeling later
        LocalDate semesterStart = LocalDate.of(2016,1,15);
        LocalDate semesterEnd = LocalDate.of(2016, 5, 16);
        long numDays = DAYS.between(semesterStart,semesterEnd);
 
//------------------------------------------------------------DatePicker Setup-----------------------------------------------------------------------------
       DatePicker datePicker = new DatePicker();
       //DayCellFactory allows modification of cells in datePicker
        Callback<DatePicker,DateCell> dayCellFactory1 = 
               new Callback <DatePicker, DateCell>(){
                    public DateCell call (final DatePicker datePicker){
                        return new DateCell(){
                        @Override
                            public void updateItem(LocalDate item, boolean empty){
                            //Must Call Super
                                super.updateItem(item, empty);
                                this.setPrefSize(75, 75);    
                                //Action Events for when a date cell is clicked
                                this.setOnMouseClicked(a -> {
                                        Stage apptStage = new Stage();
                                        BorderPane myBorderPane = new BorderPane();
                                        Scene apptScene = new Scene(myBorderPane,500,500);
                                        TimeTicks timeTicks = new TimeTicks(30);
                                        timeTicks.generateTicks();
                                        String[] timeIntervals= timeTicks.getTimeTicksStrings();
                                        ObservableList<String> list = FXCollections.observableArrayList(timeIntervals);
                                        Label startTime = new Label("Appointment Start Time");
                                        Label endTime = new Label("Appointment End Time");
                                        HBox topBox = new HBox(10);
                                        String greeting = ("Hello " + loggedInStudent.getFirstName() + " " + loggedInStudent.getLastName());
                                        String todaysDate = item.toString();
                                        Label todaysEvents = new Label("Your Appointments for " + todaysDate);
                                        Label greetLbl = new Label(greeting);
                                        topBox.getChildren().addAll(greetLbl,todaysEvents);
                                        myBorderPane.setTop(topBox);
                                        ComboBox apptStartTimeCombo = new ComboBox();
                                        ComboBox apptEndTimeCombo = new ComboBox();
                                        apptStartTimeCombo.setItems(list);
                                        apptEndTimeCombo.setItems(list);
                                        //Sets it to todays date
                                        Label apptLocLbl = new Label("Appt Location");
                                        Label apptReasonLbl = new Label("Appt Reason");
                                        TextField apptLocTxtFld = new TextField();
                                        TextField apptReasonTxtFld = new TextField();
                                        String apptDate = item.toString();
                                        Button apptBttn = new Button("Create Appt.");
                                        Button createApptBttn = new Button("Create Appt.");
                                        Button cancelBttn = new Button ("Cancel");
                                        String sql = "SELECT * FROM `test`.`appointments` WHERE studentId = '" + loggedInStudent.getStudentId() + "' AND apptDate = '" + apptDate +"';";
                                        TableView<Appointment> apptTable = viewAppointments(sql);
                                        apptTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                                        VBox myVB = new VBox(10);
                                        myVB.getChildren().addAll(startTime,apptStartTimeCombo,endTime,apptEndTimeCombo,apptLocLbl,apptLocTxtFld,apptReasonLbl,apptReasonTxtFld,createApptBttn,cancelBttn);    
                                        
                                        //Modifiying appointment
                                        Label startTime2 = new Label("Appointment Start Time");
                                        Label endTime2 = new Label("Appointment End Time");
                                        ComboBox apptStartTimeCombo2 = new ComboBox();
                                        ComboBox apptEndTimeCombo2 = new ComboBox();
                                        apptStartTimeCombo2.setItems(list);
                                        apptEndTimeCombo2.setItems(list);
                                        Label apptLocLbl2 = new Label("Appt Location");
                                        Label apptReasonLbl2 = new Label("Appt Reason");
                                        TextField apptLocTxtFld2 = new TextField();
                                        TextField apptReasonTxtFld2 = new TextField();
                                        Button cancelBttn2 = new Button ("Cancel");
                                        Button updateAppointmentBttn = new Button("Update");
                                        VBox myVB2 = new VBox(10);
                                        Button emailBtn = new Button("Send Email");
                                        myVB2.getChildren().addAll(startTime2,apptStartTimeCombo2,endTime2,apptEndTimeCombo2,apptLocLbl2,apptLocTxtFld2,apptReasonLbl2,apptReasonTxtFld2,emailBtn,updateAppointmentBttn,cancelBttn2);    
                                        
                                        //Email Button
                                        emailBtn.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                            Stage emailStage = new Stage();
                                            GridPane emailGrid = new GridPane();
                                            
                                            ObservableList<Appointment> myAppts = apptTable.getSelectionModel().getSelectedItems();
                                            Text to = new Text("To:");
                                            Text subject = new Text("Subject:");
                                            TextField toInput = new TextField();
                                            TextField subjectInput = new TextField(loggedInStudent.getFirstName()+"s" + " Appointments on " + todaysDate);
                                            Button clearButton = new Button("Clear");
                                            Button submitButton = new Button("Submit");
                                            VBox horizontal = new VBox();
                                            HBox buttonPane = new HBox();
                                            //emailGrid.add(to, columnIndex, rowIndex);
                                            emailGrid.add(to, 0, 0);
                                            emailGrid.add(toInput, 1, 0);
                                            emailGrid.add(subject, 0, 1);
                                            emailGrid.add(subjectInput, 1, 1);
                                            buttonPane.getChildren().addAll(clearButton,submitButton );
                                            buttonPane.alignmentProperty();
                                            TextArea message = new TextArea();
                                            horizontal.getChildren().addAll(emailGrid,message, buttonPane );
                                            for (int i = 0; i < myAppts.size(); i++) {
                                            message.appendText("Location: " + myAppts.get(i).apptLoc +" Reason: " + myAppts.get(i).apptReason + " StartTime: " + myAppts.get(i).apptStartTime + " EndTime: " + myAppts.get(i).apptEndTime + "\n");
                                            }
                                           
                                         //Submit the email bttn
                                     submitButton.setOnAction((as) -> {
                                            System.out.println("Button Action");
                                            SimpleEmail se = new SimpleEmail();
                                            se.setTo_Text(toInput.getText());
                                            se.setSubject_Text(subjectInput.getText());
                                            se.setMsg_Text(message.getText());
                                            se.Email();
                                            });

                                     clearButton.setOnAction((aw) -> {
                                             toInput.setText("");
                                             subjectInput.setText("");
                                             message.setText("");
                                            });
                                            Scene emailScene = new Scene(horizontal,400,400);
                                            emailStage.setScene(emailScene);
                                            emailStage.show();
                                            }
                                        });
                                        VBox box = new VBox(10);
                                        box.getChildren().addAll(apptBttn);
                                        myBorderPane.setLeft(box);
                                        myBorderPane.setCenter(apptTable);
                                        apptBttn.setOnAction(ad -> {
                                        myBorderPane.setLeft(myVB);
                    
                                        createApptBttn.setOnAction(ax -> {
                                            try {
                                                
                                                String apptStartTime= apptStartTimeCombo.getSelectionModel().getSelectedItem().toString();
                                                String apptEndTime= apptEndTimeCombo.getSelectionModel().getSelectedItem().toString();
                                                //This needs to be changed to reflect the currentlu logged studentid
                                                String createApptsql = ("INSERT INTO `test`.`appointments` (`studentId`, `apptDate`, `apptStartTime`, `apptEndTime`, `apptLoc`, `apptReason`) VALUES ('" + loggedInStudent.getStudentId() + "', '" + apptDate + "', '" + apptStartTime + "', '" + apptEndTime + "', '" +apptLocTxtFld.getText()+ "', '" + apptReasonTxtFld.getText() + "');");
                                                myStat.executeUpdate(createApptsql);
                                                TableView<Appointment> apptTable2 = viewAppointments(sql);
                                                myBorderPane.setCenter(apptTable2);
                                            }
                                            catch (SQLException ex) {
                                                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        });
                                        
                                        cancelBttn.setOnAction(as -> 
                                        {
                                        apptStage.close();
                                        });
                                 });
                                        
                                        apptTable.setOnMouseClicked(aq -> {
                                
                                        Appointment myAppt = apptTable.getSelectionModel().getSelectedItem();
                                        myBorderPane.setLeft(myVB2);
                                        String sTime = myAppt.apptStartTime;
                                        String eTime = myAppt.apptEndTime;
                                        String loc = myAppt.apptLoc;
                                        String rea = myAppt.apptReason;
                                        apptStartTimeCombo2.setValue(sTime);
                                        apptEndTimeCombo2.setValue(eTime);
                                        apptLocTxtFld2.setText(loc);
                                        apptReasonTxtFld2.setText(rea);
                                        
                                        updateAppointmentBttn.setOnAction(ax -> 
                                        {
                                            try {
                                                String apptStartTime= apptStartTimeCombo2.getSelectionModel().getSelectedItem().toString();
                                                String apptEndTime= apptEndTimeCombo2.getSelectionModel().getSelectedItem().toString();
                                                //This needs to be changed to reflect the currentlu logged studentid
                                                String update = ("UPDATE `test`.`appointments` SET `apptDate`='" + myAppt.apptDate + "', `apptStartTime`='" + apptStartTime + "', `apptEndTime`='" + apptEndTime + "', `apptLoc`='" + apptLocTxtFld2.getText() + "', `apptReason`='" + apptReasonTxtFld2.getText()+ "' WHERE `appointmentId`='" + myAppt.appointmentId + "'");
                                                myStat.executeUpdate(update);
                                                TableView<Appointment> apptTable2 = viewAppointments(sql);
                                                myBorderPane.setCenter(apptTable2);
                                            }
                                            catch (SQLException ex) {
                                                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        });
                                        cancelBttn.setOnAction(as -> {
                                        apptStage.close();
                                        });
                                    });
                                        apptStage.setScene(apptScene);
                                        apptStage.show();
                                });
                                this.setOnMouseEntered(a -> {
                                this.setScaleX(1.1);
                                this.setScaleY(1.1);
                                });
                                this.setOnMouseExited(a -> {
                                this.setScaleX(1);
                                this.setScaleY(1);
                                });
                                //Disable all dates before and after semester start and end
                                if(item.isAfter(semesterEnd)){
                                this.setDisable(true);
                                    }
                                if(item.isBefore(semesterStart)){
                                this.setDisable(true);
                                    }
                                int sId = loggedInStudent.getStudentId();
                                String sql ="SELECT * FROM test.appointments WHERE studentId = "+ sId + ";";
                                ObservableList<Appointment> myAppts = FXCollections.observableArrayList();
                                    try 
                                    {
                                    myAppts = readAppointments(sql);
                                    } 
                                    catch (SQLException ex) 
                                    {
                                    Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                for(Appointment myAppt : myAppts)
                                {
                                 String date1 = myAppt.apptDate;
                                 String date = item.toString();
                                    if(date.equals(date1))
                                       { 
                                        ImageView myView = new ImageView(imageStar);
                                        HBox myBox = new HBox(2);
                                        Label myLbl = new Label("asj");
                                        myBox.setAlignment(Pos.TOP_LEFT);
                                        myBox.getChildren().addAll(myView,myLbl);
                                        setGraphic(myBox);
                                        //this.setGraphic(myView);
                                        setTooltip(new Tooltip(
                                         "You have appointments today"));
                                        setStyle("-fx-background-color: #ac521a;");
                                       }
                                 }
                                this.setContentDisplay(ContentDisplay.TOP);
                                DayOfWeek day = DayOfWeek.from(item);
                                if (day == DayOfWeek.SATURDAY||day == DayOfWeek.SUNDAY)
                                   {
                                  this.setTextFill(Color.BLUE);
                                  setStyle("-fx-background-color: #EEEEEE;");
                                    }
                                }
                            };
                        }
                    };
                 //Set custom cells to datePicker
       datePicker.setDayCellFactory(dayCellFactory1);
       DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
       Node popupContent = datePickerSkin.getPopupContent();
       borderPane.setCenter(popupContent);
       borderPane.setLeft(monthlyHbox);
       
        });
//------------------------------------------------------------------TESTING AREA-------------------Create courses and students
    primaryStage.setTitle("VaqPack");
    primaryStage.setScene(scene);
    }
 public static void main(String[] args) {
           launch(args);
    }
 public TableView viewAppointments(String m){
     ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            try {
                appointmentList = readAppointments(m);
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            TableView<Appointment> myTableView = new TableView<Appointment>();
            myTableView.setItems(appointmentList);
            TableColumn<Appointment,Integer> appointmentId= new TableColumn<>("appointmentId");
            appointmentId.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("appointmentId"));
	    TableColumn<Appointment,Integer> studentId= new TableColumn<>("studentId");
            studentId.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("studentId"));
            TableColumn<Appointment,String> apptDate = new TableColumn<>("apptDate");
	    apptDate.setCellValueFactory(new PropertyValueFactory<Appointment,String>("apptDate"));
	    TableColumn<Appointment,String> apptStartTime = new TableColumn<>("apptStartTime");
	    apptStartTime.setCellValueFactory(new PropertyValueFactory<Appointment,String>("apptStartTime"));
	    TableColumn<Appointment,String> apptEndTime = new TableColumn<>("apptEndTime");
	    apptEndTime.setCellValueFactory(new PropertyValueFactory<Appointment,String>("apptEndTime"));
	    TableColumn<Appointment,String> apptLoc = new TableColumn<>("apptLoc");
	    apptLoc.setCellValueFactory(new PropertyValueFactory<Appointment,String>("apptLoc"));
	    TableColumn<Appointment,String> apptReason = new TableColumn<>("apptReason");
	    apptReason.setCellValueFactory(new PropertyValueFactory<Appointment,String>("apptReason"));
            myTableView.getColumns().setAll(appointmentId,studentId,apptDate,apptStartTime,apptEndTime,apptLoc,apptReason);
           return myTableView;
 }
 public void updateRecords(String m)
    {
    try {
            myStat.executeUpdate(m);
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
 public ObservableList readAppointments(String m) throws SQLException
    {
        String sql = "SELECT * FROM `test`.`appointments`";
        myRes = myStat.executeQuery(m);
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        while(myRes.next())
        {
            int appointmentId = myRes.getInt("appointmentId");
            int studentId = myRes.getInt("studentId");
            String apptDate = myRes.getString("apptDate");
            String startTime = myRes.getString("apptStartTime");
            String endTime = myRes.getString("apptEndTime");
            String apptLoc = myRes.getString("apptLoc");
            String apptReason = myRes.getString("apptReason");
            Appointment myAppointment = new Appointment(appointmentId,studentId,apptDate,startTime,endTime,apptLoc,apptReason);
            appointmentList.add(myAppointment);
        }
        return appointmentList;
    }

 public Student getStudent(ObservableList<Student> list,String m)
    {
        for(Student myStudent :list)
        {
            if(myStudent.getUserName().equals(m))
                return myStudent;
        }
        return null;
    }
 public TableView viewStudents(String m){
            ObservableList<Student> StudentList = FXCollections.observableArrayList();
            TableView<Student> myTableView = new TableView<Student>();
            try {
                StudentList = readStudents(m);
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            myTableView.setItems(StudentList);
            TableColumn<Student,Integer> studentId= new TableColumn<>("studentId");
            studentId.setCellValueFactory(new PropertyValueFactory<Student,Integer>("StudentId"));
            TableColumn<Student,String> userName = new TableColumn<>("userName");
	    userName.setCellValueFactory(new PropertyValueFactory<Student,String>("userName"));
	    TableColumn<Student,String> passWord = new TableColumn<>("passWord");
	    passWord.setCellValueFactory(new PropertyValueFactory<Student,String>("passWord"));
	    TableColumn<Student,String> firstName = new TableColumn<>("firstName");
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            TableColumn<Student,String> middleName = new TableColumn<>("middleName");
            middleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
            TableColumn<Student,String> lastName = new TableColumn<>("lastName");
            lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            TableColumn<Student,String> emailAddress = new TableColumn<>("emailAddress");
            emailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
            TableColumn<Student,String> phoneNumber = new TableColumn<>("phoneNumber");
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            myTableView.getColumns().setAll(studentId,userName,passWord,firstName,middleName,lastName,emailAddress,phoneNumber);
            return myTableView;
 }
 public ObservableList readStudents(String m) throws SQLException
    {
        String sql = "SELECT * FROM `test`.`students`";
        myRes = myStat.executeQuery(m);
        ObservableList<Student> StudentList = FXCollections.observableArrayList();
        while(myRes.next())
        {
	    int studentId = myRes.getInt("studentId");
	    String userName = myRes.getString("userName");
	    String passWord = myRes.getString("passWord");
            String firstName = myRes.getString("firstName");
            String middleName = myRes.getString("middleName");
            String lastName = myRes.getString("lastName");
            String emailAddress = myRes.getString("emailAddress"); 
            String phoneNumber = myRes.getString("phoneNumber");
            int privLevel = myRes.getInt("privLevel");
            Student myStudent = new Student(studentId,userName,passWord,firstName,middleName,lastName,emailAddress,phoneNumber,privLevel);
            StudentList.add(myStudent);
        }
        return StudentList;
    }
public TableView viewCourses(String m){
            ObservableList<Course> CourseList = FXCollections.observableArrayList();
            TableView<Course> myTableView = new TableView<Course>();
            try {
                CourseList = readCourses(m);
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            myTableView.setItems(CourseList);
            TableColumn<Course,Integer> courseId= new TableColumn<>("courseId");
            courseId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("courseId"));
            TableColumn<Course,String> courseName = new TableColumn<>("courseName");
	    courseName.setCellValueFactory(new PropertyValueFactory<Course,String>("courseName"));
            TableColumn<Course,String> classRoom = new TableColumn<>("classRoom");
	    classRoom.setCellValueFactory(new PropertyValueFactory<Course,String>("classRoom"));
            TableColumn<Course,String> startDate = new TableColumn<>("startDate");
            startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            TableColumn<Course,String> startTime = new TableColumn<>("startTime");
            startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            TableColumn<Course,String> endTime = new TableColumn<>("endTime");
            endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            TableColumn<Course,String> courseDesc = new TableColumn<>("courseDesc");
            courseDesc.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
            TableColumn<Course,Integer> profId= new TableColumn<>("profId");
            profId.setCellValueFactory(new PropertyValueFactory<Course,Integer>("profId"));
            myTableView.getColumns().setAll(courseId,courseName,classRoom,startDate,startTime,endTime,courseDesc,profId);
            return myTableView;
  }
public TableView viewRegCourses(String m){
            ObservableList<Registered> registeredList = FXCollections.observableArrayList();
            TableView<Registered> myTableView = new TableView<Registered>();
            try {
                registeredList = readRegCourses(m);
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            myTableView.setItems(registeredList);
            TableColumn<Registered,Integer> regId= new TableColumn<>("regId");
            regId.setCellValueFactory(new PropertyValueFactory<Registered,Integer>("courseId"));
            TableColumn<Registered,Integer> courseId= new TableColumn<>("courseId");
            courseId.setCellValueFactory(new PropertyValueFactory<Registered,Integer>("courseId"));
            TableColumn<Registered,Integer> studentId= new TableColumn<>("studentId");
            regId.setCellValueFactory(new PropertyValueFactory<Registered,Integer>("studentId"));
            myTableView.getColumns().setAll(regId,courseId,studentId);
            return myTableView;
  }
  public ObservableList readRegCourses(String m) throws SQLException
    {
        myRes = myStat.executeQuery(m);
        ObservableList<Registered> registeredList = FXCollections.observableArrayList();
        while(myRes.next())
        {
	    int regId = myRes.getInt("regId");
            int courseId = myRes.getInt("courseId");
	    int studentId = myRes.getInt("studentId");
            Registered myRegistered = new Registered(regId,courseId,studentId);
            registeredList.add(myRegistered);
        }
        return registeredList;
    }
 public ObservableList readCourses(String m) throws SQLException
    {
        String sql = "SELECT * FROM `test`.`students`";
        myRes = myStat.executeQuery(m);
        ObservableList<Course> CourseList = FXCollections.observableArrayList();
        while(myRes.next())
        {
	    int courseId = myRes.getInt("courseId");
	    String courseName = myRes.getString("courseName");
	    String classRoom = myRes.getString("classRoom");
            String startDate = myRes.getString("startDate");
            String startTime = myRes.getString("startTime");
            String endTime = myRes.getString("endTime");
            String courseDesc = myRes.getString("courseDesc"); 
            int profId = myRes.getInt("profId");
            Course myCourse = new Course(courseId,courseName,classRoom,startDate,startTime,endTime,courseDesc,profId);
            CourseList.add(myCourse);
        }
        return CourseList;
    }

public void unregisterCourse(Course course) throws SQLException
{
    String sql = ("SELECT * FROM test.appointments WHERE studentId = '" +loggedInStudent.getStudentId() + "' AND apptStartTime = '" + course.getStartTime() + "' AND apptEndTime = '" + course.getEndTime() + "' AND apptLoc = '" + course.getClassRoom()  +"';");
    ObservableList<Appointment> apptList = readAppointments(sql);
    
    for (int i = 0; i < apptList.size(); i++) 
    {
        String delete = "DELETE FROM `test`.`appointments` WHERE `appointmentId`='" + apptList.get(i).appointmentId + "';";
        updateRecords(delete);
    }
    
    String unreg = "DELETE FROM test.registeredstudents WHERE courseId=" + course.getCourseId()+ " AND studentId =" + loggedInStudent.getStudentId() + " ;";
    updateRecords(unreg);
} 
 
 
 
public void registerCourse(Course course)
{
LocalDate semesterStart = LocalDate.of(2016,1,15);
LocalDate semesterEnd = LocalDate.of(2016, 5, 16);
int numDays = (int)DAYS.between(semesterStart,semesterEnd);
ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();    
//for each course listed in the students course list
    String date = course.getStartDate();
    DateTimeFormatter formatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate myDate = LocalDate.parse(date,formatter);
    String regString = ("INSERT INTO `test`.`registeredstudents` (`courseId`, `studentId`) VALUES ('"+ course.getCourseId() + "', '"+ loggedInStudent.getStudentId() +"');");    
    updateRecords(regString);
    for (int j = 0; j <numDays/7 + 1; j++) 
        {
        Appointment myAppt = new Appointment(j,loggedInStudent.getStudentId(),myDate.toString(),course.getStartTime(),course.getEndTime(),course.getClassRoom(),"Class: "+ course.getCourseName());
        appointmentList.add(myAppt);
        myDate = myDate.plusDays(7);
        }
    for (int i = 0; i < appointmentList.size(); i++) {
       String sql = ("INSERT INTO `test`.`appointments` (`studentId`, `apptDate`, `apptStartTime`, `apptEndTime`, `apptLoc`, `apptReason`) VALUES ('" + loggedInStudent.getStudentId() + "', '" + appointmentList.get(i).apptDate + "', '" + appointmentList.get(i).apptStartTime + "', '" + appointmentList.get(i).apptEndTime + "', '" +appointmentList.get(i).apptLoc+ "', '" + appointmentList.get(i).apptReason + "');");
       updateRecords(sql);
    }
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
        ObservableList<Appointment> myApptList = FXCollections.observableArrayList() ;
        ObservableList<Appointment> fiveDayList = FXCollections.observableArrayList() ;
        String sql = "SELECT * FROM test.appointments WHERE studentId =" + loggedInStudent.getStudentId()+"";
        try {
            myApptList = readAppointments(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Label timeLbl = new Label("Time");
        LocalDate today = LocalDate.now();
        String todayDay = today.getDayOfWeek().toString();
        Label todayDayLbl = new Label(todayDay);
        LocalDate oneFromToday = today.plusDays(1);
        String oneFromTodayDay = oneFromToday.getDayOfWeek().toString();
        Label oneFromTodayDayLbl = new Label(oneFromTodayDay);
        LocalDate twoFromToday = today.plusDays(2);
        String twoFromTodayDay = twoFromToday.getDayOfWeek().toString();
        Label twoFromTodayDayLbl = new Label(twoFromTodayDay);
        LocalDate threeFromToday = today.plusDays(3);
        String threeFromTodayDay = threeFromToday.getDayOfWeek().toString();
        Label threeFromTodayDayLbl = new Label(threeFromTodayDay);
        LocalDate fourFromToday = today.plusDays(4);
        String fourFromTodayDay = fourFromToday.getDayOfWeek().toString();
        Label fourFromTodayDayLbl = new Label(fourFromTodayDay);
        for (int i = 0; i < myApptList.size(); i++)
        {
        String date = myApptList.get(i).apptDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate myDate = LocalDate.parse(date,formatter);
           if (myDate.equals(today)|| myDate.isAfter(today) && myDate.isBefore(fourFromToday) || myDate.equals(fourFromToday) && myApptList.get(i).getApptReason().startsWith("Class")) 
                {
                fiveDayList.add(myApptList.get(i));
                }
        }
       grid.addColumn(0, timeLbl);
       grid.addColumn(1, todayDayLbl); 
       grid.addColumn(2, oneFromTodayDayLbl); 
       grid.addColumn(3, twoFromTodayDayLbl);  
       grid.addColumn(4, threeFromTodayDayLbl);  
       grid.addColumn(5, fourFromTodayDayLbl);  
       for (int i = 0; i < fiveDayList.size(); i++) 
       {
           String locStr = " "+fiveDayList.get(i).apptLoc + " " + fiveDayList.get(i).apptReason;
           String startTime = fiveDayList.get(i).apptStartTime;
           String endTime = fiveDayList.get(i).apptEndTime;
           String date = fiveDayList.get(i).apptDate;
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           LocalDate myDate = LocalDate.parse(date,formatter);
           String myDateDay = myDate.getDayOfWeek().toString();
           int startTimeIndex = getIndex(timeIntervals,startTime);
           int endTimeIndex = getIndex(timeIntervals,endTime);
           int count = endTimeIndex - startTimeIndex;
        if(startTimeIndex > 0 && endTimeIndex > 0)
        {
           if (myDateDay.equals(todayDay)) 
           {
                for (int j = startTimeIndex; j <= endTimeIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 1, j);
                }
            }
           if (myDateDay.equals(oneFromTodayDay)) 
           {
                 for (int j = startTimeIndex; j <= endTimeIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 2, j);
                }
            }
           if (myDateDay.equals(twoFromTodayDay)) 
           {
                 for (int j = startTimeIndex; j <= endTimeIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 3, j);
                }
            }
            if (myDateDay.equals(threeFromTodayDay)) 
           {
               Label  locLbl = new Label(locStr);
                 for (int j = startTimeIndex; j <= endTimeIndex; j++)
                {
                    grid.add(locLbl, 4, j);
                }
            }
           if (myDateDay.equals(fourFromTodayDay)) 
           {
                 for (int j = startTimeIndex; j <= endTimeIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 5, j);
                }
            }
       }
       else
       {
       String[] splitStartTime = sSplitter(startTime);
       String[] splitEndTime = sSplitter(endTime);
       int startTimeFuzzyIndex = getFuzzyIndex(splitStartTime,timeIntervals,time);
       int endTimeFuzzyIndex = getFuzzyIndex(splitEndTime,timeIntervals,time);
       if(startTimeFuzzyIndex > 0 && endTimeFuzzyIndex > 0)
        {
           if (myDateDay.equals(todayDay)) 
           {
                for (int j = startTimeFuzzyIndex; j <= endTimeFuzzyIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 1, j);
                }
            }
           if (myDateDay.equals(oneFromTodayDay)) 
           {
                for (int j = startTimeFuzzyIndex; j <= endTimeFuzzyIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 2, j);                }
            }
           if (myDateDay.equals(twoFromTodayDay)) 
           {
                for (int j = startTimeFuzzyIndex; j <= endTimeFuzzyIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 3, j);
                }
            }
           if (myDateDay.equals(threeFromTodayDay)) 
           {
                for (int j = startTimeFuzzyIndex; j <= endTimeFuzzyIndex; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 4, j);
                }
            }
           if (myDateDay.equals(fourFromTodayDay)) 
           {
                for (int j = 0; j < count; j++)
                {
                    Label  locLbl = new Label(locStr);
                    grid.add(locLbl, 5, j);
                }
            }
         }
      }
   }
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
   public int getIndex(String arr[],String s)
    {
      for(int i = 0; i < arr.length; i++){
      if(arr[i].equals(s))
      return i+1;
        }
      return -1;
    }
   public int getFuzzyIndex (String splitArray[],String intervalArray[],int time)
{
    TimeTicks timeTicks = new TimeTicks(time);
    timeTicks.generateTicks();
    intervalArray= timeTicks.getTimeTicksStrings();
    int index=-1;
    for(int i = 0;i<intervalArray.length;i++)
    {
       if (intervalArray[i].startsWith(splitArray[0]) && intervalArray[i].endsWith(splitArray[2]) )
       {        
         index = i+1;
       }
   }
return index;
}

public String[] sSplitter(String s)
         
{   String hour;
    String minute;
    String ampm;
    String[] finalArray = new String[3];
    String criteria = ":";
    String criteria2 = " ";
     //splits at the :
    String[] splitArray = s.split(criteria,2);
    hour = splitArray[0];
    minute = splitArray[1];
    //splits minute from 15 AM to {"15", "AM"}
    String[] splitArray2 = minute.split(criteria2,2);
     minute = splitArray2[0];
     ampm = splitArray2[1];
     finalArray[0] = hour;
     finalArray[1] = minute;
     finalArray[2] = ampm;
          
  return finalArray;       
}  
  
 }
