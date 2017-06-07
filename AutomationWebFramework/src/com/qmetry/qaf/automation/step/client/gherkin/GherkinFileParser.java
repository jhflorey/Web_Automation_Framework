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

package com.qmetry.qaf.automation.step.client.gherkin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.qmetry.qaf.automation.core.AutomationError;
import com.qmetry.qaf.automation.step.client.AbstractScenarioFileParser;
import com.qmetry.qaf.automation.step.client.Scenario;
import com.qmetry.qaf.automation.step.client.text.BehaviorScanner;
import com.qmetry.qaf.automation.testng.dataprovider.QAFDataProvider.params;
import com.qmetry.qaf.automation.util.StringUtil;

/**
 * @author chirag.jayswal
 */
public class GherkinFileParser extends AbstractScenarioFileParser {

	private final static Log logger = LogFactory.getLog(BehaviorScanner.class);
	private static final String TAG = "@";
	private static final String COMMENT_CHARS = "#!|";
	public static final String SCENARIO_OUTELINE = "Scenario Outline";
	public static final String EXAMPLES = "EXAMPLES";
	public static final String FEATURE = "Feature";
	public static final String BACKGROUND = "Background";

	protected void processStatements(Object[][] statements, String referece,
			List<Scenario> scenarios) {

		for (int statementIndex =
				0; statementIndex < statements.length; statementIndex++) {

			String type = ((String) statements[statementIndex][0]).trim();

			// ignore blanks and statements outside scenario or step-def
			if (StringUtil.isBlank(type)
					|| !(type.equalsIgnoreCase(FEATURE) || type.equalsIgnoreCase(SCENARIO)
							|| type.equalsIgnoreCase(EXAMPLES))) {
				String nextSteptype = "";
				do {
					statementIndex++;
					if (statements.length > (statementIndex + 2)) {
						nextSteptype = ((String) statements[statementIndex][0]).trim();
					} else {
						nextSteptype = END; //
					}
				} while (!(nextSteptype.equalsIgnoreCase(EXAMPLES)
						|| nextSteptype.equalsIgnoreCase(SCENARIO)
						|| nextSteptype.equalsIgnoreCase(END)));
			}

			// Custom step definition
			if (type.equalsIgnoreCase(STEP_DEF)) {
				statementIndex = parseStepDef(statements, statementIndex, referece);
			} else if (type.equalsIgnoreCase(SCENARIO)) {
				statementIndex =
						parseScenario(statements, statementIndex, referece, scenarios);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<Object[]> parseFile(String strFile) {
		ArrayList<Object[]> rows = new ArrayList<Object[]>();
		ArrayList<Object[]> background = new ArrayList<Object[]>();

		File textFile;
		int lineNo = 0;
		boolean bglobalTags = true;
		boolean outline = false;
		boolean isBackground = false;
		ArrayList<String> globalTags = new ArrayList<String>();
		ArrayList<String> scenarioTags = new ArrayList<String>();
		ArrayList<List<Object>> examplesTable = new ArrayList<List<Object>>();

		BufferedReader br = null;
		try {

			logger.info("loading feature file: " + strFile);
			textFile = new File(strFile);
			br = new BufferedReader(new FileReader(textFile));
			String strLine = "";
			int lastScenarioIndex = 0;

			// file line by line
			// exclude blank lines and comments
			StringBuffer currLineBuffer = new StringBuffer();
			while ((strLine = br.readLine()) != null) {
				// record line number
				lineNo++;
				/**
				 * ignore if line is empty or comment line
				 */
				if (!("".equalsIgnoreCase(strLine.trim())
						|| COMMENT_CHARS.contains("" + strLine.trim().charAt(0)))) {
					currLineBuffer.append((strLine.trim()));

					// process single statement
					Object[] cols = new Object[]{"", "", "", lineNo};
					String currLine = currLineBuffer.toString();
					String type = getType(currLine);
					if (type == "") {
						// this is a statement
						cols[0] = outline ? convertParam(currLine) : currLine;
					} else {
						isBackground = false;
						if (type.equalsIgnoreCase(TAG)) {
							String[] tags = currLine.split(" ");
							if (bglobalTags) {
								globalTags.addAll(Arrays.asList(tags));
							} else {
								scenarioTags.addAll(Arrays.asList(tags));
							}
							currLineBuffer = new StringBuffer();
							continue;
						}
						if (type.equalsIgnoreCase(BACKGROUND)) {
							isBackground = true;
							currLineBuffer = new StringBuffer();
							continue;
						}
						System.arraycopy(currLine.split(":", 2), 0, cols, 0, 2);
						if (type.equalsIgnoreCase(EXAMPLES)) {
							Object[] scenario = rows.get(lastScenarioIndex);
							scenario[0] = SCENARIO;

							Map<Object, Object> metadata =
									new Gson().fromJson((String) scenario[2], Map.class);
							//scenario[2] = new Gson().toJson(metadata);

							String exampleMetadata = (String) cols[1];

							if (StringUtil.isNotBlank(exampleMetadata)
									&& exampleMetadata.trim().startsWith("{")) {
								metadata.putAll(
										new Gson().fromJson(exampleMetadata, Map.class));
								scenario[2] = new Gson().toJson(metadata);
								currLineBuffer = new StringBuffer();
								continue;
							}

						} else {
							scenarioTags.addAll(globalTags);
							String metadata = String.format("{\"groups\":%s}",
									new Gson().toJson(scenarioTags));
							cols[2] = metadata;
							scenarioTags.clear();
							if (type.equalsIgnoreCase(FEATURE)) {
								bglobalTags = false;
								outline = false;
							} else {
								outline = type.equalsIgnoreCase(SCENARIO_OUTELINE);
							}

						}
					}

					if (!examplesTable.isEmpty()) {
						String lastStamtent = (String) rows.get(rows.size() - 1)[0];
						int lastStatementIndex = lastStamtent.equalsIgnoreCase(EXAMPLES)
								? lastScenarioIndex : (rows.size() - 1);
						setExamples(rows.get(lastStatementIndex), examplesTable);
						examplesTable.clear();
						if (lastStamtent.equalsIgnoreCase(EXAMPLES)) {
							rows.remove(rows.size() - 1);
						}
					}
					

					if (isBackground)
						background.add(cols);
					else {
						rows.add(cols);
						boolean scenarioStarted = StringUtil.indexOfIgnoreCase(type, SCENARIO) == 0;
						
						if (scenarioStarted) {
							lastScenarioIndex = rows.size() - 1;
								rows.addAll(background);
						}
					}
					currLineBuffer = new StringBuffer();

				} else if (StringUtil.isNotBlank(strLine)
						&& strLine.trim().charAt(0) == '|') {
					addExample(strLine.trim(), examplesTable);
				}
			}

			int lastStatementIndex =  rows.size() - 1;
			String lastStamtent = (String) rows.get(lastStatementIndex)[0];
			if (lastStamtent.equalsIgnoreCase(EXAMPLES)) {
				rows.remove(lastStatementIndex);
				lastStatementIndex =lastScenarioIndex;
			}
			
			if (!examplesTable.isEmpty()) {
				setExamples(rows.get(lastStatementIndex), examplesTable);
				examplesTable.clear();
			}
			
		} catch (Exception e) {
			String strMsg = "Exception while reading BDD file: " + strFile + "#" + lineNo;
			logger.error(strMsg + e);
			throw new AutomationError(strMsg, e);

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		rows.add(new Object[]{"END", "", "", lineNo+1});//indicate end of BDD
		return rows;
	}

	private void setExamples(Object[] cols, ArrayList<List<Object>> examplesTable) {
		// TODO Auto-generated method stub
		boolean isMap = examplesTable.get(0).size() > 1;
		boolean isScenario = ((String) cols[0]).trim().equalsIgnoreCase(SCENARIO);
		Object data = null;
		if (isMap || isScenario) {
			List<String> keys = new ArrayList<String>();
			List<Map<String, Object>> dataMapList = new ArrayList<Map<String, Object>>();
			for (Object entry : examplesTable.get(0)) {
				keys.add((String) entry);
			}

			for (int i = 1; i < examplesTable.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int k = 0; k < keys.size(); k++) {
					map.put(keys.get(k), examplesTable.get(i).get(k).toString().trim());
				}
				dataMapList.add(map);
			}
			if (!isScenario && dataMapList.size() == 1) {
				data = dataMapList.get(0); // case of map argument to statement
			} else {
				data = dataMapList;
			}
		} else {
			List<Object> res = new ArrayList<Object>();
			for (List<Object> entry : examplesTable) {
				Object o = entry.get(0); // need to process string?...
				res.add(o);
			}
			data = res;
		}

		if (isScenario) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> metadata =
					new Gson().fromJson((String) cols[2], Map.class);
			metadata.put(params.JSON_DATA_TABLE.name(), new Gson().toJson(data));
			cols[2] = new Gson().toJson(metadata);

		} else {
			cols[0] = cols[0] + new Gson().toJson(data);
		}
	}

	private void addExample(String line, ArrayList<List<Object>> examplesTable) {
		String[] rawData = StringUtil.parseCSV(line, '|');
		ArrayList<Object> cols = new ArrayList<Object>();
		for (int i = 1; i < rawData.length - 1; i++) {
			// if (StringUtil.isBlank(rawData[i]) && (i == 0 || i ==
			// rawData.length-1)) {
			// continue;
			// }
			cols.add(rawData[i].trim());
		}
		examplesTable.add(cols);
	}

	private String convertParam(String currLine) {

		return StringUtil.replace(StringUtil.replace(currLine, ">", "}", -1), "<", "${",
				-1);
	}

	private String getType(String line) {
		if (StringUtil.indexOfIgnoreCase(line, TAG) == 0)
			return TAG;
		if (StringUtil.indexOfIgnoreCase(line, SCENARIO_OUTELINE) == 0)
			return SCENARIO_OUTELINE;
		if (StringUtil.indexOfIgnoreCase(line, SCENARIO) == 0)
			return SCENARIO;
		if (StringUtil.indexOfIgnoreCase(line, EXAMPLES) == 0)
			return EXAMPLES;
		if (StringUtil.indexOfIgnoreCase(line, FEATURE) == 0)
			return FEATURE;
		if (StringUtil.indexOfIgnoreCase(line, BACKGROUND) == 0)
			return BACKGROUND;
		return "";
	}

}
