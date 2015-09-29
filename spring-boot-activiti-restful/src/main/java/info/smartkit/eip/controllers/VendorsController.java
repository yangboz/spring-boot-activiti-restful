package info.smartkit.eip.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import info.smartkit.eip.models.VendorDao;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.smartkit.eip.consts.ThirdPartyConstants;
import info.smartkit.eip.dto.JsonObject;
import info.smartkit.eip.models.VendorRepository;
import info.smartkit.eip.utils.ParserTools;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/vendors")
// @see: http://java.dzone.com/articles/spring-rest-controller
public class VendorsController {
	private Logger LOG = LogManager.getLogger(VendorsController.class);
	// ==============
	// PRIVATE FIELDS
	// ==============

	// Autowire an object of type CompanyDao
	@Autowired
	private VendorDao _vendorDao;
	//
	private VendorRepository vendorRepository;

	@Autowired
	public VendorsController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	// ==============
	// PUBLIC METHODS
	// ==============
	@RequestMapping(method = RequestMethod.GET, params = { "latitude",
			"longitude", "category" })
	@ApiOperation(httpMethod = "GET", value = "Response a list describing all of vendors that is successfully get or not.")
	public JsonObject list(
			@RequestParam(value = "latitude", defaultValue = "31.21524") String latitude,
			@RequestParam(value = "longitude", defaultValue = "121.420033") String longitude,
			@RequestParam(value = "category", defaultValue = "美食") String category)
			throws ClientProtocolException, IOException, ParseException {
		// return new JsonObject(this.vendorRepository.findAll());
		// @see: http://www.tuicool.com/articles/3aIFrm
		String url_geocoder = "http://api.map.baidu.com/geocoder/v2/?ak="
				+ ThirdPartyConstants.BAIDU_AK + "&location=" + latitude + ","
				+ longitude + "&output=json&pois=0";
		String url2ParsedStr = ParserTools.doGet(url_geocoder);
		// String encodedJsonStr = URLEncoder.encode(url2ParsedStr, "utf-8");
		// {"status":0,"result":{"location":{"lng":121.4200330434,"lat":31.215239865509},"formatted_address":"上海市长宁区天山路1890弄-1-～41号","business":"天山路,遵义路,虹桥","addressComponent":{"city":"上海市","direction":"附近","distance":"24","district":"长宁区","province":"上海市","street":"天山路","street_number":"1890弄-1-～41号"},"poiRegions":[],"cityCode":289}}
		//
		JSONObject jObject = new JSONObject(url2ParsedStr);
		JSONObject resultObject = jObject.getJSONObject("result");
		JSONObject addressComponentObject = resultObject
				.getJSONObject("addressComponent");
		String cityStr = addressComponentObject.getString("city");
		cityStr = cityStr.substring(0, cityStr.length()-1);
		String districtStr = addressComponentObject.getString("district");
		//
		LOG.info("Geocoder by BAIDU,city :" + url_geocoder
				+ ",then decoding JSON:" + resultObject.toString()+",city:"+cityStr);
		//Call DZDP API.
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("city", cityStr);//Notice:"上海市" to "上海"
		paramMap.put("latitude", latitude);
		paramMap.put("longitude", longitude);
		paramMap.put("category", category);
		paramMap.put("region", districtStr);
		paramMap.put("limit", "20");
		paramMap.put("radius", "2000");
		paramMap.put("offset_type", "0");
		// paramMap.put("has_coupon", "1");
		// paramMap.put("has_deal", "1");
		// paramMap.put("keyword", "泰国菜");
		paramMap.put("sort", "7");
		paramMap.put("format", "json");

		String requestResult = requestApi(ThirdPartyConstants.DZDP_apiUrl,
				ThirdPartyConstants.DZDP_appKey,
				ThirdPartyConstants.DZDP_secret, paramMap);
		//
		return new JsonObject(requestResult);
	}

