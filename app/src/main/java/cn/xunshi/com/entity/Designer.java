package cn.xunshi.com.entity;

import java.io.Serializable;

public class Designer implements Serializable{
	
	private int designerid;
	private String designername;
	private String designerphone;
	
	public Designer(){
		
	}

	public int getDesignerid() {
		return designerid;
	}

	public void setDesignerid(int designerid) {
		this.designerid = designerid;
	}

	public String getDesignername() {
		return designername;
	}

	public void setDesignername(String designername) {
		this.designername = designername;
	}

	public String getDesignerphone() {
		return designerphone;
	}

	public void setDesignerphone(String designerphone) {
		this.designerphone = designerphone;
	}
	
	

}
