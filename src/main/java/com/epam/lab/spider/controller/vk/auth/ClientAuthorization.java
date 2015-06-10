package com.epam.lab.spider.controller.vk.auth;

import com.epam.lab.spider.controller.oauth.OAuthConfiguration;
import com.epam.lab.spider.controller.oauth.OAuthDialog;
import com.epam.lab.spider.controller.vk.Authorization;
import com.epam.lab.spider.controller.vk.Configuration;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Boyarsky Vitaliy on 21.01.2015.
 */
public class ClientAuthorization implements Authorization {

    private static final String REDIRECT_URI = "https://oauth.vk.com/blank.html";
    private static final String API_VERSION = "5.27";
    private static final String DISPLAY = "popup";
    private static final String RESPONSE_TYPE = "token";
    private static final String REVOKE = "1";
    private static final String TOKEN_PATH = "src/main/resources/token.xml";

    private Configuration conf;

    public ClientAuthorization(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public AccessToken signIn(boolean needs) {
        AccessToken token = null;
        AccessTokenFile file = new AccessTokenFile();
        boolean auth = true;
        if (file.exists() && !needs) {
            token = file.load();
            auth = !token.isExpired();
        }
        if (auth) {
            token = parseResponse(OAuthDialog.open(buildConfiguration()));
            file.save(token);
        }
        return token;
    }

    private AccessToken parseResponse(String response) {
        AccessToken token = new AccessToken();
        token.setAccessToken(response.split("#access_token=")[1].split("&")[0]);
        token.setExpirationMoment(Long.parseLong(response.split("&expires_in=")[1].split("&")[0]));
        token.setUserId(Integer.parseInt(response.split("&user_id=")[1].split("&")[0]));
        return token;
    }

    private OAuthConfiguration buildConfiguration() {
        OAuthConfiguration conf = new OAuthConfiguration();
        conf.setIcon("src/main/resources/logo.png");
        conf.setToken("access_token");
        conf.setAuthorization(buildRequest());
        return conf;
    }

    private String buildRequest() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://oauth.vk.com/authorize?")
                .append("client_id=").append(conf.getAppId())
                .append("&scope=").append(conf.getPermissions())
                .append("&redirect_uri=").append(REDIRECT_URI)
                .append("&display=").append(DISPLAY)
                .append("&v=").append(API_VERSION)
                .append("&response_type=").append(RESPONSE_TYPE)
                .append("&revoke=").append(REVOKE);
        return sb.toString();
    }

    private class AccessTokenFile {

        private File file = new File(TOKEN_PATH);

        public boolean exists() {
            return file.exists();
        }

        public void save(AccessToken token) {
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                Document doc = docFactory.newDocumentBuilder().newDocument();
                Element root = doc.createElement("vk");
                doc.appendChild(root);
                Attr attr = doc.createAttribute("user_id");
                attr.setValue(String.valueOf(token.getUserId()));
                root.setAttributeNode(attr);
                Element element = doc.createElement("access_token");
                element.appendChild(doc.createTextNode(token.getAccessToken()));
                root.appendChild(element);
                element = doc.createElement("expires_in");
                element.appendChild(doc.createTextNode(String.valueOf(token.getExpirationMoment().getTime())));
                root.appendChild(element);
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }

        public AccessToken load() {
            AccessToken token = new AccessToken();
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                Document doc = docFactory.newDocumentBuilder().parse(file);
                token.setUserId(Integer.parseInt(doc.getElementsByTagName("vk").item(0).getAttributes().getNamedItem
                        ("user_id")
                        .getTextContent()));
                token.setAccessToken(doc.getElementsByTagName("access_token").item(0).getTextContent());
                token.setExpirationMoment(TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(doc.getElementsByTagName
                        ("expires_in").item(0)
                        .getTextContent()) - System.currentTimeMillis()));
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            return token;
        }
    }

}
