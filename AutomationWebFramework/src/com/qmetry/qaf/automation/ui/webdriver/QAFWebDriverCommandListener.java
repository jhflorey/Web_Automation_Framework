/*******************************************************************************
 * QMetry Automation Framework provides a powerful and versatile platform to author 
 * Automated Test Cases in Behavior Driven, Keyword Driven or Code Driven approach
 *                
 * Copyright 2016 Infostretch Corporation
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE
 *
 * You should have received a copy of the GNU General Public License along with this program in the name of LICENSE.txt in the root folder of the distribution. If not, see https://opensource.org/licenses/gpl-3.0.html
 *
 * See the NOTICE.TXT file in root folder of this source files distribution 
 * for additional information regarding copyright ownership and licenses
 * of other open source software / files used by QMetry Automation Framework.
 *
 * For any inquiry or need additional information, please contact support-qaf@infostretch.com
 *******************************************************************************/


package com.qmetry.qaf.automation.ui.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.Response;

import com.qmetry.qaf.automation.core.QAFListener;

/**
com.qmetry.qaf.automation.ui.webdriver.QAFWebElementCommandListener.java
 * 
 * @author chirag.jayswal
 */
public interface QAFWebDriverCommandListener extends QAFListener {
	/**
	 * This can be used as intercepter. If you want to skip execution of actual
	 * command then set response in {@link CommandTracker#setResponce(Response)}
	 * 
	 * @param driver
	 * @param commandHandler
	 */
	public void beforeCommand(QAFExtendedWebDriver driver, CommandTracker commandHandler);

	public void afterCommand(QAFExtendedWebDriver driver, CommandTracker commandHandler);

	/**
	 * This can be used to propagate exception. You can get information about
	 * from where exception thrown by inspecting
	 * {@link CommandTracker#getStage()}
	 * 
	 * @param driver
	 * @param commandHandler
	 */
	public void onFailure(QAFExtendedWebDriver driver, CommandTracker commandHandler);

	/**
	 * Here you can specify additional desired capabilities for the driver.
	 * 
	 * @param desiredCapabilities
	 */
	public void beforeInitialize(Capabilities desiredCapabilities);

	/**
	 * this method will be called when new driver instance is created
	 * 
	 * @param driver
	 */
	public void onInitialize(QAFExtendedWebDriver driver);
	
	public void onInitializationFailure(Capabilities desiredCapabilities, Throwable t);

}
