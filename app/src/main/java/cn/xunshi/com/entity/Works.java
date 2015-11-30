package cn.xunshi.com.entity;

import java.io.Serializable;

public class Works implements Serializable{

	private int worksid;
	private String worksname;
	private String workspicture;
	private String worksprice;
	private String material;
	private int designerid;
	
	public Works(){
		
	}

	public int getWorksid() {
		return worksid;
	}

	public void setWorksid(int worksid) {
		this.worksid = worksid;
	}

	public String getWorksname() {
		return worksname;
	}

	public void setWorksname(String worksname) {
		this.worksname = worksname;
	}

	public String getWorkspicture() {
		return workspicture;
	}

	public void setWorkspicture(String workspicture) {
		this.workspicture = workspicture;
	}

	public String getWorksprice() {
		return worksprice;
	}

	public void setWorksprice(String worksprice) {
		this.worksprice = worksprice;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getDesignerid() {
		return designerid;
	}

	public void setDesignerid(int designerid) {
		this.designerid = designerid;
	}
	
	
	
}
