package com.epam.lab.spider;



import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.vk.Photo;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {
    public  final static Logger LOG = Logger.getLogger(Run.class);
    public static void main(String[] args) throws SQLException {


        try {


            String token = "fc4c2f7391dba9276136c882e7a74b0bd6ae79eb104e445a46c0e2aa5361840447f4eea6041a4a14c75a3";

            Profile profile = new Profile();
            profile.setAppId(4949213);
            profile.setAccessToken(token);
            profile.setVkId(21119920);
            profile.setExtTime(new Date(System.currentTimeMillis() + (24 * 3600 * 1000)));

            Vkontakte vk = new Vkontakte(profile.getAppId());
            // Initialization auth_token
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken(profile.getAccessToken());
            accessToken.setUserId(profile.getVkId());
            accessToken.setExpirationMoment(profile.getExtTime().getTime());
            vk.setAccessToken(accessToken);
            // !Initialization auth_token

            Parameters parameters;
//            parameters = new Parameters();
//            parameters.add("user_id",profile.getVkId());

/*            long ssss = vk.account().getAppPermissions(parameters);
            {
                CloseableHttpClient client = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet("https://api.vk.com/method/account.getAppPermissions.xml?user_id=21119920&access_token="+token);
                CloseableHttpResponse response2 = client.execute(httpGet);

                Header[] he = response2.getAllHeaders();


                System.out.println(Arrays.deepToString(he));

                StringWriter writer = new StringWriter();
                IOUtils.copy(response2.getEntity().getContent(), writer, "UTF-8");
                String theString = writer.toString();



                System.out.println("CODE !!!!\n" + theString);
            }

            System.out.println("permition:"+ ssss);
            //parameters.add("", 21119920);*/

            parameters = new Parameters();
            URL uri = vk.photos().getWallUploadServer(parameters);

            System.out.println("URI:"+uri);

            String file = "https://pp.vk.me/c629304/v629304738/2a6b/X_0LLLU3fsE.jpg";
            {
                System.out.println("begin file upload");

                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost  httpPost = new HttpPost(uri.toString());


                HttpEntity entity = MultipartEntityBuilder.create()

//                        .addTextBody("field1", "value1")
                        .addBinaryBody("photo", new URL(file).openStream(), ContentType.create("image/jpeg"), "image.jpg").build();
                httpPost.setEntity(entity);


                // Here we go!
                String response = EntityUtils.toString(client.execute(httpPost).getEntity(), "UTF-8");

                LOG.debug("RESPONSE FROM FILE SERVER: "+response);

                client.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response);

                parameters = new Parameters();
                parameters.add("server", jsonObject.get("server").toString());
                parameters.add("photo", jsonObject.get("photo").toString());
                parameters.add("hash", jsonObject.get("hash").toString());
                parameters.add("user_id",profile.getVkId());

                List<Photo> photos = vk.photos().saveWallPhoto(parameters);
                List<String> attachmentPhoto = new ArrayList<>();
                for(Photo photo:photos){
                    String photoInVk  = "photo"+photo.getOwnerId()+"_"+photo.getId();
                    attachmentPhoto.add(photoInVk);
                }


                StringBuilder attachmetsStringBuilder = new StringBuilder();
                if(!attachmentPhoto.isEmpty())attachmetsStringBuilder.append(attachmentPhoto.get(0));
                for (int i = 1; i < attachmentPhoto.size() ; i++) {
                    attachmetsStringBuilder.append(", ").append(attachmentPhoto.get(i));
                }
                LOG.debug("Attachments: "+attachmetsStringBuilder.toString());

                parameters = new Parameters();
                parameters.add("attachments", attachmetsStringBuilder.toString());
                parameters.add("message", "spider test post");

                vk.wall().post(parameters);

            }


        } catch (VKException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    }
