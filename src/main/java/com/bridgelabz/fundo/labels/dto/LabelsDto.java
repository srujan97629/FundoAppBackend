package com.bridgelabz.fundo.labels.dto;

public class LabelsDto
{
	private String labelName;
	
	public LabelsDto() {
		
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Override
	public String toString() {
		return "LabelsDto [labelName=" + labelName + "]";
	}
	
}
