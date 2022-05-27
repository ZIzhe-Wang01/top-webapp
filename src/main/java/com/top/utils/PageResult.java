package com.top.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Paging results wrapper object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResult<T> implements Serializable {
	
	private static final long serialVersionUID = 8800028898646527257L;
	
	private Long total;
	private Integer totalPage;
	private Integer currentPage;
	private Integer rows;
	private List<T> list;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	
	
}
