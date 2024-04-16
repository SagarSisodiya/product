package com.invento.product.model;

public class MetaData{
	
	private String size;
	
	private String color;
	
	private String memory;
	
	private String hardDisk;
	
	private String loadCapacity;

	public MetaData() {
		super();
	}

	public MetaData(String size, String color, String memory, String hardDisk, String loadCapacity) {
		super();
		this.size = size;
		this.color = color;
		this.memory = memory;
		this.hardDisk = hardDisk;
		this.loadCapacity = loadCapacity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getHardDisk() {
		return hardDisk;
	}

	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}

	public String getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(String loadCapacity) {
		this.loadCapacity = loadCapacity;
	}
}
