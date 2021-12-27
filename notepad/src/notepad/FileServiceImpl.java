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
//		저장할 파일의 경로를 얻기 위해 저장 다이얼로그 호출 후 저장할 위치 지정
//		지정된 결과는 file에 저장됨
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Text files", "*.txt"),
				new ExtensionFilter("all files", "*,*")
				);
		File file = fileChooser.showSaveDialog(null);

//		파일 저장 시 null로 인한 예외 처리
		String filePath = "";
		
		try {
//			파일의 경로를 얻어옴
			filePath = file.getPath();
//			만약 file이 Null 인 경우, catch 부분 실행
		} catch (NullPointerException e) {
//			아래 코드는 Mac 호환성 문제를 막기 위해 filePath를 임시폴더로 지정하는 코드입니다.
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
	
	@Override
	public void Insert(Parent form, String filePath) {
		String contents = getText(form);
//		TextArea에 변화된 내용이 있는지 확인 
		int hash = getHash(contents);
//		이전 데이터와 현재 데이터의 해시값을 비교합니다.
//		둘이 같으면 변화가 없는 것으로 간주, insert메소드를 종료합니다.
		if(oldHash == hash) return;
		
//		아래 코드 부터는 해쉬 값이 같지 않음 -> 내용에 변화가 생김
//		인 상태이므로 이런 상황을 처리하는 코드입니다.
		oldHash = hash;
//		경로에 대한 key, value 값을 만듭니다.
//		우선적으로 key 값만으로도 어느정도 중복이 막히지만,
//		경로값은 중요도가 크므로 
//		경로를 값으로 같는 value의 값도 같이 저장해
//		중복을 원천봉쇄합니다.
		String key = String.valueOf(getHash(filePath));
		String value = filePath;
		iFileDao.Insert(key, value);
	}
	
	private int getHash(String contents) {
		return contents.hashCode();
	}
	
	@Override
	public void LoadRecent(Parent form) {
//		이 메소드는 최근에 저장한 파일 경로를 갱신하는 메소드입니다.
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
