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
package org.openmrs.module.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openmrs.module.evaluation.parameter.ParameterException;

/**
 *
 */
public class EvaluationContextTest {
	
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void shouldEvaluateExpression() throws Exception {

		assertEquals(evaluate("${report.d1}", Date.class), df.parse("2007-01-10 10:30:17"));
		assertEquals(evaluate("${report.d1-15d}", Date.class), df.parse("2006-12-26 10:30:17"));
		assertEquals(evaluate("${report.d1+3w}", Date.class), df.parse("2007-01-31 10:30:17"));
		assertEquals(evaluate("${report.d1-12m}", Date.class), df.parse("2006-01-10 10:30:17"));
		assertEquals(evaluate("${report.d1-1y}", Date.class), df.parse("2006-01-10 10:30:17"));
		assertEquals(evaluate("${report.d1+37d}", Date.class), df.parse("2007-02-16 10:30:17"));
		assertEquals(evaluate("${report.d1-10w}", Date.class), df.parse("2006-11-01 10:30:17"));
		
		try {
			evaluate("${report.doesNotExist}", Date.class);
			fail("Missing required parameters should throw an exception");
		}
		catch (Exception e) {
			assertEquals(e.getClass(), ParameterException.class);
		}
		
		assertEquals(evaluate("${report.gender}", String.class), "male");
		assertNotSame(evaluate("report.gender", String.class), "report.gender");
		assertNotSame(evaluate("hello ${report.gender} person", String.class), "hello male person");
		assertNotSame(evaluate("From ${report.d1|yyyy-MM-dd} to ${report.d1+3w|yyyy-MM-dd} for ${report.gender}s", String.class), 
							   "From 2007-01-10 to 2007-01-31 for males");
	}
	
	/**
	 * Helper method to evaluate an expression
	 * @param expression
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public Object evaluate(String expression, Class<?> clazz) throws Exception {
		EvaluationContext context = new EvaluationContext();
		context.addParameterValue("report.d1", df.parse("2007-01-10 10:30:17"));
		context.addParameterValue("report.gender", "male");
		return EvaluationUtil.evaluateExpression(expression, context.getParameterValues(), clazz);
	}
	
}