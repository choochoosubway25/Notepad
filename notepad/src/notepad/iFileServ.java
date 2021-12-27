package notepad;

import java.util.List;

import javafx.scene.Parent;

public interface iFileServ {
	public String SaveWindow();
	public void SaveFile(Parent form, String filePath);
	public void Insert(Parent form, String filePath);
	public void LoadRecent(Parent form);
	public List<String> select();
	public boolean IsChange(Parent form);
}
