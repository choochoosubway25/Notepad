package notepad;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
//아래 주석처리된 코드도 가능하다. (단 주석처리된 코드 모드를 교체해야한다.)
//import static com.mongodb.client.model.Projections.excludeId;


public class FileDaoImpl implements IFileDao{
//	DB Name
	final private String DB_NAME = "NotepadDB";
//	Collection Name
	final private String COLLECTION_NAME = "Notepad";
	private MongoCollection<Document> collection;
	
	public FileDaoImpl() {
		collection = MongoClients.create()
				.getDatabase(DB_NAME)
				.getCollection(COLLECTION_NAME);
	}
	@Override
	public void Insert(String key, String value) {
		/*
		 * 이 코드는 mongodb 서버에 경로의 key 값과, 경로 값을 같이 저장하는 코드입니다.
		 * 이때, 이미 db에 있는 상황이라면, 이를 걸러내 삽입을 중지합니다.
		 * 이를 통해 경로를 저장 코드의 실행 시간 효율을 높입니다.
		 */
//		isDubplicate에서 중복되면 true, 아니면 false 반환
		if(isDuplicate(key,value)) return;   // 이 코드는 중복값을 걸러내는 코드
		Document doc = new Document();
		doc.put(key, value);
		collection.insertOne(doc);
	}

	private boolean isDuplicate(String key, String value) {
		/*
		 * 아래 코드는 주어진 key, value에 해당하는 데이터를 찾는 코드로
		 * 결과값은 데이터들의 집합이 됩니다.
		 * 데이터들의 집합에 무언가 있으면 중복 데이터가 있으니 true, 
		 * 없으면 false 가 됩니다.
		 */
//		중복된 key, value 찾기
		Bson doc = eq(key, value);
		FindIterable<Document> docs = collection.find(doc);
		if(docs.first()==null) return false;
		return true;
	}
	
	@Override
	public List<String> select() {
		FindIterable<Document> docs = collection.find();
		List<String> pathLst = new ArrayList<String>();
		
//		아래 주석처리된 코드도 가능하다. (단 주석처리된 코드 모드를 교체해야한다.)
//		docs.projection(excludeId());
		docs.projection(exclude("_id"));
		for(Document doc : docs) {
			String tmp = doc.values().toString();
			pathLst.add(tmp.replace("[", "").replace("]", ""));
		}
		
		return pathLst;
	}


}
