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
//		������ ������ ��θ� ��� ���� ���� ���̾�α� ȣ�� �� ������ ��ġ ����
//		������ ����� file�� �����
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text files", "*.txt"),
				new ExtensionFilter("all files", "*,*")
				);
		File file = fileChooser.showSaveDialog(null);

		String filePath = "";
		
		try {
//			������ ��θ� ����
			filePath = file.getPath();
//			���� file�� Null �� ���, catch �κ� ����
		} catch (NullPointerException e) {
			filePath = getTmpDir() + "tmp.txt";
		}
		
//		������ ���� ��� ��ȯ
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
