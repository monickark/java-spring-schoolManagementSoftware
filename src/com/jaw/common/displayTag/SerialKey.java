package com.jaw.common.displayTag;

import org.displaytag.decorator.TableDecorator;

public class SerialKey extends TableDecorator {

	public Integer getSerialno() {

		return getListIndex() + 1;
	}

}
