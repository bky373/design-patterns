package com.company.design;

import com.company.design.adapter.*;
import com.company.design.proxy.Browser;
import com.company.design.proxy.BrowserProxy;
import com.company.design.proxy.IBrowser;
import com.company.design.singleton.AClazz;
import com.company.design.singleton.BClazz;
import com.company.design.singleton.SocketClient;

public class Main {

    public static void main(String[] args) {

        // 싱글톤 패턴
        AClazz aClazz = new AClazz();
        BClazz bClazz = new BClazz();

        SocketClient aClient = aClazz.getSocketClient();
        SocketClient bClient = bClazz.getSocketClient();

        System.out.println("두 개의 SocketClient 객체가 동일한가?");
        System.out.println(aClient.equals(bClient));


        // 어댑터 패턴
        HairDryer hairDryer = new HairDryer();
        connect(hairDryer);

        Cleaner cleaner = new Cleaner();
        SocketAdapter cleanerAdapter = new SocketAdapter(cleaner);
        connect(cleanerAdapter);

        AirConditioner airConditioner = new AirConditioner();
        SocketAdapter airAdapter = new SocketAdapter(airConditioner);
        connect(airAdapter);


        // 프록시 패턴
        /* Proxy를 사용하지 않은 경우 : 계속 새로운 Html 객체 생성 */
        /* Browser browser = new Browser("www.naver.com");
           browser.show();
           browser.show();
           browser.show();
           browser.show();
           browser.show();
         */

        /* Proxy를 사용한 경우 : 캐시된 html 사용 */
        IBrowser browser = new BrowserProxy("www.naver.com");
        browser.show();
        browser.show();
        browser.show();
        browser.show();
        browser.show();

    }

    // 콘센트
    public static void connect(Electronic110V electronic110V) {
        electronic110V.powerOn();
    }
}
