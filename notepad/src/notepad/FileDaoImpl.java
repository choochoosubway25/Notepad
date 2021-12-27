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
//�Ʒ� �ּ�ó���� �ڵ嵵 �����ϴ�. (�� �ּ�ó���� �ڵ� ��带 ��ü�ؾ��Ѵ�.)
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
		 * �� �ڵ�� mongodb ������ ����� key ����, ��� ���� ���� �����ϴ� �ڵ��Դϴ�.
		 * �̶�, �̹� db�� �ִ� ��Ȳ�̶��, �̸� �ɷ��� ������ �����մϴ�.
		 * �̸� ���� ��θ� ���� �ڵ��� ���� �ð� ȿ���� ���Դϴ�.
		 */
//		isDubplicate���� �ߺ��Ǹ� true, �ƴϸ� false ��ȯ
		if(isDuplicate(key,value)) return;   // �� �ڵ�� �ߺ����� �ɷ����� �ڵ�
		Document doc = new Document();
		doc.put(key, value);
		collection.insertOne(doc);
	}

	private boolean isDuplicate(String key, String value) {
		/*
		 * �Ʒ� �ڵ�� �־��� key, value�� �ش��ϴ� �����͸� ã�� �ڵ��
		 * ������� �����͵��� ������ �˴ϴ�.
		 * �����͵��� ���տ� ���� ������ �ߺ� �����Ͱ� ������ true, 
		 * ������ false �� �˴ϴ�.
		 */
//		�ߺ��� key, value ã��
		Bson doc = eq(key, value);
		FindIterable<Document> docs = collection.find(doc);
		if(docs.first()==null) return false;
		return true;
	}
	
	@Override
	public List<String> select() {
		FindIterable<Document> docs = collection.find();
		List<String> pathLst = new ArrayList<String>();
		
//		�Ʒ� �ּ�ó���� �ڵ嵵 �����ϴ�. (�� �ּ�ó���� �ڵ� ��带 ��ü�ؾ��Ѵ�.)
//		docs.projection(excludeId());
		docs.projection(exclude("_id"));
		for(Document doc : docs) {
			String tmp = doc.values().toString();
			pathLst.add(tmp.replace("[", "").replace("]", ""));
		}
		
		return pathLst;
	}


}
