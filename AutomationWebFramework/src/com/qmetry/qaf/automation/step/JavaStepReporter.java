/*******************************************************************************
 * QMetry Automation Framework provides a powerful and versatile platform to
 * author
 * Automated Test Cases in Behavior Driven, Keyword Driven or Code Driven
 * approach
 * Copyright 2016 Infostretch Corporation
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 * You should have received a copy of the GNU General Public License along with
 * this program in the name of LICENSE.txt in the root folder of the
 * distribution. If not, see https://opensource.org/licenses/gpl-3.0.html
 * See the NOTICE.TXT file in root folder of this source files distribution
 * for additional information regarding copyright ownership and licenses
 * of other open source software / files used by QMetry Automation Framework.
 * For any inquiry or need additional information, please contact
 * support-qaf@infostretch.com
 *******************************************************************************/

package com.qmetry.qaf.automation.step;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.MessageTypes;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.keys.ApplicationProperties;
import com.qmetry.qaf.automation.util.Reporter;

/**
 * com.qmetry.qaf.automation.step.JavaStepReporter.java
 * 
 * @author chirag
 */
@Aspect
public class JavaStepReporter {

	@Around("execution(@QAFTestStep * *.*(..))")
	public Object javaTestStep(ProceedingJoinPoint jp,
			JoinPoint.StaticPart thisJoinPointStaticPart) throws Throwable {
		JavaStep testStep = null;
		Signature sig = null;

		try {
			sig = thisJoinPointStaticPart.getSignature();
			if ((sig instanceof MethodSignature) && TestBaseProvider.instance().get()
					.getContext().getBoolean(JavaStep.ATTACH_LISTENER, true)) {
				// this must be a call or execution join point
				Method method = ((MethodSignature) sig).getMethod();

				testStep = new MockJavaStep(method, jp);
				if (null != jp.getArgs()) {
					testStep.setActualArgs(jp.getArgs());
				}
			}
		} catch (Exception e) {
			// ignore it...
		}

		if (ConfigurationManager.getBundle().getBoolean("method.recording.mode", false)) {
			ConfigurationManager.getBundle().setProperty("method.param.names",
					((MethodSignature) sig).getParameterNames());
			return null;
		} else {
			// unblock for sub-steps
			TestBaseProvider.instance().get().getContext()
					.setProperty(JavaStep.ATTACH_LISTENER, true);
			if (null != testStep) {
				try {
					return testStep.execute();
				} catch (JPThrowable e) {
					throw e.getCause();
				}
			} else {

				// this call is from text client (bdd/kwd/excel)
				testStep = (JavaStep) TestBaseProvider.instance().get().getContext()
						.getProperty("current.teststep");
				testStep.setFileName(jp.getSourceLocation().getFileName());
				testStep.setLineNumber(jp.getSourceLocation().getLine());
				testStep.signature = jp.getSignature().toLongString();

				return jp.proceed();

			}

		}

	}

	private class JPThrowable extends Error {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3472971476885434113L;

		public JPThrowable(Throwable cause) {
			super(cause);
		}
	}

	class MockJavaStep extends JavaStep {
		private final ProceedingJoinPoint jp;

		public MockJavaStep(Method method, ProceedingJoinPoint jp) {
			super(method);
			this.jp = jp;
			setFileName(jp.getSourceLocation().getFileName());
			setLineNumber(jp.getSourceLocation().getLine());
			signature = jp.getSignature().toLongString();
		}

		@Override
		protected Object doExecute() {
			try {
				if (ApplicationProperties.DRY_RUN_MODE.getBoolenVal(false)){
					Reporter.log(getDescription(), MessageTypes.TestStepPass);
					return true;
				}else 
					return jp.proceed();

			} catch (InvocationTargetException ite) {
				throw new JPThrowable(ite);
			} catch (Throwable t) {

				throw new JPThrowable(t);
			}
		}
	}
}
