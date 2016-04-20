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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Corey
 */
public class VaqPackOrganizer extends Application {
    
    public ArrayList<Student> students = new ArrayList<Student>();
    public ArrayList<Course> courses = new ArrayList<Course>();
    public ArrayList<Appointment> myAppointments = new ArrayList<Appointment>();
    Image imageDecline = new Image(getClass().getResourceAsStream("decline-button.png"));
    Image imageStar = new Image(getClass().getResourceAsStream("iconStarGold.png"));
    Image weekImg = new Image(getClass().getResourceAsStream("calendar_view_week.png"));
    ImageView weekImgView = new ImageView();
    Connection myConnection = null;
    Statement myStat = null; 
    ResultSet myRes = null;
    Button weeklyScheduleBttn ;
    Button monthlyScheduleBttn;
    Button schoolInfoBttn;
    ToolBar toolBar; 
    ColumnConstraints column;      
    RowConstraints row;      
    GridPane grid;
    ObjectProperty<Font> fontTracking;
    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
        
        //Setups DB connection
        Class.forName("com.mysql.jdbc.Driver"); 
        myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Pass!234");   
        myStat = myConnection.createStatement();
        String sql = "SELECT * FROM students";
        String firstName1 = "Roland";
        String middleName1 = "Tower";
        String  lastName1 = "DeChaine";
        String emailAddress1 = "DarkTower";
        String phoneNumber1 = "6666666";
        int privLevel1 = 1;
        String passWord1 = "Testies";
        String beg = "INSERT INTO `students` (`firstName`, `middleName`, `lastName`, `emailAddress`, `phoneNumber`, `privLevel`,`passWord`)";
        String createStudentSql = beg + "VALUES ('"+ firstName1+"'," + "'"+middleName1 +"',"+ "'"+lastName1+"'," + "'"+emailAddress1+"'," +"'"+phoneNumber1+"'," + "'"+privLevel1+"'," + "'"+passWord1+"')";
        
        myStat.executeUpdate(createStudentSql);
        myRes = myStat.executeQuery(sql);
      
        while(myRes.next())
        {
        //System.out.println("First name Is:" + "\t" +  myRes.getString("firstName"));
        }
      
        //Login Splash Page
        //---------------------------------LOGIN PAGE------------------------------------------------------------------
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
        Label messageLbl = new Label();
        loginGridPane.add(userNameLbl, 0, 0);
        loginGridPane.add(userNameTxt, 1, 0);
        loginGridPane.add(passwordLbl, 0, 1);
        loginGridPane.add(passFld, 1, 1);
        loginGridPane.add(loginBtn, 2, 1);
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
        loginBtn.setOnAction(ae -> {
            //To Do stuff here
           loginStage.close();
            primaryStage.show();
            
        });
        //-----------------------------END OF LOGIN PAGE------------------------------------------------------------------
        
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
        
