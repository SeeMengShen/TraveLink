package my.edu.tarc.travelink.ui.home.news

import androidx.lifecycle.ViewModel
import my.edu.tarc.travelink.R
import my.edu.tarc.travelink.R.drawable.*
import my.edu.tarc.travelink.ui.home.data.News
import java.time.LocalDate


class NewsViewModel: ViewModel() {

    val news = listOf<News>(
        News(
            newsTitle = "The Kelana Jaya Line LRT service which experienced disruption at five underground stations between Ampang Park LRT Station and Masjid Jamek LRT Station has returned to operation after the power supply problem was restored at 9.51pm tonight.\n",
            newsDate = LocalDate.of(2023,5,6),
            newsDesc = "Preliminary investigation found that the disturbance was caused by one of the cables not being able to channel the power supply to the underground stations.\n" +
                    "\n" +
                    "A detailed investigation will be conducted to identify the cause of this incident so that it can be avoided in the future.\n" +
                    "\n" +
                    "To accommodate affected passengers, an intermediate train service has been provided between Pasar Seni LRT Station and Dang Wangi LRT Station.\n" +
                    "\n" +
                    "A free intermediate bus service is also provided involving 8 buses between Pasar Seni LRT Station and Dang Wangi LRT Station.\n" +
                    "\n" +
                    "Auxiliary Police officers and station attendants have also been stationed on the platform and station concourse area to assist the movement of passengers.\n" +
                    "\n" +
                    "Rapid Rail apologizes for the inconvenience caused to users as a result of this disruption.",
            newsPhoto = rapid_kl_train
        ),
        News(
            newsTitle = "Good news for all Skip Stop Express bus service users! Starting on 17 April 2023, the Proof of Concept (POC) Route DS01 will be incorporated as one of the regular routes in the Rapid KL bus service.",
            newsDate = LocalDate.of(2023,4,14),
            newsDesc = "Route DS01 is a bus service in the Ampang corridor with shorter journeys and limited stops, from Ampang LRT Station to Ampang Park and Kuala Lumpur City Center (KLCC).\n" +
                    "\n" +
                    "This service operates from Monday to Friday during the morning peak time which is from 6:30 am until 10:00 am and during the evening peak time which is from 4:30 pm until 7:00 pm.\n" +
                    "\n" +
                    "The service has recorded a very encouraging number of passengers since it started operating on 16 May 2022 which is 54,000 with an average daily passenger of 400 people.",
            newsPhoto = rapid_kl_bus
        ),
        News(
            newsTitle = "For the first time since the country entered the endemic phase, the number of passengers using rail and bus services operated by Prasarana Malaysia Berhad (Prasarana) reached one million passengers a day on 21 March 2023, which is a jump from the average of 846,000 passengers a day from January 1 to March 15.",
            newsDate = LocalDate.of(2023,3,25),
            newsDesc = "Of that number, Rapid Rail Sdn Bhd (Rapid Rail) services recorded a total of 767,000 passengers while Rapid Bus Sdn Bhd (Rapid Bus) services recorded 235,000 passengers, the highest figure for bus services since the pandemic.\n" +
                    "\n" +
                    "Before Covid-19 started to hit the country in early 2020, the average number of passengers using Prasarana services was about 1.2 million people a day.\n" +
                    "\n" +
                    "The number of one million passengers per day includes an average of 100,000 passengers who use the Putrajaya Line MRT service since it was launched by YAB Prime Minister Datuk Seri Anwar Ibrahim on March 16.\n" +
                    "\n" +
                    "Passengers now enjoy free fares for Putrajaya Line MRT service users, including intermediate buses for the Putrajaya Line MRT from Kwasa Damansara to Putrajaya Sentral, until next March 31.\n" +
                    "\n" +
                    "For any further information, please contact us at suggest@rapidkl.com.my or call us at 03 – 7885 2585 from Monday to Friday, from 7.00am to 8.30pm, while for the period from Saturday to Sunday and Public Holidays from 7.00am to 5.30 pm.\n" +
                    "\n" +
                    "The public can also get the latest information through Rapid KL's social media channels or through the PULSE application which can be downloaded for free.",
            newsPhoto = rapidtrain2
        ),
        News(
            newsTitle = "Good news for the residents of Shah Alam! Proof of concept (POC) of the 'On Demand' Rapid KL x Kumpool intermediate bus that previously operated in Wangsa Maju and Setapak will now be introduced in Shah Alam.",
            newsDate = LocalDate.of(2023,2,10),
            newsDesc = "The 'On Demand' service covering the area around Alam Megah, Shah Alam will be known as route T757 and will operate for three (3) months from 13 February 2023 until 12 May 2023.\n" +
                    "\n" +
                    "This POC is a collaboration between Rapid Bus Sdn Bhd (Rapid Bus) and Handal Indah Sdn Bhd, the owner of Kumpool, an e-hailing service booking application around Kuala Lumpur and Johor Bahru.\n" +
                    "\n" +
                    "The Chief Executive Officer of Rapid Bus, Muhammad Yazurin Sallij said, \"The POC has previously received a very encouraging response in Wangsa Maju where a total of 26,813 passengers have used it during the 4 months it has been implemented. This shows that the provision of more comprehensive 'first-last mile' connectivity is very important. Accordingly, Rapid Bus decided to introduce this kind of service in areas with relatively low passenger rates,\"\n" +
                    "\n" +
                    "Still maintaining the fare of RM1.00, this service also offers a seat guarantee after the reservation is made. Passengers can also use My50 Unlimited Travel Pass, Touch n' Go card, as well as Malaysia Family Pass (which is only valid on weekends and public holidays) as a payment mode when using this 'On Demand' service.\n" +
                    "\n" +
                    "This 'On Demand' intermediate bus will operate every day from 6.30 am until 11.30 pm. In addition, the available stops are also more and are not subject to the existing T757 route bus stops in Alam Megah. This service also offers a shorter journey time as it does not need to go through all the stops if there is no reservation.",
            newsPhoto = smolbus
        ),
        News(
            newsTitle = "The LRT11 intermediate bus service that operates between Sentul Timur and City stations will be added from Monday 27 February 2023 for the convenience of the public using the route.",
            newsDate = LocalDate.of(2023,2,25),
            newsDesc = "A total of 15 LRT11 intermediate buses will operate between the two stations with a frequency of every 10 minutes.\n" +
                    "\n" +
                    "The addition of this intermediate bus is to support train operations between Sentul Timur and Bandaraya stations which will operate with a frequency of every 24 minutes from Monday.\n" +
                    "\n" +
                    "The bus service which was reactivated last Thursday will stop at Sentul Timur, Sentul, Titiwangsa, PWTC, Sultan Ismail and Bandaraya stations. DBKL will support with a special bus route to facilitate travel.\n" +
                    "\n" +
                    "Rail operations personnel, auxiliary police and Troopers will be assigned at the stations to help control the movement of passengers at the stations and platforms.\n" +
                    "\n" +
                    "As a result of structural and track damage near the City station believed to be caused by construction works in the nearby area, five trains that were previously used between Sentul Timur and City were unable to return to the LRT depot in Ampang for maintenance.\n" +
                    "\n" +
                    "As a result, it was found that only two of the five trains now meet the safety and operational criteria set while three more trains have been removed from service due to safety factors.\n" +
                    "\n" +
                    "The two trains will be used alternately from Monday to ensure that both are not affected and can continue to operate at an optimal level, while avoiding the complete closure of the stations between Sentul Timur and the City.\n" +
                    "\n" +
                    "We apologize for the inconvenience that may be experienced by passengers during this period and will continue to take appropriate measures to reduce the inconvenience experienced.\n",
            newsPhoto = rapidmedia
        ),
        News(
            newsTitle = "Rapid Rail Sdn Bhd would like to inform you that the disruption to the Kelana Jaya Line LRT service that occurred at 6.38pm yesterday was caused by a disruption in the Passenger Intrusion Emergency Stop Security System or 'Passenger Intrusion Emergency Stop' (PIES) that occurred at Taman Bahagia LRT Station was successfully overcome at 9.40pm yesterday.",
            newsDate = LocalDate.of(2023,3,28),
            newsDesc = "This disruption was caused by one of the \"PIES\" components that had been stuck under train number 63, causing the train to be stopped for safety reasons.\n" +
                    "\n" +
                    "The operation has activated intermediate train and bus services to carry affected passengers between Kelana Jaya LRT Station and Asia Jaya LRT Station.\n" +
                    "\n" +
                    "As a mitigation measure, Rapid Rail is now actively carrying out improvement works on several other issues that have occurred recently so that they can be avoided in the future.\n" +
                    "\n" +
                    "Among the improvements being carried out is upgrading the LRT encryption system and the \"PIES\" system.\n" +
                    "\n" +
                    "This improvement step is to ensure the safety and comfort of users and further provide quality services for public transport users.",
            newsPhoto = rapidmedia
        ),
        News(
            newsTitle = "The 57.7km long Putrajaya Line MRT service will be fully opened at 3pm today. The launch ceremony was completed by the Prime Minister, YAB Dato' Seri Anwar Ibrahim at Serdang MRT Depot, this morning.",
            newsDate = LocalDate.of(2023,3,16),
            newsDesc = "The full operation of the Putrajaya Line MRT service from Kwasa Damansara station to Putrajaya Sentral will be operated by Rapid Rail Sdn Bhd (Rapid Rail), a subsidiary of Prasarana Malaysia Berhad.\n" +
                    "\n" +
                    "The MRT line that connects the Federal Government Administration Center with the surrounding areas of the Klang Valley is expected to record a daily passenger count of more than 100,000 people in the first year of its opening.\n" +
                    "\n" +
                    "Along with this full opening, an intermediate bus service will be launched to support operations. The Putrajaya Line MRT intermediate bus service involves 92 buses and has a total of 31 routes covering approximately 350km.\n" +
                    "\n" +
                    "To encourage the public to use this new service, free fares will be given to passengers entering or exiting at any of the 36 stations along the route, including intermediate bus services for Putrajaya Line MRT stations, for two weeks from 3pm today until 12 midnight, March 31, 2023.\n" +
                    "\n" +
                    "The MRT Putrajaya Line has a total of 36 stations, including 27 elevated stations and nine underground stations. Of that number, 10 stations are interchange or connection stations, which make it easier for passengers from the Putrajaya Line to go to the existing rail lines.\n" +
                    "\n" +
                    "A total of 17 stations are equipped with parking facilities that provide a total of 5,878 parking spaces.\n" +
                    "\n" +
                    "The operation of the Putrajaya Line MRT service will use a total of 49 4-carriage trains with a capacity of 1,200 people with a frequency of 5 minutes during peak hours and 10 minutes outside of peak hours. The travel time from Putrajaya Sentral to Kwasa Damansara takes 84 minutes.\n" +
                    "\n" +
                    "The availability of this public transport network is also expected to further improve the current traffic situation, provide added value to the local economy and encourage the use of public transport towards the target of 40% by 2030.",
            newsPhoto = putrarapid
        ),
        News(
            newsTitle = "Peak hour operations for rail services (LRT, MRT and KL Monorail) and buses operated by Prasarana will be adjusted throughout the month of Ramadan this year.",
            newsDate = LocalDate.of(2023,3,22),
            newsDesc = "This peak time change is to facilitate the travel of users throughout the month of Ramadan, especially for those who want to go to their respective destinations to break their fast.\n" +
                    "\n" +
                    "Rapid Rail Operating Hours\n" +
                    "\n" +
                    "The morning peak hour operation will start 30 minutes earlier than usual which is from 6.30 am to 9.30 am compared to the current time which is 7.00 am to 9.30 am.\n" +
                    "\n" +
                    "The evening peak time will start at 4.00 pm to 8.00 pm compared to 5.00 pm to 7.30 pm now.\n" +
                    "\n" +
                    "Rapid Bus Operating Hours\n" +
                    "\n" +
                    "For Rapid Bus services in the Klang Valley, Penang and Kuantan, including MRT intermediate buses, morning peak hours are from 6.30am to 9.30am while afternoon peak hours are from 4.00pm to 8.00pm.\n" +
                    "\n" +
                    "Ramadan Bazaar Enlivens the Month of Fasting\n" +
                    "\n" +
                    "In conjunction with the month of Ramadan, Prasarana's subsidiary Prasarana Integrated Development Sdn Bhd (PRIDE) will open a Ramadan bazaar at five (5) selected LRT stations. The stations involved are Pasar Seni LRT, Damai LRT, Sri Rampai LRT, Putra Heights LRT and Ampang LRT.\n" +
                    "\n" +
                    "Various dishes for breaking the fast will be sold from 3.30 pm to 7.30 pm. This facility can give users the opportunity to get iftar dishes.",
            newsPhoto = prasarana
        ),
    )

    fun get(newsTitle: String): News? {
        return news.find{news -> news.newsTitle == newsTitle}
    }

    fun getAll() = news


}


