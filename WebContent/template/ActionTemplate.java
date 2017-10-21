package ##base_package##.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import ##base_package##.entity.##base_name2##;
import ##base_package##.service.##base_name2##Service;
import com.gtercn.system.cms.util.CommonUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ##base_name2##Action extends ActionSupport {

	@Autowired
	private ##base_name2##Service ##base_name##Service;

	private ##base_name2## entity;

	private JSONArray json;

	public JSONArray getJson() {
		return json;
	}

	public void setJson(JSONArray json) {
		this.json = json;
	}

	public ##base_name2## getEntity() {
		return entity;
	}

	public void setEntity(##base_name2## entity) {
		this.entity = entity;
	}

	/**
	 * 检索数据
	 * 
	 * @return
	 */
	public String listData() {
		Map<String, Object> map = new HashMap<String, Object>();
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			/*
			String configid = request.getParameter("configid");
			String appid = request.getParameter("appid");
			map.put("configid", configid);
			map.put("appid", appid);
			*/
			
			##listData1##

			int pageSize = 15;// 每页显示15条
			int totalCount = ##base_name##Service.getTotalCount(map);
			int currentIndex = 0;// 当前页
			String index = request.getParameter("pno");
			if (index != null && index != "") {
				currentIndex = Integer.valueOf(index);
			} else {
				currentIndex = 1;
			}
			int totalPages = (totalCount % pageSize == 0) ? (totalCount / pageSize)
					: (totalCount / pageSize + 1);
			map.put("beginResult", (currentIndex - 1) * pageSize);
			map.put("pageSize", pageSize);
			List<##base_name2##> list = ##base_name##Service.queryAllData(map);

			context.put("##base_name2##List", list);
			context.put("totalPages", totalPages);
			context.put("totalCount", totalCount);
			context.put("currentIndex", currentIndex);

			/*
			context.put("configid", configid);
			context.put("appid", appid);
			*/
			##listData2##

		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		}
		return "list";
	}

	/**
	 * 添加数据(进入画面)
	 * 
	 * @return
	 */
	public String addDataPage() {
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();

		try {
			int currentIndex = 0;// 当前页
			String index = request.getParameter("backPageNo");// 返回，记录列表页数据
			if (index != null && index != "") {
				currentIndex = Integer.valueOf(index);
			} else {
				currentIndex = 1;
			}
			context.put("currentIndex", currentIndex);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Action.ERROR;
		}
		return "add";
	}

	/**
	 * 添加数据
	 * 
	 * @return
	 */
	public String addData() {
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			String uuid = CommonUtil.getUID();
			entity.##pk_name_camel##(uuid);

			int ret = ##base_name##Service.addData(entity);
			System.out.println("ret = " + ret);
			writer = response.getWriter();

			System.out.println("entity2 = " + entity.toString());

			writer.print("<script>alert('添加成功!');window.location.href='##base_name##Action!listData.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('添加失败!');window.location.href='##base_name##Action!listData.action';</script>");
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteData() {
		ServletRequest request = ServletActionContext.getRequest();
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String ids[] = request.getParameterValues("##pk_name##");
			for (String id : ids) {
				##base_name##Service.deleteData(id);
			}
			writer.print("<script>alert('删除成功!');window.location.href='##base_name##Action!listData.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('删除失败!');window.location.href='##base_name##Action!listData.action';</script>");
		}
		return null;
	}

	/**
	 * 修改数据(进入画面)
	 * 
	 * @return
	 */
	public String updateDataPage() {
		ActionContext context = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("##pk_name##");
		try {
			entity = ##base_name##Service.getDataById(id);
			context.put("entity", entity);
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		}
		return "update";
	}

	/**
	 * 修改数据
	 * 
	 * @return
	 */
	public String updateData() {
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			System.out.println("entity = " + entity.toString());

			int ret = ##base_name##Service.updateData(entity);
			System.out.println("ret = " + ret);
			writer = response.getWriter();

			System.out.println("entity2 = " + entity.toString());

			writer.print("<script>alert('修改成功!');window.location.href='##base_name##Action!listData.action';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.print("<script>alert('修改失败!');window.location.href='##base_name##Action!listData.action';</script>");
		}
		return null;
	}

}
