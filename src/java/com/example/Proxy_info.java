package com.example;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.util.Iterator;
import java.util.List;
import java.net.URI;

/**
 *
 * @author sachin vashistha
 */
public class Proxy_info {
    public String[] checkProxy()
    {
        String[] proxy_carrier=new String[2];
        try {
            System.setProperty("java.net.useSystemProxies","true");
            List<java.net.Proxy> l = ProxySelector.getDefault().select(
                        new URI("http://www.yahoo.com/"));

            for (Iterator<java.net.Proxy> iter = l.iterator(); iter.hasNext(); ) {

                java.net.Proxy proxy = iter.next();

                System.out.println("proxy hostname : " + proxy.type());

                InetSocketAddress addr = (InetSocketAddress)proxy.address();

                if(addr == null) {
                    System.out.println("No Proxy");

                } else {
                    System.out.println("proxy hostname : " + addr.getHostName());
                    System.out.println("proxy port : " + addr.getPort());
                    proxy_carrier[0]=addr.getHostName();
                    proxy_carrier[1]= String.valueOf(addr.getPort());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxy_carrier;
    }
}
