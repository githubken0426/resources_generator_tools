package com.gtercn.oneday.web.api.controllers.favorite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtercn.oneday.web.api.OneDayApiConfig;
import com.gtercn.oneday.web.api.entity.Favorite;
import com.gtercn.oneday.web.api.entity.FavoriteKey;
import com.gtercn.oneday.web.api.entity.Goods;
import com.gtercn.oneday.web.api.entity.Picture;
import com.gtercn.oneday.web.api.enums.ErrorCode;
import com.gtercn.oneday.web.api.exception.ApiException;
import com.gtercn.oneday.web.api.form.FavoriteAddForm;
import com.gtercn.oneday.web.api.service.favorite.IFavoriteService;
import com.gtercn.oneday.web.api.service.prcture.IPictureService;
import com.gtercn.oneday.web.api.utils.CommonUtil;
import com.gtercn.oneday.web.api.utils.DateFormateUtil;
import com.gtercn.oneday.web.api.view.JsonListView;
import com.gtercn.oneday.web.api.view.JsonObjectView;

@Controller
@RequestMapping({ "/app/v1/favorite" })
public class FavoriteController {
	@Autowired
	private IFavoriteService favoriteService;
	@Autowired
	private IPictureService pictureService;

	// 获取收藏列表
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JsonListView favoriteList(HttpServletRequest request, String user_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JsonListView json = new JsonListView();
		List<Favorite> listrsult = favoriteService.getList(user_id);
		try {
			if (listrsult.size() > 0) {
				for (int i = 0; i < listrsult.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					Goods goods = favoriteService.getGoodsDetailInfo(listrsult
							.get(i).getGoodsId(), user_id);
					BigDecimal price = goods.getPrice();
					map.put("goods_description", goods.getGoodsDescription());
					map.put("price", price);
					map.put("preservation", goods.getPreservation());
					map.put("goods_name", goods.getGoodsName());
					map.put("price_now", goods.getDiscountPrice());
					String[] str = goods.getPictureIdGroup().split(",");
					Picture picture = pictureService.getPicture(str[0]);
					map.put("picture_url",
							OneDayApiConfig.ONE_DAY_API
									.getValue("resources_ip")
									+ "/"
									+ picture.getPictureUrl());
					map.put("goods_id", goods.getGoodsId());
					// code=1不显示的商品；code=0 显示的商品；code=2 下架商品（在收藏列表能显示，但是不能点）
					map.put("code", goods.getDeleteFlag());
					list.add(map);
				}
				json.setResult(list);
				json.setMessage("");

			} else {
				json.setErr_code("10002");
				json.setErr_message("用户未收藏任何商品");
				json.setMessage("您暂时没有收藏任何商品");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return json;
	}

	// 加入收藏
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonObjectView favoriteAdd(@RequestBody FavoriteAddForm form,
			HttpServletRequest request, String user_id) throws ApiException {
		JsonObjectView jsonView = new JsonObjectView();
		// Map<String, Object> map = new HashMap<String, Object>();
		String time = DateFormateUtil.getNowTime("yyyy-MM-dd HH:mm:ss");
		String userId = form.getUserId();
		String goodsId = form.getGoodsId();
		// 用户ID为空
		if (userId == null || userId.equals("")) {
			throw new ApiException(ErrorCode.FAVORITE_USERID_CODE);
		}
		// 商品ID为空
		if (goodsId == null || goodsId.equals("")) {
			throw new ApiException(ErrorCode.FAVORITE_GOODSID_CODE);
		}
		FavoriteKey key = new FavoriteKey();
		key.setGoodsId(goodsId);
		key.setUserId(userId);
		Favorite f = favoriteService.query(key);
		if (f == null) {
			Favorite favorite = new Favorite();
			favorite.setFavoriteId(CommonUtil.getUID());
			favorite.setUserId(userId);
			favorite.setGoodsId(goodsId);
			favorite.setDeleteFlag(0);
			favorite.setInsertTime(time);
			favorite.setUpdateTime(time);
			favoriteService.add(favorite);
			// map.put("result", favorite);
			// jsonView.setResult(map);
			jsonView.setMessage("收藏成功");
			jsonView.setResult(null);
		} else {
			favoriteService.updateFlag(key);
			jsonView.setMessage("收藏成功");
			jsonView.setResult(null);
		}

		return jsonView;
	}

	// 移出收藏夹

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonObjectView favoriteDelete(@RequestBody FavoriteAddForm form,
			HttpServletRequest request, String user_id) throws ApiException {
		JsonObjectView jsonView = new JsonObjectView();
		// Map<String, Object> map = new HashMap<String, Object>();
		// 用户ID为空
		if (form.getUserId() == null || form.getUserId().equals("")) {
			throw new ApiException(ErrorCode.FAVORITE_USERID_CODE);
		}
		// 商品ID为空
		if (form.getGoodsId() == null || form.getGoodsId().equals("")) {
			throw new ApiException(ErrorCode.FAVORITE_GOODSID_CODE);
		}

		FavoriteKey key = new FavoriteKey();
		String userId = form.getUserId();
		String goodsId = form.getGoodsId();
		key.setGoodsId(goodsId);
		key.setUserId(userId);
		favoriteService.delete(key);
		jsonView.setMessage("操作成功");
		jsonView.setResult(null);
		return jsonView;
	}

	// 判断商品是否被收藏
	@ResponseBody
	@RequestMapping(value = "/boolean", method = RequestMethod.GET)
	public JsonObjectView favoriteBoolean(HttpServletRequest request,
			String user_id, String goods_id) throws ApiException {
		JsonObjectView jsonView = new JsonObjectView();
		// Map<String, Object> map = new HashMap<String, Object>();
		// 用户ID为空
		if (user_id == null || user_id.equals("")) {
			throw new ApiException(ErrorCode.FAVORITE_USERID_CODE);
		}
		// 商品ID为空
		if (goods_id == null || goods_id.equals("")) {
			throw new ApiException(ErrorCode.FAVORITE_GOODSID_CODE);
		}
		FavoriteKey key = new FavoriteKey();
		key.setGoodsId(goods_id);
		key.setUserId(user_id);
		Favorite favorite = favoriteService.queryByKey(key);
		if (!(favorite == null || favorite.equals(""))) {
			jsonView.setResult("ture");
			jsonView.setErr_code("10002");
			jsonView.setErr_message("商品已收藏");
			jsonView.setMessage("商品已收藏");
		} else {
			jsonView.setResult("false");
			jsonView.setMessage("商品未收藏");
		}
		return jsonView;
	}

}
