package notepad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class NotepadService implements iFileServ{
	
	@Override
	public String SaveWindow(){
//		저장할 파일의 경로를 얻기 위해 저장 다이얼로그 호출 후 저장할 위치 지정
//		지정된 결과는 file에 저장됨
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text files", "*.txt"),
				new ExtensionFilter("all files", "*,*")
				);
		File file = fileChooser.showSaveDialog(null);

		String filePath = "";
		
		try {
//			파일의 경로를 얻어옴
			filePath = file.getPath();
//			만약 file이 Null 인 경우, catch 부분 실행
		} catch (NullPointerException e) {
			filePath = getTmpDir() + "tmp.txt";
		}
		
//		저장할 파일 경로 반환
		return filePath;
	}
	
	private String getTmpDir() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		return tmpDir;
	}


	@Override
	public void SaveFile(Parent form, String filePath) {
		String contents = getText(form);
		SaveFile(filePath, contents);
	}
	
	private void SaveFile(String filePath, String contents) {
		// TODO Auto-generated method stub
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(contents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getText(Parent form) {
		TextArea txtArea = (TextArea) form.lookup("#txtArea");
		return txtArea.getText();
	}
}
