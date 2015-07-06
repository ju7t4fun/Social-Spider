package com.epam.lab.spider.controller.vk;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by hell-engine on 7/5/2015.
 */
public class AlphaLimitVKExecutor  {
    public static final Logger LOG = Logger.getLogger(AlphaLimitVKExecutor.class);

    private static RequestConfig proxyConfig;
    static {
        HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
        proxyConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();
    }
    private static final boolean proxyDebug = false;
    private CloseableHttpClient client = HttpClients.createDefault();
    {
        if(proxyDebug)
        try {SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
                    sslsf).build();
            client = httpclient;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }



    public Response execute(HttpRequestBase vkHttpRequestBase, Response.Type responseType) throws VKException, IOException{
        if(proxyDebug)vkHttpRequestBase.setConfig(proxyConfig);
        int connectionTimeOutMillisecond = 5000,
            requestTimeOutMillisecond = 250;
        int attemptCountRestoreProtocolException = 0;
        boolean networkProblem = false, manyRequest = false, protocolException=false;
        do {
             {
                try {
                    if(networkProblem){
                        Thread.sleep(connectionTimeOutMillisecond);
                        networkProblem = false;
                    }else if(manyRequest){
                        Thread.sleep(requestTimeOutMillisecond);
                        manyRequest = false;
                    }else if(protocolException){
                        Thread.sleep(requestTimeOutMillisecond);
                        protocolException = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                CloseableHttpResponse closeableHttpResponse = client.execute(vkHttpRequestBase);
                int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
                if( statusCode !=200 ){
                    String title, message, resultMessage;
                    title = "VK INVALIDED RESPONSE. STATUS CODE: "+statusCode+". ButtHandsCritter, НЕГАЙНО ФІКСИТЬ КОД! ";
                    resultMessage = title;
                    if(statusCode == 400 && vkHttpRequestBase.getURI().toString().length() > 4098){
                        message = "IS POSSIBLE CAUSE ButtHands IN THE ATTEMPT TO CREATE A TOO LONG GET-REQUEST. VK server not care URL > 4098. Use POST-method, ButtHandsCritter!";
                        resultMessage += message;
                    }
                    LOG.fatal(resultMessage);
                    throw new RuntimeException(resultMessage);
                }
                return ResponseFactory.getInstance(responseType, closeableHttpResponse.getEntity());
            } catch (VKException x){
                if(x.getExceptionCode()==VKException.VK_MANY_REQUESTS){
                    manyRequest=true;
                    LOG.error("Too many request. Next attempt to request at "+new Date(System.currentTimeMillis()+requestTimeOutMillisecond));
                }
                else throw x;
            } catch (ClientProtocolException e){
                if(attemptCountRestoreProtocolException>3){
                    throw new VKException(VKException.VK_PROTOCOL_ERROR,"");
                }
                protocolException = true;
                attemptCountRestoreProtocolException++;
            } catch (UnknownHostException e) {
                networkProblem = true;
                LOG.error("Connection Error. Next attempt to restore connection at "+new Date(System.currentTimeMillis()+connectionTimeOutMillisecond));
//                throw new VKException(VKException.VK_NETWORK_CONNECT_ERROR, e.getMessage(), e);
            }
        }while (true);

    }

    public static AlphaLimitVKExecutor getInstance() {
        return instance;
    }

    private static AlphaLimitVKExecutor instance = new AlphaLimitVKExecutor();

}
