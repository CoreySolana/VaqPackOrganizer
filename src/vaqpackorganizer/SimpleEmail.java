/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpackorganizer;

import java.util.Properties;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Alfonso 2
 */
public class SimpleEmail extends Application{
    private String to_Text;
    private String subject_Text;
    private String msg_Text;
    
     @Override
    public void start(Stage primaryStage) throws Exception {
        Stage loginStage = new Stage();
        GridPane emailGrid = new GridPane();
        Text to = new Text("To:");
        Text subject = new Text("Subject:");
        TextField toInput = new TextField();
        TextField subjectInput = new TextField();
        Button clearButton = new Button("Clear");
         Button submitButton = new Button("Submit");
         VBox horizontal = new VBox();
         HBox buttonPane = new HBox();
         TextArea message = new TextArea();
         
        //emailGrid.add(to, columnIndex, rowIndex);
         emailGrid.add(to, 0, 0);
         emailGrid.add(toInput, 1, 0);
         emailGrid.add(subject, 0, 1);
         emailGrid.add(subjectInput, 1, 1);
         
         buttonPane.getChildren().addAll(clearButton,submitButton );
         buttonPane.alignmentProperty();
         
         horizontal.getChildren().addAll(emailGrid,message, buttonPane );
         
         
         submitButton.setOnAction((event) -> {
         System.out.println("Button Action");
         SimpleEmail se = new SimpleEmail();
         se.setTo_Text(toInput.getText());
         se.setSubject_Text(subjectInput.getText());
         se.setMsg_Text(message.getText());
         se.Email();
         
         
            
           //  System.out.println("email sent");
             System.out.println("send to: " + toInput.getText() );
             System.out.println("Subject: " +subjectInput.getText() );
             System.out.println("Messsage: " + message.getText());
        });
         
          clearButton.setOnAction((event) -> {
        
              
         toInput.setText("");
         subjectInput.setText("");
         message.setText("");
         
            
          
        });
         
          
        
       // BorderPane loginBorderPane = new BorderPane();
        Scene emailScene = new Scene(horizontal,400,400);
        
        loginStage.setScene(emailScene);
        loginStage.show();
        
       
    }
    
    
    
    public void Email()
    {
        // Recipient's email ID needs to be mentioned.
      //String to = "";//change accordingly
        String to = getTo_Text();//change accordingly

      // Sender's email ID needs to be mentioned
      String from = "abc@gmail.com";//change accordingly
      final String username = "VackPackApp11@gmail.com";//change accordingly
      final String password = "Time1234";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");

      // Get the Session object.
      Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse( to));

         // Set Subject: header field
         //message.setSubject("Testing Subject");
          message.setSubject(getSubject_Text());

         // Now set the actual message
        // message.setText("Hello, this is sample for to check send " + "email using JavaMailAPI ");
           message.setText(getMsg_Text());
           
         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
    }

    /**
     * @return the to_Text
     */
    public String getTo_Text() {
        return to_Text;
    }

    /**
     * @param to_Text the to_Text to set
     */
    public void setTo_Text(String to_Text) {
        this.to_Text = to_Text;
    }

    /**
     * @return the subject_Text
     */
    public String getSubject_Text() {
        return subject_Text;
    }

    /**
     * @param subject_Text the subject_Text to set
     */
    public void setSubject_Text(String subject_Text) {
        this.subject_Text = subject_Text;
    }

    /**
     * @return the msg_Text
     */
    public String getMsg_Text() {
        return msg_Text;
    }

    /**
     * @param msg_Text the msg_Text to set
     */
    public void setMsg_Text(String msg_Text) {
        this.msg_Text = msg_Text;
    }

   
    
}
