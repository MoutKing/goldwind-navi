/**
 * 
 */
package com.gw.web.test.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gw.web.common.action.BaseAction;
import com.gw.web.common.multithread.AsyncRunner;
import com.gw.web.common.multithread.AsyncRunnerParams;
import com.gw.web.common.multithread.AsyncRunnerUtil;
import com.gw.web.test.model.TempModel;
import com.gw.web.test.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shenghui test url is http://localhost:10080/list.md?isOpen=1
 */
@RestController
public class TestAction extends BaseAction {
	@Autowired
	private TestService testService;

	@RequestMapping(value = "/list.dt", method = RequestMethod.GET)
	public HashMap<String,String> list_get(HttpServletResponse response, HttpServletRequest request) {
		logger.info("<<<<<<<<<<<<<                 list_get                     >>>>>>>>>>>>>>"+ request.getSession().getId());
		// logger.info("<<<<<<<<<<<<< [" + tm.getIsOpen() + "] >>>>>>>>>>>>>>");

		// logger.debug( "<<<<<<<<<<<<<<<<<<< list=[" + testService.list() + "]
		// >>>>>>>>>>>>>>>>>" );

		String total = "abc";
		String total1 = "ddddeddddd";

		AsyncRunnerParams param = new AsyncRunnerParams(20);
		String[] ids = AsyncRunnerUtil.run(this, new String[] { "getIntRslt", "getStringRslt", "getListRslt" }, new AsyncRunnerParams[] { param, param, param });

		if (ids != null && ids.length > 0) {
			Integer intRslt = (Integer) AsyncRunnerUtil.get(ids[0]);
			String stringRslt = (String) AsyncRunnerUtil.get(ids[1]);
			List<String> listRslt = (ArrayList<String>) AsyncRunnerUtil.get(ids[2]);

			logger.info("###############      intRslt = [" + intRslt + "]         stringRslt =[" + stringRslt + "]      listRslt[0] = " + listRslt.get(0) + "     ##############");
		}

		HashMap<String,String> m = new HashMap<String,String>();
		m.put("total", total);
		m.put("total1", total1);
		return m;
	}

	@RequestMapping(value = "/use", method = RequestMethod.GET)
	public HashMap<String,String> userInfo() {
		HashMap<String,String> m = new HashMap<String,String>();
		m.put("total", "555");
		m.put("total1", "6666");
		return m;
	}

	@RequestMapping(value = "/list.dt", method = RequestMethod.POST)
	public HashMap<String,String> list_post(@ModelAttribute TempModel tm) {
		logger.info("<<<<<<<<<<<<<                 list_post             [" + tm.getContent() + "]        >>>>>>>>>>>>>>");

		logger.info("<<<<<<<<<<<<<                 [" + tm.getIsOpen() + "]                     >>>>>>>>>>>>>>");

		// logger.debug( "<<<<<<<<<<<<<<<<<<< list=[" + testService.list() + "]
		// >>>>>>>>>>>>>>>>>" );

		String total = "abc";
		String total1 = "ddddddddd";

		AsyncRunnerParams param = new AsyncRunnerParams(20);
		String[] ids = AsyncRunnerUtil.run(this, new String[] { "getIntRslt", "getStringRslt", "getListRslt" }, new AsyncRunnerParams[] { param, param, param });

		if (ids != null && ids.length > 0) {
			Integer intRslt = (Integer) AsyncRunnerUtil.get(ids[0]);
			String stringRslt = (String) AsyncRunnerUtil.get(ids[1]);
			List<String> listRslt = (ArrayList<String>) AsyncRunnerUtil.get(ids[2]);

			logger.info("###############      intRslt = [" + intRslt + "]         stringRslt =[" + stringRslt + "]      listRslt[0] = " + listRslt.get(0) + "     ##############");
		}

		HashMap<String,String> m = new HashMap<String,String>();
		m.put("total", total);
		m.put("total1", total1);
		return m;
	}

	@AsyncRunner(id = "getIntRslt")
	public Integer getIntRslt(int entNo) throws Exception {
		return testService.getIntRslt();
	}

	@AsyncRunner(id = "getStringRslt")
	public String getStringRslt(int entNo) throws Exception {
		return String.valueOf(entNo);
	}

	@AsyncRunner(id = "getListRslt")
	public List<String> getListRslt(int entNo) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(entNo));
		return list;
	}
}
