package Common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class Common {
	public static void Notice(String content) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setAlertType(AlertType.WARNING);
		alert.setContentText(content);
		
		alert.show();
	}
	
	public static void StoreMenu(TextArea txtArea, Menu recent, List<String> pathLst) {
		// manubar 핸들(제어권) 얻기
//		MenuBar menuBar = (MenuBar) form.lookup("menuBar");
//		getMenus는 메뉴바의 메뉴들을 얻을 수 있다.
//		get을 이용하면 각 위치의 정보들을 얻으며 0번부터 시작한다.
//		Menu recent = menuBar.getMenus().get(1);
//		기존에 가지고 있는 메뉴를 지움 
		recent.getItems().clear();
		
		for(String path : pathLst) {
			MenuItem item = new MenuItem(path);
			item.setOnAction(e->{
				ItemAction(txtArea, path);
			});
			recent.getItems().add(item);
		}	
	}
	
	public static void ItemAction(TextArea txtArea, String path) {
		File file = new File(path);
//		exists() 현재 파일이 경로에 존재하는지 확인
//		System.out.println(file.exists());
		
		if(file.exists()) {
//			System.out.println("존재함.");
//			파일에서 문자열 읽어오기
			String contents = getText(file);
			txtArea.setText(contents);
		} else {
//			System.out.println("파일이 없어요!");
			Notice("파일이 읎어요!");
		}
	}
	
	public static void ItemAction(Parent form, String path) {
		File file = new File(path);
//		exists() 현재 파일이 경로에 존재하는지 확인
//		System.out.println(file.exists());
		
		if(file.exists()) {
//			System.out.println("존재함.");
//			파일에서 문자열 읽어오기
			String contents = getText(file);
			System.out.println(contents);
			setTextArea(form, contents);
		} else {
//			System.out.println("파일이 없어요!");
			Notice("파일이 읎어요!");
		}
	}
	
	public static void setTextArea(Parent form, String contents) {
		TextArea txtArea = (TextArea) form.lookup("#txtArea");
		txtArea.setText(contents);
	}
	
	public static String getText(File file) {
		String txt="";
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String line;
//			readLine bufferedReader 에서 데이터를 한 줄씩 읽어 들임
//			데이터가 존재하지 않는 경우 null 반환
			while( (line=br.readLine())!= null) {
				txt += (line + "\n");
			} 
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return txt;
	}

	public static void StoreMenu(Parent form, List<String> pathLst) {
//		 manubar 핸들(제어권) 얻기
		MenuBar menuBar = (MenuBar) form.lookup("menuBar");
//		getMenus는 메뉴바의 메뉴들을 얻을 수 있다.
//		get을 이용하면 각 위치의 정보들을 얻으며 0번부터 시작한다.
		Menu recent = menuBar.getMenus().get(1);
//		기존에 가지고 있는 메뉴를 지움 
		recent.getItems().clear();
		
		for(String path : pathLst) {
			MenuItem item = new MenuItem(path);
			item.setOnAction(e->{
				ItemAction(form, path);
			});
			recent.getItems().add(item);
		}	
	}
	
}
