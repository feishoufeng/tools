package cc.zytrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.zytrip.serviceimpl.code.CodeService;
import cc.zytrip.util.Pagination;

/**
 * Created by YangZhang on 2016/10/20.
 */
@Controller
@RequestMapping("/code")
public class CodeController {

	@Autowired
	private CodeService codeService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view() {
		return "/create_code";
	}

	@ResponseBody
	@RequestMapping(value = "/getTableNames", method = RequestMethod.POST)
	public Object getTableNames() {
		Pagination<String> page = new Pagination<String>();
		page.setItems(codeService.queryTableName());
		return page;
	}

	@ResponseBody
	@RequestMapping(value = "/createClass", method = RequestMethod.POST)
	public Object createClass(@RequestParam(name = "thPack") String thPack, @RequestParam(name = "path") String path,
			@RequestParam(name = "tableName") String tableName, @RequestParam(name = "model") String model,
			@RequestParam(name = "dao") String dao, @RequestParam(name = "daoxml") String daoxml,
			@RequestParam(name = "service") String service) {

		codeService.createClass(model, dao, daoxml, service, path, tableName, thPack);
		return 1;
	}

}
