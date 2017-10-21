package ##base_package##.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ##base_package##.dao.##base_name2##Mapper;
import ##base_package##.entity.##base_name2##;

@Service(value = "##base_name##Service")
public class ##base_name2##ServiceImpl implements ##base_name2##Service {
	@Autowired
	private ##base_name2##Mapper dao;

	@Override
	public List<##base_name2##> queryAllData(Map<String, Object> map) {
		return dao.queryAllData(map);
	}

	@Override
	public ##base_name2## getDataById(String id) throws Exception {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateData(##base_name2## o) throws Exception {
		return dao.updateByPrimaryKeySelective(o);
	}

	@Override
	public int getTotalCount(Map<String, Object> map) {
		return dao.getTotalCount(map);
	}

	@Override
	public int addData(##base_name2## o) throws Exception {
		return dao.insert(o);		
	}

	@Override
	public int deleteData(String id) throws Exception {
		return dao.deleteByPrimaryKey(id);
	}

}
