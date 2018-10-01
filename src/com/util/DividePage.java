package com.util;

public class DividePage {
	private int pageSize;
	private int totalRecord;
	private int currentPage;
	
	public DividePage(int pageSize, int totalRecord, int currentPage) {
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		setCurrentPage(currentPage);
	}
	
	public DividePage(int pageSize, int totalRecord) {
		this(pageSize, totalRecord, 1);
	}
	
	public int getPageCount() {
		int pageCount = totalRecord / pageSize;
		int mod = totalRecord % pageSize;
		if (mod != 0) {
			pageCount++;
		}
		return pageCount;
	}
	
	public int fromIndex() {
		return (currentPage - 1) * pageSize;
	}
	
	public int toIndex() {
		return pageSize;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (getCurrentPage() != 0) {
			int validPage = currentPage < 1 ? 1 : currentPage;
			validPage = validPage > getCurrentPage() ? getCurrentPage() : validPage;
			this.currentPage = validPage;
		}
		else {
			this.currentPage = 1;
		}
	}

}
