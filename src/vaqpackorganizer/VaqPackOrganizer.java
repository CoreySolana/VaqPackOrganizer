/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpackorganizer;


import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    ToolBar toolBar; 
    ColumnConstraints column;      
    RowConstraints row;      
    GridPane grid;
    ObjectProperty<Font> fontTracking;
    Student loggedInStudent;
    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
//Setups Connection to database
        myConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test","root","rootb1"); /////
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
            try {
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
        ToggleButton userManagerBttn = new ToggleButton("User Management", new ImageView(imageDecline));
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
         });
        
 //-------------------------------------------------------MONTH VIEW-----------------------------------------------------------------------------------------
        monthlyScheduleBttn.setOnAction((ae) -> {
        
        borderPane.setBottom(newCourseBttn);
        HBox monthlyHbox = new HBox();
        HBox monthlyHbox2 = new HBox();
        monthlyHbox.prefHeightProperty().bind(toolBar.prefHeightProperty());
        Button newApptNoDateBttn = new Button("Create Appointment");
        
        
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
        
        Button apptBttn1 = new Button("Create Appointment");
        Button emailBtn = new Button("Send Email");
        //monthlyHbox.getChildren().add(apptBttn);
       // monthlyHbox2.getChildren().add(apptBttn1);
        monthlyHbox2.getChildren().addAll(apptBttn1,emailBtn);
        
        //email
         emailBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                SimpleEmail ee = new SimpleEmail();
                try {
                    Stage estage = new Stage();
                    
                    ee.start(estage);
                    
                    
                    
                    
                    
                    
                    //System.out.println("Hello World!");
                } catch (Exception ex) {
                    Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
        });
        
        
 //This sets up the date for the semester for labeling later
       LocalDate semesterStart = LocalDate.of(2016,1,15);
       LocalDate semesterEnd = LocalDate.of(2016, 5, 16);
       
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
                                        myBorderPane.setPadding(new Insets(11, 12, 13, 14));
                                        Button createApptBttn = new Button("Create Appt.");
                                        Button cancelBttn = new Button ("Cancel");
                                        Button okBttn = new Button("Create Appt.");
                                        String sql = "SELECT * FROM `test`.`appointments` WHERE studentId = '" + loggedInStudent.getStudentId() + "' AND apptDate = '" + apptDate +"';";
                                        TableView<Appointment> apptTable = viewAppointments(sql);
                                        VBox myVB = new VBox(10);
                                        myVB.getChildren().addAll(startTime,apptStartTimeCombo,endTime,apptEndTimeCombo,apptLocLbl,apptLocTxtFld,apptReasonLbl,apptReasonTxtFld,okBttn, cancelBttn);    
                                        
                                        myBorderPane.setLeft(createApptBttn);
                                        myBorderPane.setCenter(apptTable);
                                        
                                        createApptBttn.setOnAction(ad -> {
                                        myBorderPane.setLeft(myVB);
                                        });
                                        
                                        apptTable.setOnMouseClicked(aq -> {
                                        Appointment myAppt = apptTable.getSelectionModel().getSelectedItem();
                                        myBorderPane.setLeft(myVB);
                                        String sTime = myAppt.apptStartTime;
                                        String eTime = myAppt.apptEndTime;
                                        String loc = myAppt.apptLoc;
                                        String rea = myAppt.apptReason;
                                        
                                        apptStartTimeCombo.setValue(sTime);
                                        apptEndTimeCombo.setValue(eTime);
                                        apptLocTxtFld.setText(loc);
                                            apptReasonTxtFld.setText(rea);
                                        });
                                        
                                        okBttn.setOnAction(ax -> {
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
                                        
                                        cancelBttn.setOnAction(as -> {
                                        apptStage.close();
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
                                
                                try {
                                myAppts = readAppointments(sql);
                                 } 
                                catch (SQLException ex) {
                                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                
                                for(Appointment myAppt : myAppts){
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
       borderPane.setRight(monthlyHbox2);
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
        FlowPane myFlowPane = new FlowPane();
        myFlowPane.getChildren().addAll(courseSplash,courseNameLbl,courseNameTxt,courseClassLbl,courseClassTxt,courseStartDateLbl,startDatePicker,courseStartTimeLbl,apptStartTimeCombo,courseEndTimeLbl,apptEndTimeCombo,courseDescriptionLbl,courseDescriptionTxt,courseSubmit);
        borderPane.setRight(myFlowPane);
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
       
        //------------------------------------------------------------------TESTING AREA-------------------Create courses and students
    primaryStage.setTitle("VaqPack");
    primaryStage.setScene(scene);
    }
 public static void main(String[] args) {
           launch(args);
    }

 
 public TableView viewAppointments(String m){
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            TableView<Appointment> myTableView = new TableView<Appointment>();
            try {
                appointmentList = readAppointments(m);
            } catch (SQLException ex) {
                Logger.getLogger(VaqPackOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
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
 
 }

