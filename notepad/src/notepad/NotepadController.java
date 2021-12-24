package notepad;

import javafx.scene.Parent;

public class NotepadController {
	private Parent form;
	private iFileServ fileServ;
	private String filePath;

	public NotepadController() {
		fileServ = new NotepadService();
	}

	public void setForm(Parent form) {
		this.form = form;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void OnSave() {
		if (filePath == null) {
			filePath = fileServ.SaveWindow();
		}
		
//		System.out.println(filePath);
		fileServ.SaveFile(form, filePath);
	}


}
