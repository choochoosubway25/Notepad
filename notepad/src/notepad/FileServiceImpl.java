package notepad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import Common.Common;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileServiceImpl implements iFileServ{
	private IFileDao iFileDao;
	private int oldHash;
	public FileServiceImpl() {
		iFileDao = new FileDaoImpl();
	}
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

//		���� ���� �� null�� ���� ���� ó��
		String filePath = "";
		
		try {
//			������ ��θ� ����
			filePath = file.getPath();
//			���� file�� Null �� ���, catch �κ� ����
		} catch (NullPointerException e) {
//			�Ʒ� �ڵ�� Mac ȣȯ�� ������ ���� ���� filePath�� �ӽ������� �����ϴ� �ڵ��Դϴ�.
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
	
	@Override
	public void Insert(Parent form, String filePath) {
		String contents = getText(form);
//		TextArea�� ��ȭ�� ������ �ִ��� Ȯ�� 
		int hash = getHash(contents);
//		���� �����Ϳ� ���� �������� �ؽð��� ���մϴ�.
//		���� ������ ��ȭ�� ���� ������ ����, insert�޼ҵ带 �����մϴ�.
		if(oldHash == hash) return;
		
//		�Ʒ� �ڵ� ���ʹ� �ؽ� ���� ���� ���� -> ���뿡 ��ȭ�� ����
//		�� �����̹Ƿ� �̷� ��Ȳ�� ó���ϴ� �ڵ��Դϴ�.
		oldHash = hash;
//		��ο� ���� key, value ���� ����ϴ�.
//		�켱������ key �������ε� ������� �ߺ��� ��������,
//		��ΰ��� �߿䵵�� ũ�Ƿ� 
//		��θ� ������ ���� value�� ���� ���� ������
//		�ߺ��� ��õ�����մϴ�.
		String key = String.valueOf(getHash(filePath));
		String value = filePath;
		iFileDao.Insert(key, value);
	}
	
	private int getHash(String contents) {
		return contents.hashCode();
	}
	
	@Override
	public void LoadRecent(Parent form) {
//		�� �޼ҵ�� �ֱٿ� ������ ���� ��θ� �����ϴ� �޼ҵ��Դϴ�.
		List<String> pathLst = iFileDao.select();
		
//		for(String path : pathLst)
//			System.out.println("serv : " + path);
		Common.StoreMenu(form, pathLst);
	}
	@Override
	public List<String> select() {
		return iFileDao.select();
	}
	@Override
	public boolean IsChange(Parent form) {
		String contents = getText(form);
		int hash = getHash(contents);
		if(oldHash == hash) return false;
		
		oldHash = hash;
		return true;
	}
	

}
