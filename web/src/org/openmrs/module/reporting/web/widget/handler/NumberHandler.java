/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.web.widget.handler;

import java.io.IOException;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.web.widget.WidgetConfig;
import org.openmrs.module.reporting.web.widget.html.TextWidget;
import org.openmrs.module.reporting.web.widget.html.WidgetFactory;

/**
 * FieldGenHandler for Number Types
 */
@Handler(supports={Number.class}, order=50)
public class NumberHandler extends WidgetHandler {
	
	/** 
	 * @see WidgetHandler#handle(WidgetConfig)
	 */
	@Override
	public void handle(WidgetConfig config) throws IOException {
		
		TextWidget w = WidgetFactory.getInstance(TextWidget.class, config);
		Class<?> clazz = config.getType();
		
		if (clazz == Integer.class || clazz == Long.class) {
			config.setConfiguredAttribute("size", "8");
		}
		if (clazz == Double.class || clazz == Float.class) {
			config.setConfiguredAttribute("size", "12");
		}
		
		// TODO: Add validation
		w.render(config);
	}
}