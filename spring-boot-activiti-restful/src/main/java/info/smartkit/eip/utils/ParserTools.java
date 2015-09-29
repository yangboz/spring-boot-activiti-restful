package info.smartkit.eip.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.Cookie;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.httpclient.cookie.CookieOrigin;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class ParserTools
{

    // /////////////////////////////http 请求/////////////////////////////////////
    /**
     * post 获取 rest 资源
     * 
     * @param url
     * @param name_value_pair
     * @return
     * @throws IOException
     */
    public static String doPost(String url, List<NameValuePair> name_value_pair) throws IOException
    {
        String body = "{}";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setEntity(new UrlEncodedFormEntity(name_value_pair, StandardCharsets.UTF_8));
            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return body;
    }

    /**
     * get 获取 rest 资源
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url) throws ClientProtocolException, IOException
    {
        String body = "{}";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getCookieSpecs().register("easy", csf);
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            // parsing JSON
            JSONObject result = new JSONObject(body); // Convert String to JSON Object
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return body;
    }

    // customer cookie policy, ignore cookie check
    private static CookieSpecFactory csf = new CookieSpecFactory()
    {
        @Override
        public CookieSpec newInstance(HttpParams params)
        {
            return new BrowserCompatSpec()
            {
                public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException
                {
                    // Oh, I am easy
                }
            };
        }
    };
}
