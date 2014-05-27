package com.github.aiderpmsi.pims.parser.model;

import javax.xml.bind.annotation.XmlElement;

public class Element {

	private String name = null;
	
	private String pattern = null;
	
	private String in = null;
	
	private String out = null;

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@XmlElement(defaultValue="")
	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	@XmlElement(defaultValue="")
	public String getOut() {
		return out;
	}

	public void setOut(String out) {
		this.out = out;
	}

}