	@SuppressWarnings("unused")
	private String requestApi(String apiUrl, String appKey, String secret,
			Map<String, String> paramMap) {
		String queryString = getQueryString(appKey, secret, paramMap);

		StringBuffer response = new StringBuffer();
		HttpClientParams httpConnectionParams = new HttpClientParams();
		httpConnectionParams.setConnectionManagerTimeout(1000);
		HttpClient client = new HttpClient(httpConnectionParams);
		HttpMethod method = new GetMethod(apiUrl);

		try {
			if (StringUtils.isNotBlank(queryString)) {
				// Encode query string with UTF-8
				String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8");
				LOG.debug("Encoded Query:" + encodeQuery);
				method.setQueryString(encodeQuery);
			}

			client.executeMethod(method);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				response.append(line).append(
						System.getProperty("line.separator"));
			}
			reader.close();
		} catch (URIException e) {
			LOG.error("Can not encode query: " + queryString
					+ " with charset UTF-8. ", e);
		} catch (IOException e) {
			LOG.error("Request URL: " + apiUrl + " failed. ", e);
		} finally {
			method.releaseConnection();
		}
		//
//		LOG.info("DZDP API response:"+response);
		return response.toString();
//		return new JSONObject(response.toString());
	}

	private String getQueryString(String appKey, String secret,
			Map<String, String> paramMap) {
		String sign = sign(appKey, secret, paramMap);

		// 添加签名
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=")
				.append(sign);
		for (Entry<String, String> entry : paramMap.entrySet()) {
			stringBuilder.append('&').append(entry.getKey()).append('=')
					.append(entry.getValue());
		}
		String queryString = stringBuilder.toString();
		return queryString;
	}

	private String sign(String appKey, String secret,
			Map<String, String> paramMap) {
		// 对参数名进行字典排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);

		// 拼接有序的参数名-值串
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}

		stringBuilder.append(secret);
		String codes = stringBuilder.toString();

		// SHA-1编码， 这里使用的是Apache
		// codec，即可获得签名(shaHex()会首先将中文转换为UTF8编码然后进行sha1计算，使用其他的工具包请注意UTF8编码转换)
		/*
		 * 以下sha1签名代码效果等同 byte[] sha =
		 * org.apache.commons.codec.digest.DigestUtils
		 * .sha(org.apache.commons.codec
		 * .binary.StringUtils.getBytesUtf8(codes)); String sign =
		 * org.apache.commons
		 * .codec.binary.Hex.encodeHexString(sha).toUpperCase();
		 */
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes)
				.toUpperCase();

		return sign;
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST)
	 * 
	 * @ApiOperation(httpMethod = "POST", value =
	 * "Response a string describing if the vendor is successfully created or not."
	 * ) public Vendor create(@RequestBody @Valid Vendor vendor) { return
	 * this.vendorRepository.save(vendor); }
	 * 
	 * @RequestMapping(method = RequestMethod.GET)
	 * 
	 * @ApiOperation(httpMethod = "GET", value =
	 * "Response a list describing all of vendors that is successfully get or not."
	 * ) public JsonObject list() { return new
	 * JsonObject(this.vendorRepository.findAll()); }
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 * 
	 * @ApiOperation(httpMethod = "GET", value =
	 * "Response a string describing if the vendor id is successfully get or not."
	 * ) public Vendor get(@PathVariable("id") long id) { return
	 * this.vendorRepository.findOne(id); }
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 * 
	 * @ApiOperation(httpMethod = "PUT", value =
	 * "Response a string describing if the reimbursement item is successfully updated or not."
	 * ) public Vendor update(@PathVariable("id") long id,
	 * 
	 * @RequestBody @Valid Vendor vendor) { return
	 * vendorRepository.save(vendor); }
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 * 
	 * @ApiOperation(httpMethod = "DELETE", value =
	 * "Response a string describing if the item is successfully delete or not."
	 * ) public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
	 * this.vendorRepository.delete(id); return new
	 * ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK); }
	 */
}