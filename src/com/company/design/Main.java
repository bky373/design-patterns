package com.company.design;

import com.company.design.adapter.*;
import com.company.design.aop.AopBrowser;
import com.company.design.decorator.*;
import com.company.design.facade.Ftp;
import com.company.design.facade.Reader;
import com.company.design.facade.SftpClient;
import com.company.design.facade.Writer;
import com.company.design.observer.Button;
import com.company.design.observer.IButtonListener;
import com.company.design.proxy.BrowserProxy;
import com.company.design.proxy.IBrowser;
import com.company.design.singleton.AClazz;
import com.company.design.singleton.BClazz;
import com.company.design.singleton.SocketClient;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.concurrent.atomic.AtomicLong;

public class Main {
    // 어댑터 패턴 연습을 위한 콘센트 연결 메소드
    public static void connect(Electronic110V electronic110V) {
        electronic110V.powerOn();
    }

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


        // AOP 관련
        AtomicLong start = new AtomicLong();
        AtomicLong end = new AtomicLong();

        IBrowser aopBrower = new AopBrowser("www.naver.com",
                () -> {
                    System.out.println("before");
                    start.set(System.currentTimeMillis());
                },
                () -> {
                    long now = System.currentTimeMillis();
                    end.set(now - start.get());
                }
        );

        aopBrower.show();
        System.out.println("loading time : " + end.get());

        aopBrower.show();
        System.out.println("loading time : " + end.get());


        // 데코레이터 패턴
        ICar audi = new Audi(1000);
        audi.showPrice();

        // a3
        ICar audi3 = new A3(audi, "A3");
        audi3.showPrice();

        // a4
        ICar audi4 = new A4(audi, "A4");
        audi4.showPrice();

        // a5
        ICar audi5 = new A5(audi, "A5");
        audi5.showPrice();


        // 옵저버 패턴
        Button button = new Button("버튼");

        button.addListener(new IButtonListener() {
            @Override
            public void clickEvent(String event) {
                System.out.println(event);
            }
        });

        button.click("메시지 전달 : click1");
        button.click("메시지 전달 : click2");
        button.click("메시지 전달 : click3");
        button.click("메시지 전달 : click4");


        // 파사드 패턴
        /* 파사드 패턴 적용 전 */
        Ftp ftpClient = new Ftp("www.foo.co.kr", 22, "/home/etc");
        ftpClient.connect();
        ftpClient.moveDirectory();

        Writer writer = new Writer("text.tmp");
        writer.fileConnect();
        writer.write();

        Reader reader = new Reader("text.tmp");
        reader.fileConnect();
        reader.fileRead();

        reader.fileDisconnect();
        writer.fileDisConnect();
        ftpClient.disConnect();


        /* 파사드 패턴 적용 후 */
        SftpClient sftpClient = new SftpClient("www.foo.co.kr", 22, "/home/etc", "text.tmp");
        sftpClient.connect();

        sftpClient.write();

        sftpClient.read();

        sftpClient.disConnect();

    }
}
