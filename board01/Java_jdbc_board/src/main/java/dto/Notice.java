package dto;

public class Notice {
	private int id;
	private String title;
	private String writerID;
	private String content;
	private String regdate;
	private int hit;
	
	public Notice() {
		
	}
	
	public Notice(int id, String title, String writerID, String content, String regdate, 
			int hit, String files) {
		this.id = id;
		this.title = title;
		this.writerID = writerID;
		this.content = content;
		this.regdate = regdate;
		this.hit = hit;
		this.files = files;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriterID() {
		return writerID;
	}

	public void setWriterID(String writerID) {
		this.writerID = writerID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	private String files;
}