 //Monthly Button
        monthlyScheduleBttn.setOnAction((ae) -> {
        borderPane.setBottom(null);
        HBox monthlyHbox = new HBox();
        HBox monthlyHbox2 = new HBox();
        monthlyHbox.prefHeightProperty().bind(toolBar.prefHeightProperty());
        Button apptBttn = new Button("Create Appointment");
        
        //ApptBttn listener
        {
        apptBttn.setOnAction(af -> {
          Stage apptStage = new Stage();
          FlowPane myFlowPane = new FlowPane();
          Scene apptScene = new Scene(myFlowPane,300,400);
          TimeTicks timeTicks = new TimeTicks(30);
          timeTicks.generateTicks();
          String[] timeIntervals= timeTicks.getTimeTicksStrings();
          ObservableList<String> list = FXCollections.observableArrayList(timeIntervals);
          Label startTime = new Label("Appointment Start Time");
          Label endTime = new Label("Appointment End Time");
          Label todaysEvents = new Label("Todays Schedule of Events");
          ComboBox apptStartTimeCombo = new ComboBox();
          ComboBox apptEndTimeCombo = new ComboBox();
          apptStartTimeCombo.setItems(list);
          apptEndTimeCombo.setItems(list);
          String apptStartTime;
          String apptEndTime;
          String apptLoc;
          String apptReason;
          Label apptDateLbl = new Label("Appt Date");
          Label apptLocLbl = new Label("Appt Location");
          Label apptReasonLbl = new Label("Appt Reason");
          TextField apptLocTxtFld = new TextField();
          TextField apptReasonTxtFld = new TextField();
          myFlowPane.setPadding(new Insets(11, 12, 13, 14));
          myFlowPane.setHgap(20);
          myFlowPane.setVgap(20);
          DatePicker myPicker = new DatePicker();
          
          Button cancelBttn = new Button ("Cancel");
          Button okBttn = new Button("Create Appt.");
          myFlowPane.getChildren().addAll(myPicker,new Separator(),startTime,apptStartTimeCombo,endTime,apptEndTimeCombo,apptLocLbl,apptLocTxtFld,apptReasonLbl,apptReasonTxtFld,okBttn, cancelBttn);    
          okBttn.setOnAction(ax -> {
             try {
             myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Pass!234");
             myStat = myConnection.createStatement();
             
             String createApptsql = "INSERT INTO `test`.`appointments` (`students_studentId`, `apptDate`, `apptStartTime`, `apptEndTime`, `apptLoc`, `apptReason`) VALUES ('1', '2012-12-2', '08:00 AM', '10:00 AM', 'Hells Kitchen', 'Fight Crime');";
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
        monthlyHbox.getChildren().add(apptBttn);
        monthlyHbox2.getChildren().add(apptBttn1);
        
 //This sets up the date for the semester for labeling later
       LocalDate semesterStart = LocalDate.of(2016,1,15);
       LocalDate semesterEnd = LocalDate.of(2016, 5, 16);
       long semesterRange = ChronoUnit.DAYS.between(semesterStart,semesterEnd);
       long semesterRangeMonths = ChronoUnit.MONTHS.between(semesterStart, semesterEnd);
//       
       int days =  (int) semesterRange;
       int[] dayOfMonthArray = new int[days];
       
       for (int i = 0;i<days;i++)
       {
           dayOfMonthArray[i] = semesterStart.plusDays(i).getDayOfMonth();
       }
       for (int i = 0;i<dayOfMonthArray.length;i++)
       {
       System.out.println(dayOfMonthArray[i]);
       }
        //DatePicker setup  
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
                                        FlowPane myFlowPane = new FlowPane();
                                        Scene apptScene = new Scene(myFlowPane,300,400);
                                        TimeTicks timeTicks = new TimeTicks(30);
                                        timeTicks.generateTicks();
                                        String[] timeIntervals= timeTicks.getTimeTicksStrings();
                                        ObservableList<String> list = FXCollections.observableArrayList(timeIntervals);
                                        Label startTime = new Label("Appointment Start Time");
                                        Label endTime = new Label("Appointment End Time");
                                        Label todaysEvents = new Label("Todays Schedule of Events");
                                       
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
                                        myFlowPane.setPadding(new Insets(11, 12, 13, 14));
                                        myFlowPane.setHgap(20);
                                        myFlowPane.setVgap(20);
                                        Button cancelBttn = new Button ("Cancel");
                                        Button okBttn = new Button("Create Appt.");
                                        TableView<Appointment> apptTable = viewAppointments();
                                        myFlowPane.getChildren().addAll(startTime,apptStartTimeCombo,endTime,apptEndTimeCombo,apptLocLbl,apptLocTxtFld,apptReasonLbl,apptReasonTxtFld,okBttn, cancelBttn,apptTable);    
                                        int stId = 1;
                                        okBttn.setOnAction(ax -> {
                                            try {
                                        
                                                String apptStartTime= apptStartTimeCombo.getSelectionModel().getSelectedItem().toString();
                                                String apptEndTime= apptEndTimeCombo.getSelectionModel().getSelectedItem().toString();
                                                //This needs to be changed to reflect the currentlu logged studentid
                                                String createApptsql = ("INSERT INTO `test`.`appointments` (`studentId`, `apptDate`, `apptStartTime`, `apptEndTime`, `apptLoc`, `apptReason`) VALUES ('" + stId + "', '" + apptDate + "', '" + apptStartTime + "', '" + apptEndTime + "', '" +apptLocTxtFld.getText()+ "', '" + apptReasonTxtFld.getText() + "');");
                                                myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Pass!234");
                                                myStat = myConnection.createStatement();
                                                myStat.executeUpdate(createApptsql);
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
                                
                                //Show Weekends in blue
                                /*if (myAppt.appointmentDate.isEqual(item))
                                {
                                 setTooltip(new Tooltip(myAppt.appointmentStartTime + "--" + myAppt.appointmentStartTime ));
                                  ImageView myView = new ImageView(imageStar)  ;
                                  myView.setFitHeight(10);
                                  myView.setFitWidth(10);
                                  
                                this.setGraphic(myView);
                                this.setContentDisplay(ContentDisplay.TOP);
                                }
                                */
                                
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
   //        myCourse = new Course(courseName + " " + i ,coursePrefix,courseNumber,classRoom,startTime,endTime,courseDesc,startIndex,endIndex,myProf,studentsRegistered);   
        //Add the course to the courses ArrayList
        //courses.add(myCourse);
    }
//This initializes a new student
 for (int i = 0;i<courseCount;i++)
 {
   //  myStudent = new Student(i + "" + firstName,middleName,LastName,passWord,userName,studentId,emailAddress,phoneNumber, privLvl,courseList,appointmentList);    
     //students.add(myStudent);
 }
 //Adds course 2 and 3 to student 1
// students.get(0).getCourseList().add(courses.get(1));
 //students.get(0).getCourseList().add(courses.get(2));
 
 
 //Add student 0 to courses registered students
 //courses.get(1).getStudentsRegistered().add(students.get(0));
 //courses.get(2).getStudentsRegistered().add(students.get(0));

// for (int i = 0;i<students.get(0).getCourseList().size();i++)
  //      System.out.print(students.get(0).getCourseList().get(i).getCourseName() + " " + " \n");
 
    primaryStage.setTitle("VaqPack");
    primaryStage.setScene(scene);

 
 
    }
 public static void main(String[] args) {
           launch(args);
    }

 
 public TableView viewAppointments(){
 
 
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            TableView<Appointment> myTableView = new TableView<Appointment>();
            try {
                appointmentList = readAppointments();
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
 
 
 public ObservableList readAppointments() throws SQLException
    {
        myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","Pass!234");   
        myStat = myConnection.createStatement();
        String sql = "SELECT * FROM `test`.`appointments`";
        myRes = myStat.executeQuery(sql);
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

