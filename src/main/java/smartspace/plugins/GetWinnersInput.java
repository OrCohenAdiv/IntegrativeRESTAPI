package Plugin;

public class GetWinnersInput {
	private int size;
	private int page;

	public GetWinnersInput() {
		this.size = 10;
		this.page = 0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}