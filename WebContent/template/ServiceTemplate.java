package ##base_package##.service;

import java.util.List;
import java.util.Map;

import ##base_package##.entity.##base_name2##;

public interface ##base_name2##Service {

	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<##base_name2##> queryAllData(Map<String, Object> map);

	/**
	 * 查询所有数据条数
	 * 
	 * @return
	 */
	public int getTotalCount(Map<String, Object> map);

	/**
	 * 根据id获取
	 * 
	 * @param id
	 * @return
	 */
	public ##base_name2## getDataById(String id) throws Exception;

	/**
	 * 新增
	 * 
	 * @param o
	 * @return
	 */
	public int addData(##base_name2## o) throws Exception;

	/**
	 * 修改
	 * 
	 * @param o
	 * @return
	 */
	public int updateData(##base_name2## o) throws Exception;

	/**
	 * 删除
	 * 
	 * @param o
	 * @return
	 */
	public int deleteData(String id) throws Exception;
	
}
