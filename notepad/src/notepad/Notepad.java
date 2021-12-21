package notepad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Notepad extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage s) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Notepad.fxml"));
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		
		s.setTitle("Notepad");
		s.setScene(scene);
		s.show();
		
	}
	

}
