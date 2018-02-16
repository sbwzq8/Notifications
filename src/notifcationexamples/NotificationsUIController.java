/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @FXML Button task1Button;
    @FXML Button task2Button;
    @FXML Button task3Button;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        if (task1 == null) {
            System.out.println("start task 1");
            task1Button.setText("End Task 1");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        }
        else{
            System.out.println("end task 1");
            task1Button.setText("Start Task 1");
            task1.end();
            task1 = null;
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            System.out.println("end task 1");
            task1 = null;
            task1Button.setText("Start Task 1");
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        if (task2 == null) {
            System.out.println("start task 2");
            task2Button.setText("End Task 2");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if(message.equals("Task2 done.")){
                    System.out.println("end task 2");
                    task2Button.setText("Start Task 2");
                    task2 = null;
                }
            });
            
            task2.start();
        }        
        else{
            System.out.println("end task 2");
            task2Button.setText("Start Task 2");
            task2.end();
            task2 = null;
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        if (task3 == null) {
            System.out.println("start task 3");
            task3Button.setText("End Task 3");
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                if(evt.getNewValue().equals("Task3 done.")){
                    System.out.println("end task 3");
                    task3Button.setText("Start Task 3");
                    task3 = null;
                }
            });
            
            task3.start();
        }
        else{
            System.out.println("end task 3");
            task3Button.setText("Start Task 3");
            task3.end();
            task3 = null;
        }
    } 
}
