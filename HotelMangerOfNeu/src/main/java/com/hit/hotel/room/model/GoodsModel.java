package com.hit.hotel.room.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.hit.hotel.hr.model.DepartmentModel;

@Alias("Goods")
public class GoodsModel implements Serializable {

	private int no = 0;
	private String code = null;
	private String name=null;
	//关联的房间号
	private RoomModel room=null;
	private int price = 0;
	public int getNo() {
		return no;
	}
	public RoomModel getRoom() {
		return room;
	}
	public void setRoom(RoomModel room) {
		this.room = room;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
