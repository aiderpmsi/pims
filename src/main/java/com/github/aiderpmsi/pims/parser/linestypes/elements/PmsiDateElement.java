package com.github.aiderpmsi.pims.parser.linestypes.elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import com.github.aiderpmsi.pims.parser.model.PmsiElementConfig;

public class PmsiDateElement extends PmsiElementBase {
	
	SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
	
	public PmsiDateElement(PmsiElementConfig config) {
		super(config);
		format.setCalendar(new GregorianCalendar());
        format.setLenient(false);
	}

	@Override
	public boolean validate() {
		if (content == null)
			return false;
		
        try {
        	format.parse(new String(content.sequence, content.start, content.count));
        } catch (ParseException | IllegalArgumentException e) {
            return false;
        }
        return true;

	}

}