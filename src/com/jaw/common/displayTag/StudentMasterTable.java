package com.jaw.common.displayTag;

import org.displaytag.decorator.TableDecorator;

/**
 * A table decorator which adds checkboxes for selectable rows.
 * 
 * @author Fabrizio Giustina
 * @version $Id: CheckboxTableDecorator.java 1134 2008-12-27 10:16:33Z fgiust $
 */
public class StudentMasterTable extends TableDecorator {

	public Integer getSerialno() {

		return getListIndex() + 1;
	}

}
