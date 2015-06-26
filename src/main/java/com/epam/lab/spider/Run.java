package com.epam.lab.spider;



import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.controller.vk.auth.AccessToken;
import com.epam.lab.spider.job.OnePostJob;
import com.epam.lab.spider.model.db.dao.savable.exception.InvalidEntityException;
import com.epam.lab.spider.model.db.dao.savable.exception.ResolvableDAOException;
import com.epam.lab.spider.model.db.dao.savable.exception.UnsupportedDAOException;
import com.epam.lab.spider.model.db.entity.Attachment;
import com.epam.lab.spider.model.db.entity.NewPost;
import com.epam.lab.spider.model.db.entity.Post;
import com.epam.lab.spider.model.db.entity.Profile;
import com.epam.lab.spider.model.db.service.savable.exception.UnsupportedServiseException;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Boyarsky Vitaliy on 05.06.2015.
 */
public class Run {
    public  final static Logger LOG = Logger.getLogger(Run.class);

    public static void printPermition(String token) throws IOException {
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

    public static void postWithImage(String token, Integer user, Integer wall, String photoURL, String message){
        try {

            Profile profile = new Profile();
            profile.setAppId(4949213);
            profile.setAccessToken(token);
            profile.setVkId(user);
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

            {
                String file = photoURL;
                String attacment = ""+OnePostJob.uploadPhoto(vk, file, wall);

                LOG.debug("Attachments: "+attacment);

                parameters = new Parameters();
                parameters.add("attachments", attacment);
                parameters.add("message", message);
                parameters.add("owner_id", wall);

                vk.wall().post(parameters);

            }

        } catch (VKException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ResolvableDAOException, InvalidEntityException, UnsupportedDAOException, UnsupportedServiseException {

        String token = "21cd2db88ca0c45e1a133671c343ca6fc85cadf62cbbba87f402f07bb0e2e19cad328d638a10a7bbb69a2";

        Attachment attachment = new Attachment();
        attachment.setType(Attachment.Type.PHOTO);
        attachment.setPayload("https://pp.vk.me/c621828/v621828277/2ae4a/oHtE9X6TBrQ.jpg");


        Post post = new Post();
        post.addAttachment(attachment);
        post.setMessage("Згадай про те як ми спалали москву!");


        NewPost newPost = new NewPost();
        newPost.setPost(post);
        newPost.setState(NewPost.State.CREATED);
        newPost.setPostTime(new Date(System.currentTimeMillis()+15000));
        newPost.setWallId(2);

//        SavableServiceUtil.safeSave(newPost);

//        -55431976
        postWithImage(token,21119920,-55431976,attachment.getPayload(),post.getMessage());



        long start, finish;
        {
            start = System.nanoTime();
            RunClass test = new RunClass();
            System.out.println("getInjectedMethod = " + test.getInjectedMethod());
            finish = System.nanoTime();
            System.out.println(finish - start);
        }
        {
            start = System.nanoTime();
            RunClass test = new RunClass();
            System.out.println("getInjectedMethod = " + test.getInjectedMethod());
            finish = System.nanoTime();
            System.out.println(finish - start);
        }




    }
}
interface RunInterface {


}

class RunClass implements RunInterface {

}

