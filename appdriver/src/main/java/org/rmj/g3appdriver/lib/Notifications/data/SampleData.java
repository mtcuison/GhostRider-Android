package org.rmj.g3appdriver.lib.Notifications.data;

import org.rmj.g3appdriver.lib.Notifications.pojo.AnnouncementInfo;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<AnnouncementInfo> GetAnnouncementList(){
        List<AnnouncementInfo> loList = new ArrayList<>();

        AnnouncementInfo loInfo = new AnnouncementInfo();
        loInfo.setsTitlexxx("Guanzon Festival 2023");
        loInfo.setsContentx("After a hiatus due to the Covid-19 pandemic, the Guanzon Festival is finally back on track!\n" +
                "\n" +
                "Guanzon Group of Companies has once again begun its back-to-back surprises games and awesome entertainment for everyone to rejoice in. An event as festive as you can imagine only happened in 3 phenomenal days!\n" +
                "\n" +
                "The first day of the event definitely gave a worthwhile entrance of the motorcade along with the different riders group all over Pangasinan. It was accompanied by the drum beaters who really gave out a lively performance for all the onlookers. This was followed by the opening ceremony which was led by Guanzon Group’s President, Mr. Guanson Y. Lo. A heartwarming message was also given by their industry partners: Yamaha’s President, Mr. Hiroshi Koike, Kawasaki’s Assistant to the President, Mr. Koji Nakamori, Suzuki’s President, Mr. Akira Utsumi, Honda’s President, Mr. Susumu Mitsuishi and the Municipal Admin of Calasiao, Ms. Roma Q. Macanlalay.\n" +
                "\n" +
                "One of the highlights during the festival was the performance and chance to personally meet their favorite celebrities: Marko Rudio, JM Bales, JP Bacallan, Cherizawa, Barbie Imperial, Sanya Lopez, Adie and Michael Pangilinan.\n" +
                "\n" +
                "The most anticipated Bingo Motorsiklo made the event extra special where they were given various motorcycles and mobile phones.\n" +
                "\n" +
                "These whole 3 days full of jam-packed activities were brought to you by the Guanzon Group who brings joy and excitement to the organization and the communities it serves.");
        loInfo.setsCntntLnk("https://www.guanzongroup.com.ph/guanzon-festival-2023/");
        loInfo.setsImageLnk("https://www.guanzongroup.com.ph/wp-content/uploads/2023/03/gf23-post-01-768x768.jpg");
        loInfo.setsDateTime("March 1, 2023");
        loList.add(loInfo);

        loInfo = new AnnouncementInfo();
        loInfo.setsTitlexxx("THE UNSTOPPABLE GROWTH OF GUANZON GROUP OF COMPANIES");
        loInfo.setsContentx("Despite the adversity in 2022, Guanzon’s continuous growth has shown that they are capable and will continue to succeed, as they opened their 99th concept store on January 14, 2023.\n" +
                "\n" +
                "The Beginning of 2023 Guanzon Group has successfully launched its third authorized Xiaomi store in San Jose Del Monte.");
        loInfo.setsCntntLnk("https://www.guanzongroup.com.ph/the-unstoppable-growth-of-guanzon-group-of-companies/");
        loInfo.setsImageLnk("https://www.guanzongroup.com.ph/wp-content/uploads/2023/02/Xiaomi_sjdm9-768x576.png");
        loInfo.setsDateTime("February 2, 2023");
        loList.add(loInfo);

        return loList;
    }

}
