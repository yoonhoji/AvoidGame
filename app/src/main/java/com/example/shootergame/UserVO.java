package com.example.shootergame;

import java.io.Serializable;

public class UserVO implements Serializable {
	int useq;
	String nickname;
	String id;
	String pw;
	int rp;
	int point;
	String status;
	
	public int getUseq() {
		return useq;
	}
	public UserVO setUseq(int useq) {
		this.useq = useq;
		return this;
	}
	public String getNickname() {
		return nickname;
	}
	public UserVO setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}
	public String getId() {
		return id;
	}
	public UserVO setId(String id) {
		this.id = id;
		return this;
	}
	public String getPw() {
		return pw;
	}
	public UserVO setPw(String pw) {
		this.pw = pw;
		return this;
	}
	public int getRp() {
		return rp;
	}
	public UserVO setRp(int rp) {
		this.rp = rp;
		return this;
	}
	public int getPoint() {
		return point;
	}
	public UserVO setPoint(int point) {
		this.point = point;
		return this;
	}
	public String getStatus() {
		return status;
	}
	public UserVO setStatus(String status) {
		this.status = status;
		return this;
	}
}
