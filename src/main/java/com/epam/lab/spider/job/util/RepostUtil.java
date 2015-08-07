package com.epam.lab.spider.job.util;

import com.epam.lab.spider.integration.vk.Parameters;
import com.epam.lab.spider.integration.vk.VKException;
import com.epam.lab.spider.integration.vk.Vkontakte;
import com.epam.lab.spider.integration.vk.auth.AccessToken;
import com.epam.lab.spider.model.entity.DataLock;
import com.epam.lab.spider.model.entity.Owner;
import com.epam.lab.spider.model.entity.Profile;
import org.apache.log4j.Logger;

/**
 * @author Yura Kovalik
 */
// Незаблоковує стіну при помилці доступу при ріпості.
public class RepostUtil {
    public static final Logger LOG = Logger.getLogger(RepostUtil.class);
    public static boolean makeRepost(Profile profile, String postObject, Owner groupOwner, String sign)  {
        String signature = null;
        if(sign!=null){
            signature=  sign.trim();
        }
        try{
        Vkontakte vk = new Vkontakte();

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(profile.getAccessToken());
        accessToken.setUserId(profile.getVkId());
        accessToken.setExpirationMoment(profile.getExtTime().getTime());
        vk.setAccessToken(accessToken);
        return makeRepost(vk,postObject, groupOwner, signature);
        } catch (VKException x) {
            switch (x.getExceptionCode()) {
                case VKException.VK_CAPTCHA_NEEDED: {
                    Locker.getInstance().lock(profile, DataLock.Mode.CAPTCHA);
                    LOG.error("Posting has failed. Profile is locked. Captcha input need!");
                }
                break;
                case VKException.VK_AUTHORIZATION_FAILED: {
                    Locker.getInstance().lock(profile, DataLock.Mode.AUTH_KEY);
                    LOG.error("Posting has failed. Profile is locked. Authorized to vk needed! ");
                }
                break;
                default: {
                    LOG.error("Reposting has failed. Source:" + postObject, x);
                    x.printStackTrace();
                }
            }
        }catch (InterruptedException x) {
            LOG.error(x);
        }
        return false;
    }

    public static boolean makeRepost(Vkontakte vk, String postObject, Owner groupOwner, String sign) throws InterruptedException, VKException{
        Parameters parameters = new Parameters();
        parameters.add("object",postObject);
        if(sign != null && !sign.isEmpty()){
            parameters.add("message",sign);
        }
        if(groupOwner!=null) {
            if (groupOwner.getVkId() < 0) parameters.add("group_id", -groupOwner.getVkId());
            else if(vk.getAccessToken().getUserId() != groupOwner.getVkId()) {
                LOG.error("Method destined to repost in group or himself! To repost himself set groupOwner in null!");
                // TODO: SEND EVENT TO USER
                return false;
            }
        }
        boolean manyRequest = false;
        do {
            try {
                if (manyRequest) {
                    Thread.sleep(400);
                    manyRequest = false;
                }
                vk.wall().repost(parameters);
            } catch (VKException x) {
                if(x.getExceptionCode()==VKException.VK_MANY_REQUESTS)manyRequest=true;
                else throw x;
            }
            // фікс кривої архітектури
            // перехоплення NullPointerException by UnknownHostException
            catch (NullPointerException x){
                Thread.sleep(500);
            }
        }while (manyRequest);
        return true;

    }
}
