package notepad;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Common.Common;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;

public class NotepadController implements Initializable {
	private Parent form;
	private iFileServ fileServ;
	private String filePath;

	@FXML private Menu recent;
	@FXML private TextArea txtArea;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		List<String> pathLst = fileServ.select();
//		�̷��� ���� fxml ��ü�� �̾Ƽ� �ִ� ������
//		form�� �ʱ�ȭ ���Ŀ� ����� ſ��
//		������ storeMenu�� �۵����� �ʱ� �����Դϴ�.
		Common.StoreMenu(txtArea, recent, pathLst);
	}
	
	public NotepadController() {
		fileServ = new FileServiceImpl();
	}

	public void setForm(Parent form) {
		this.form = form;
	}
	
	public void OnSave() {
		if (filePath == null) 
			filePath = fileServ.SaveWindow();
		saveFile();
	}
	
	public void OnSaveAs() {
		filePath = fileServ.SaveWindow();
		saveFile();
	}

	private void saveFile() {
		fileServ.SaveFile(form, filePath);
		fileServ.Insert(form, filePath);
		fileServ.LoadRecent(form);
	}
}
